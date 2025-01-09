package com.shine.security.strategy;

import com.shine.common.exception.BaseException;
import com.shine.security.http.SecurityStatus;
import com.shine.security.request.AccessTokenRequest;
import com.shine.security.response.AccessTokenResponse;
import com.shine.security.response.AuthorizeResponse;

/**
 * @author huihui
 * @date 2024/11/27 18:38
 * @description AuthorizationStrategy
 */
public interface AuthorizationStrategy<T extends AccessTokenRequest> {

    default AuthorizeResponse authorize(String responseType, String clientId, String redirectUri, String scope, String state) {
        throw new BaseException(SecurityStatus.AUTHORIZATION_DEFAULT_ACT);
    }

    AccessTokenResponse token(T request);

}
