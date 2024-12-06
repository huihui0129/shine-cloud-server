package com.shine.security.strategy;

import com.shine.security.request.RefreshTokenRequest;
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
public class RefreshTokenStrategy implements AuthorizationStrategy<RefreshTokenRequest> {

    @Override
    public AccessTokenResponse token(RefreshTokenRequest request) {
        return null;
    }

}
