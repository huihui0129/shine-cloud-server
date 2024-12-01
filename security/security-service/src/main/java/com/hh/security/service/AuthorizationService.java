package com.hh.security.service;

import com.hh.security.request.AuthorizationTokenRequest;
import com.hh.security.response.AccessTokenResponse;
import com.hh.security.response.AuthorizeResponse;

/**
 * @author huihui
 * @date 2024/11/27 18:09
 * @description AuthorizationService
 */
public interface AuthorizationService {

    /**
     * 获取授权码
     *
     * @param responseType
     * @param clientId
     * @param redirectUri
     * @param scope
     * @param state
     * @return
     */
    AuthorizeResponse authorize(String responseType, String clientId, String redirectUri, String scope, String state);

    /**
     * 获取访问令牌
     *
     * @param request
     * @return
     */
    AccessTokenResponse token(AuthorizationTokenRequest request);

}
