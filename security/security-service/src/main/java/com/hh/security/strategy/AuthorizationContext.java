package com.hh.security.strategy;

import com.hh.security.response.AuthorizeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author huihui
 * @date 2024/11/27 18:42
 * @description AuthorizationContext
 */
@Data
@AllArgsConstructor
public class AuthorizationContext {

    private AuthorizationStrategy authorizationStrategy;

    public AuthorizeResponse authorize(String responseType, String clientId, String redirectUri, String scope, String state) {
        return authorizationStrategy.authorize(responseType, clientId, redirectUri, scope, state);
    }

}
