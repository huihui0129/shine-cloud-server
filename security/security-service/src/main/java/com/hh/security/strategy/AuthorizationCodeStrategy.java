package com.hh.security.strategy;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hh.common.exception.BaseException;
import com.hh.common.status.ResponseStatus;
import com.hh.rabbitmq.constant.RabbitConstant;
import com.hh.security.entity.AuthorizationCode;
import com.hh.security.entity.Client;
import com.hh.security.enums.AuthorizationCodeStatusEnum;
import com.hh.security.http.AuthorityStatus;
import com.hh.security.mapper.AuthorizationCodeMapper;
import com.hh.security.mapper.ClientMapper;
import com.hh.security.response.AccessTokenResponse;
import com.hh.security.response.AuthorizeCodeResponse;
import com.hh.security.response.AuthorizeResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author huihui
 * @date 2024/11/27 18:42
 * @description AuthorizationCodeStrategy 授权码模式
 */
@Slf4j
@Component
public class AuthorizationCodeStrategy implements AuthorizationStrategy {

    @Resource
    private ClientMapper clientMapper;

    @Resource
    private AuthorizationCodeMapper authorizationCodeMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public AuthorizeResponse authorize(String responseType, String clientId, String redirectUri, String scope, String state) {
        if (StringUtils.isBlank(clientId)) {
            throw new BaseException(ResponseStatus.PARAMS_ERROR, "是哪一个客户端呢？");
        }
        Client client = clientMapper.selectOne(
                Wrappers.<Client>lambdaQuery()
                        .eq(Client::getClientId, clientId)
        );
        if (client == null) {
            throw new BaseException(AuthorityStatus.NOT_EXISTS_CLIENT);
        }
        AuthorizeCodeResponse response = new AuthorizeCodeResponse();
        String code = UUID.randomUUID().toString().replaceAll("-", "");
        response.setCode(code);
        // 保存授权码到服务器
        AuthorizationCode authorizationCode = new AuthorizationCode();
        authorizationCode.setClientId(clientId);
        authorizationCode.setAuthorizationCode(code);
        authorizationCode.setRedirectUri(redirectUri);
        authorizationCode.setScope(scope);
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(client.getAccessTokenLefetime());
        authorizationCode.setExpireAt(expireTime);
        authorizationCode.setStatus(AuthorizationCodeStatusEnum.S1.getCode());
        authorizationCodeMapper.insert(authorizationCode);
        // 发送一条消息给rabitmq
        rabbitTemplate.setConfirmCallback((data, ack, cause) -> {
            if (ack) {
                log.info("消息发送成功！数据：{}", cause);
            } else {
                log.error("消息发送失败！数据：{}", cause);
            }
        });
        rabbitTemplate.convertAndSend(RabbitConstant.Security.AUTHORIZATION_CODE_EXCHANGE, RabbitConstant.Security.AUTHORIZATION_CODE_KEY, authorizationCode.getId());
        return response;
    }

    @Override
    public AccessTokenResponse token(String clientId, String clientSecret, String grantType, String code, String refreshToken) {
        return null;
    }
}
