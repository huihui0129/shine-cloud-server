package com.shine.security.strategy;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shine.common.context.ShineRequestContext;
import com.shine.common.exception.BaseException;
import com.shine.common.status.ResponseStatus;
import com.shine.rabbitmq.constant.RabbitConstant;
import com.shine.security.authorization.impl.AuthorityPrincipal;
import com.shine.security.authorization.impl.ClientAuthorityPrincipal;
import com.shine.security.context.SecurityContextHolder;
import com.shine.security.entity.AccessToken;
import com.shine.security.entity.AuthorizationCode;
import com.shine.security.entity.Client;
import com.shine.security.enums.AuthorizationCodeStatusEnum;
import com.shine.security.http.AuthorityStatus;
import com.shine.security.manager.AsyncManager;
import com.shine.security.mapper.AuthorizationCodeMapper;
import com.shine.security.mapper.ClientMapper;
import com.shine.security.password.PasswordEncoder;
import com.shine.security.properties.SecurityProperties;
import com.shine.security.request.AuthorizationCodeRequest;
import com.shine.security.response.AccessTokenResponse;
import com.shine.security.response.AuthorizeCodeResponse;
import com.shine.security.response.AuthorizeResponse;
import com.shine.security.token.TokenManager;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AuthorizationCodeStrategy implements AuthorizationStrategy<AuthorizationCodeRequest> {

    @Resource
    private ClientMapper clientMapper;

    @Resource
    private AuthorizationCodeMapper authorizationCodeMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource(name = "secretEncoder")
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityProperties properties;

    @Resource
    private AsyncManager asyncManager;

    @Override
    public AuthorizeResponse authorize(String responseType, String clientId, String redirectUri, String scope, String state) {
        log.info("获取授权码：{}", clientId);
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
        response.setRedirectUri(redirectUri);
        response.setScope(scope);
        response.setState(state);
        // 保存授权码到服务器
        Long userId = SecurityContextHolder.getContext().getPrincipal().getId();
        AuthorizationCode authorizationCode = new AuthorizationCode();
        authorizationCode.setClientId(clientId);
        authorizationCode.setAuthorizationCode(code);
        authorizationCode.setRedirectUri(redirectUri);
        authorizationCode.setScope(scope);
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(client.getAccessTokenLefetime());
        authorizationCode.setExpireAt(expireTime);
        authorizationCode.setStatus(AuthorizationCodeStatusEnum.S1.getCode());
        authorizationCode.setCreateUser(userId);
        authorizationCode.setUpdateUser(userId);
        authorizationCodeMapper.insert(authorizationCode);
        // 发送一条消息给rabbitmq
        rabbitTemplate.setConfirmCallback((data, ack, cause) -> {
            if (ack) {
                log.info("消息发送成功！数据：{}", data);
            } else {
                log.error("消息发送失败！数据：{}", cause);
            }
        });
        rabbitTemplate.convertAndSend(RabbitConstant.Security.AUTHORIZATION_CODE_EXCHANGE, RabbitConstant.Security.AUTHORIZATION_CODE_KEY, authorizationCode.getId());
        // 存储Token信息
        return response;
    }

    @Override
    public AccessTokenResponse token(AuthorizationCodeRequest request) {
        String clientId = request.getClientId();
        String clientSecret = request.getClientSecret();
        String code = request.getCode();
        String grantType = request.getGrantType();
        log.info("使用授权码获取令牌：{}", clientId);
        Client client = clientMapper.selectOne(Wrappers.<Client>lambdaQuery().eq(Client::getClientId, clientId));
        // 验证客户端
        if (!StringUtils.equals(clientId, client.getClientId())) {
            throw new BaseException(AuthorityStatus.NOT_EXISTS_CLIENT);
        }
        if (!passwordEncoder.matches(clientSecret, client.getClientSecret())) {
            throw new BaseException(AuthorityStatus.CLIENT_ID_MISMATCH);
        }
        // 验证授权码
        AuthorizationCode authorizationCode = authorizationCodeMapper.selectOne(
                Wrappers.<AuthorizationCode>lambdaQuery()
                        .eq(AuthorizationCode::getAuthorizationCode, code)
                        .eq(AuthorizationCode::getClientId, client.getClientId())
        );
        // 不存在
        if (authorizationCode == null) {
            throw new BaseException(AuthorityStatus.NO_AUTHORIZATION_CODE);
        }
        // 已使用
        if (AuthorizationCodeStatusEnum.S2.getCode().equals(authorizationCode.getStatus())) {
            throw new BaseException(AuthorityStatus.AUTHORIZATION_CODE_USED);
        }
        // 已过期
        if (AuthorizationCodeStatusEnum.S3.getCode().equals(authorizationCode.getStatus())) {
            throw new BaseException(AuthorityStatus.AUTHORIZATION_CODE_EXPIRE);
        }
        // 错误的授权码
        if (!StringUtils.equals(authorizationCode.getAuthorizationCode(), code)) {
            throw new BaseException(AuthorityStatus.INCORRECT_AUTHORIZATION_CODE);
        }
        // 非本人获取
        Long userId = SecurityContextHolder.getContext().getPrincipal().getId();
        if (authorizationCode.getCreateUser().equals(userId)) {
            throw new BaseException(AuthorityStatus.CREATE_USER_MISMATCH);
        }
        // 生成token
        String token = ShineRequestContext.getToken();
        AuthorityPrincipal user = TokenManager.parse(token, AuthorityPrincipal.class);
        ClientAuthorityPrincipal principal = new ClientAuthorityPrincipal();
        principal.setClientId(client.getClientId());
        principal.setId(user.getId());
        principal.setUsername(user.getUsername());
        principal.setPassword(user.getPassword());
        // 客户端访问令牌过期时间
        Integer expireIn = properties.getClientAccessTokenExpireSeconds();
        // 客户端刷新令牌过期时间
        Integer refreshExpireIn = properties.getClientRefreshTokenExpireSeconds();
        String accessToken = TokenManager.generate(principal, expireIn);
        String refreshToken = TokenManager.generate(principal, refreshExpireIn);
        AccessTokenResponse response = new AccessTokenResponse();
        response.setAccessToken(accessToken);
        response.setTokenType("Bearer");
        response.setExpiresIn(expireIn);
        response.setRefreshToken(refreshToken);
        response.setScope(authorizationCode.getScope());
        // 授权码标记为已使用
        authorizationCodeMapper.update(
                Wrappers.<AuthorizationCode>lambdaUpdate()
                        .eq(AuthorizationCode::getId, authorizationCode.getId())
                        .set(AuthorizationCode::getStatus, AuthorizationCodeStatusEnum.S2.getCode())
        );
        // 记录token
        AccessToken saveToken = new AccessToken();
        saveToken.setClientId(clientId);
        saveToken.setAccessToken(accessToken);
        saveToken.setRefreshToken(refreshToken);
        saveToken.setUserId(userId);
        saveToken.setGrantType(grantType);
        saveToken.setScope(authorizationCode.getScope());
        saveToken.setTokenType("Bearer");
        saveToken.setRedirectUri(authorizationCode.getRedirectUri());
        saveToken.setAccessTokenExpireTime(LocalDateTime.now().plusSeconds(expireIn));
        saveToken.setRefreshTokenExpireTime(LocalDateTime.now().plusSeconds(refreshExpireIn));
        saveToken.setCreateUser(userId);
        saveToken.setUpdateUser(userId);
        saveToken.setRemark("使用授权码生成的令牌");
        asyncManager.saveToken(saveToken);
        return response;
    }

}
