package com.shine.security.strategy;

import com.shine.security.request.AccessTokenRequest;
import com.shine.security.response.AccessTokenResponse;
import com.shine.security.response.AuthorizeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author huihui
 * @date 2024/11/27 18:42
 * @description AuthorizationContext
 */
@Data
@AllArgsConstructor
public class AuthorizationContext<T extends AccessTokenRequest> {

    private AuthorizationStrategy<T> authorizationStrategy;

    public AuthorizeResponse authorize(String responseType, String clientId, String redirectUri, String scope, String state) {
        return authorizationStrategy.authorize(responseType, clientId, redirectUri, scope, state);
    }

    public AccessTokenResponse token(T request) {
        return authorizationStrategy.token(request);
    }

}
