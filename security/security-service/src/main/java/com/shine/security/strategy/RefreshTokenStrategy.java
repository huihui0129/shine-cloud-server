package com.shine.security.strategy;

import com.shine.security.response.AccessTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author huihui
 * @date 2024/12/5 11:29
 * @description RefreshTokenStrategy
 */
@Slf4j
@Component
public class RefreshTokenStrategy implements AuthorizationStrategy {

    @Override
    public AccessTokenResponse token(String clientId, String clientSecret, String grantType, String code, String refreshToken) {
        return null;
    }

}
