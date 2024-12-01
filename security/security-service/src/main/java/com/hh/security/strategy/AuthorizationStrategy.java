package com.hh.security.strategy;

import com.hh.security.response.AccessTokenResponse;
import com.hh.security.response.AuthorizeResponse;

/**
 * @author huihui
 * @date 2024/11/27 18:38
 * @description AuthorizationStrategy
 */
public interface AuthorizationStrategy {

    AuthorizeResponse authorize(String responseType, String clientId, String redirectUri, String scope, String state);

    AccessTokenResponse token(String clientId, String clientSecret, String grantType, String code, String refreshToken);

}
