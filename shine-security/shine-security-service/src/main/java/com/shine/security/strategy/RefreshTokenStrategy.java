package com.shine.security.strategy;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shine.common.exception.SecurityException;
import com.shine.common.exception.BaseException;
import com.shine.common.response.Result;
import com.shine.common.status.ResponseStatus;
import com.shine.security.authorization.impl.AuthorityPrincipal;
import com.shine.security.constant.SecurityConstant;
import com.shine.security.context.SecurityContextHolder;
import com.shine.security.entity.AccessToken;
import com.shine.security.entity.Client;
import com.shine.security.entity.RefreshToken;
import com.shine.security.http.SecurityStatus;
import com.shine.security.manager.AsyncManager;
import com.shine.security.mapper.ClientMapper;
import com.shine.security.password.PasswordEncoder;
import com.shine.security.properties.SecurityProperties;
import com.shine.security.request.RefreshTokenRequest;
import com.shine.security.response.AccessTokenResponse;
import com.shine.security.service.RefreshTokenService;
import com.shine.security.token.TokenManager;
import com.shine.user.feign.UserFeign;
import com.shine.user.request.UserRequest;
import com.shine.user.response.UserPermissionResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author huihui
 * @date 2024/12/5 11:29
 * @description RefreshTokenStrategy
 */
@Slf4j
@Component
public class RefreshTokenStrategy implements AuthorizationStrategy<RefreshTokenRequest> {

    @Resource
    private ClientMapper clientMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Resource
    private UserFeign userFeign;

    @Resource
    private SecurityProperties properties;

    @Resource
    private RefreshTokenService refreshTokenService;

    @Resource
    private AsyncManager asyncManager;

    @Override
    public AccessTokenResponse token(RefreshTokenRequest request) {
        log.info("使用刷新令牌获取Token：{}", request.getRefreshToken());
        String clientId = request.getClientId();
        String clientSecret = request.getClientSecret();
        String grantType = request.getGrantType();
        Client client = clientMapper.selectOne(Wrappers.<Client>lambdaQuery().eq(Client::getClientId, clientId));
        // 验证客户端
        if (client == null) {
            throw new BaseException(SecurityStatus.NOT_EXISTS_CLIENT);
        }
        if (!passwordEncoder.matches(clientSecret, client.getClientSecret())) {
            throw new BaseException(SecurityStatus.CLIENT_ID_MISMATCH);
        }
        Long userId = SecurityContextHolder.getContext().getPrincipal().getId();
        // 验证刷新令牌
        Boolean hasKey = redisTemplate.hasKey(SecurityConstant.REFRESH_TOKEN_REDIS_PREFIX + userId);
        if (hasKey == null || !hasKey) {
            log.error("不存在的Key：{}", userId);
            throw new SecurityException(SecurityStatus.NO_REFRESH_TOKEN);
        }
        String refreshToken = redisTemplate.opsForValue().get(SecurityConstant.REFRESH_TOKEN_REDIS_PREFIX + userId);
        if (StringUtils.isBlank(refreshToken)) {
            log.error("不存在的value：{}", userId);
            throw new SecurityException(SecurityStatus.NO_REFRESH_TOKEN);
        }
        // 没有问题 开始重新生成访问令牌了
        RefreshToken dsToken = refreshTokenService.getOne(
                Wrappers.<RefreshToken>lambdaQuery()
                        .eq(RefreshToken::getRefreshToken, refreshToken)
        );
        UserRequest userRequest = new UserRequest();
        userRequest.setUserId(userId);
        Result<UserPermissionResponse> userResult = userFeign.getUser(userRequest);
        if (!userResult.getSuccess()) {
            throw new BaseException(ResponseStatus.ERROR, "未获取到用户");
        }
        UserPermissionResponse user = userResult.getData();
        AuthorityPrincipal principal = new AuthorityPrincipal();
        principal.setClientId(client.getClientId());
        principal.setId(user.getId());
        principal.setUsername(user.getUsername());
        principal.setPassword(user.getPassword());
        principal.setRoleList(user.getRoleList());
        principal.setPermissionList(user.getPermissionList());
        AuthorityPrincipal refreshTokenPrincipal = new AuthorityPrincipal();
        refreshTokenPrincipal.setClientId(client.getClientId());
        refreshTokenPrincipal.setId(user.getId());
        refreshTokenPrincipal.setUsername(user.getUsername());
        // 客户端访问令牌过期时间
        Integer expireIn = properties.getClientAccessTokenExpireSeconds();
        // 客户端刷新令牌过期时间
        Integer refreshExpireIn = properties.getClientRefreshTokenExpireSeconds();
        String accessToken = TokenManager.generate(principal, expireIn);
        refreshToken = TokenManager.generate(refreshTokenPrincipal, refreshExpireIn);
        AccessTokenResponse response = new AccessTokenResponse();
        response.setAccessToken(accessToken);
        response.setTokenType("Bearer");
        response.setExpiresIn(expireIn);
        response.setRefreshToken(refreshToken);
        response.setScope(dsToken.getScope());

        // 记录token
        AccessToken saveToken = new AccessToken();
        saveToken.setClientId(clientId);
        saveToken.setAccessToken(accessToken);
        saveToken.setUserId(userId);
        saveToken.setGrantType(grantType);
        saveToken.setScope(dsToken.getScope());
        saveToken.setTokenType("Bearer");
        saveToken.setRedirectUri(dsToken.getRedirectUri());
        saveToken.setExpireTime(LocalDateTime.now().plusSeconds(expireIn));
        saveToken.setCreateUser(userId);
        saveToken.setUpdateUser(userId);
        saveToken.setRemark("使用授权码生成的令牌");

        RefreshToken saveRefreshToken = new RefreshToken();
        saveRefreshToken.setClientId(clientId);
        saveRefreshToken.setRefreshToken(refreshToken);
        saveRefreshToken.setRedirectUri(dsToken.getRedirectUri());
        saveRefreshToken.setUserId(userId);
        saveRefreshToken.setExpireTime(LocalDateTime.now().plusSeconds(refreshExpireIn));
        saveRefreshToken.setUsed(false);
        saveRefreshToken.setScope(dsToken.getScope());
        saveRefreshToken.setCreateTime(LocalDateTime.now());
        saveRefreshToken.setCreateUser(userId);
        saveRefreshToken.setUpdateTime(LocalDateTime.now());
        saveRefreshToken.setUpdateUser(userId);

        // 保存令牌到Redis
        redisTemplate.opsForValue().set(SecurityConstant.ACCESS_TOKEN_REDIS_PREFIX + userId, accessToken, properties.getClientAccessTokenExpireSeconds(), TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(SecurityConstant.REFRESH_TOKEN_REDIS_PREFIX + userId, refreshToken, properties.getClientRefreshTokenExpireSeconds(), TimeUnit.SECONDS);

        // 保存令牌
        asyncManager.saveToken(saveToken, saveRefreshToken);
        return response;
    }

}
