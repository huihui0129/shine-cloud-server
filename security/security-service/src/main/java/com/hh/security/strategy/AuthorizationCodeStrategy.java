package com.hh.security.strategy;

import com.hh.security.response.AuthorizeResponse;

/**
 * @author huihui
 * @date 2024/11/27 18:42
 * @description AuthorizationCodeStrategy 授权码模式
 */
public class AuthorizationCodeStrategy implements AuthorizationStrategy {

    @Override
    public AuthorizeResponse authorize(String responseType, String clientId, String redirectUri, String scope, String state) {
        return null;
    }

}
