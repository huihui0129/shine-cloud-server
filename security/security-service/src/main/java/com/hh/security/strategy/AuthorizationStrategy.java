package com.hh.security.strategy;

import com.hh.common.exception.BaseException;
import com.hh.security.http.AuthorityStatus;
import com.hh.security.response.AccessTokenResponse;
import com.hh.security.response.AuthorizeResponse;

/**
 * @author huihui
 * @date 2024/11/27 18:38
 * @description AuthorizationStrategy
 */
public interface AuthorizationStrategy {

    default AuthorizeResponse authorize(String responseType, String clientId, String redirectUri, String scope, String state) {
        throw new BaseException(AuthorityStatus.AUTHORIZATION_DEFAULT_ACT);
    }

    AccessTokenResponse token(String clientId, String clientSecret, String grantType, String code, String refreshToken);

}
