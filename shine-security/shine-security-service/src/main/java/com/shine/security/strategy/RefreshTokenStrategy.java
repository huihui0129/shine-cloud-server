package com.shine.security.strategy;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shine.common.exception.BaseException;
import com.shine.security.authorization.impl.AuthorityPrincipal;
import com.shine.security.entity.Client;
import com.shine.security.http.AuthorityStatus;
import com.shine.security.mapper.ClientMapper;
import com.shine.security.password.PasswordEncoder;
import com.shine.security.request.RefreshTokenRequest;
import com.shine.security.response.AccessTokenResponse;
import com.shine.security.token.TokenManager;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

    @Override
    public AccessTokenResponse token(RefreshTokenRequest request) {
        AuthorityPrincipal refreshTokenPrincipal = TokenManager.parse(request.getRefreshToken());
        log.info("使用刷新令牌获取Token：{}，RefreshToken：{}", refreshTokenPrincipal.getUsername(), request.getRefreshToken());
        String clientId = request.getClientId();
        String clientSecret = request.getClientSecret();
        String grantType = request.getGrantType();
        Client client = clientMapper.selectOne(Wrappers.<Client>lambdaQuery().eq(Client::getClientId, clientId));
        // 验证客户端
        if (client == null) {
            throw new BaseException(AuthorityStatus.NOT_EXISTS_CLIENT);
        }
        if (!passwordEncoder.matches(clientSecret, client.getClientSecret())) {
            throw new BaseException(AuthorityStatus.CLIENT_ID_MISMATCH);
        }
        return null;
    }

}
