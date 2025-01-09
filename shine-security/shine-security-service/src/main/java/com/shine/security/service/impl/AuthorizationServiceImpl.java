package com.shine.security.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shine.common.exception.BaseException;
import com.shine.rabbitmq.constant.RabbitConstant;
import com.shine.security.entity.AuthorizationCode;
import com.shine.security.enums.AuthorizationCodeStatusEnum;
import com.shine.security.enums.AuthorizationResponseTypeEnum;
import com.shine.security.http.SecurityStatus;
import com.shine.security.mapper.AuthorizationCodeMapper;
import com.shine.security.request.AccessTokenRequest;
import com.shine.security.response.AccessTokenResponse;
import com.shine.security.response.AuthorizeResponse;
import com.shine.security.service.AuthorizationService;
import com.shine.security.strategy.AuthorizationCodeStrategy;
import com.shine.security.strategy.AuthorizationContext;
import com.shine.security.strategy.PasswordStrategy;
import com.shine.security.strategy.RefreshTokenStrategy;
import com.rabbitmq.client.Channel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author huihui
 * @date 2024/11/27 18:09
 * @description AuthorizationServiceImpl
 */
@Slf4j
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Resource
    private AuthorizationCodeStrategy authorizationCodeStrategy;

    @Resource
    private RefreshTokenStrategy refreshTokenStrategy;

    @Resource
    private PasswordStrategy passwordStrategy;

    @Resource
    private AuthorizationCodeMapper authorizationCodeMapper;

    @RabbitListener(queues = RabbitConstant.Security.AUTHORIZATION_CODE_DIE_QUEUE)
    public void handleAuthorizationCodeStatus(Message message, Channel channel) {
        String body = new String(message.getBody());
        log.warn("接收到消息：{}", body);
        // 签收消息
        try {
            Long id = Long.valueOf(body);
            AuthorizationCode authorizationCode = authorizationCodeMapper.selectById(id);
            if (authorizationCode == null) {
                log.error("授权码已被删除：{}", id);
            } else if (AuthorizationCodeStatusEnum.S2.getCode().equals(authorizationCode.getStatus())) {
                log.info("授权码已被使用：{}", id);
            } else if (AuthorizationCodeStatusEnum.S3.getCode().equals(authorizationCode.getStatus())) {
                log.error("重复消费消息？：{}", id);
            } else {
                // 修改为已过期
                int count = authorizationCodeMapper.update(
                        Wrappers.<AuthorizationCode>lambdaUpdate()
                                .eq(AuthorizationCode::getId, id)
                                .set(AuthorizationCode::getStatus, AuthorizationCodeStatusEnum.S3.getCode())
                );
                if (count > 0) {
                    log.info("更新授权码状态成功：{}", id);
                } else {
                    log.error("更新授权码状态失败：{}", id);
                }
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.warn("消息签收成功！");
        } catch (IOException e) {
            log.error("消息签收失败", e);
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), true, true);
                log.warn("拒绝签收成功！");
            } catch (IOException ex) {
                log.error("拒绝签收失败！", ex);
            }
        }
    }

    /**
     * 获取授权码
     *
     * @param responseType
     * @param clientId
     * @param redirectUri
     * @param scope
     * @param state
     * @return
     */
    @Override
    public AuthorizeResponse authorize(String responseType, String clientId, String redirectUri, String scope, String state) {
        AuthorizationResponseTypeEnum typeEnum = AuthorizationResponseTypeEnum.findByCode(responseType);
        AuthorizationContext context = this.findAuthorizationContext(typeEnum);
        return context.authorize(responseType, clientId, redirectUri, scope, state);
    }

    /**
     * 获取访问令牌
     *
     * @param request
     * @return
     */
    @Override
    public AccessTokenResponse token(AccessTokenRequest request) {
        AuthorizationResponseTypeEnum typeEnum = AuthorizationResponseTypeEnum.findByCode(request.getGrantType());
        AuthorizationContext context = this.findAuthorizationContext(typeEnum);
        return context.token(request);
    }

    /**
     * 获取对应的策略
     *
     * @param typeEnum
     * @return
     */
    private AuthorizationContext findAuthorizationContext(AuthorizationResponseTypeEnum typeEnum) {
        switch (typeEnum) {
            case CODE: // 授权码模式
                return new AuthorizationContext(authorizationCodeStrategy);
            case REFRESH_TOKEN: // 刷新令牌模式
                return new AuthorizationContext(refreshTokenStrategy);
            case PASSWORD:
                return new AuthorizationContext(passwordStrategy);
            default:
                throw new BaseException(SecurityStatus.AUTH_MODE_ERROR);
        }
    }
}
