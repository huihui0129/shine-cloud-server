package com.hh.security.service.impl;

import com.hh.common.exception.BaseException;
import com.hh.security.enums.AuthorizationResponseTypeEnum;
import com.hh.security.http.AuthorityStatus;
import com.hh.security.request.AuthorizationTokenRequest;
import com.hh.security.response.AccessTokenResponse;
import com.hh.security.response.AuthorizeResponse;
import com.hh.security.service.AuthorizationService;
import com.hh.security.strategy.AuthorizationCodeStrategy;
import com.hh.security.strategy.AuthorizationContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author huihui
 * @date 2024/11/27 18:09
 * @description AuthorizationServiceImpl
 */
@Slf4j
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Resource
    private AuthorizationCodeStrategy authorizationCodeStrategy;

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
    @Override
    public AuthorizeResponse authorize(String responseType, String clientId, String redirectUri, String scope, String state) {
        AuthorizationResponseTypeEnum typeEnum = AuthorizationResponseTypeEnum.findByCode(responseType);
        AuthorizationContext context;
        switch (typeEnum) {
            case CODE: // 授权码模式
                context = new AuthorizationContext(authorizationCodeStrategy);
                break;
            default:
                throw new BaseException(AuthorityStatus.AUTH_MODE_ERROR);
        }
        return context.authorize(responseType, clientId, redirectUri, scope, state);
    }

    /**
     * 获取访问令牌
     *
     * @param request
     * @return
     */
    @Override
    public AccessTokenResponse token(AuthorizationTokenRequest request) {
        // TODO 需要存入表然后查询授权类型
        AuthorizationResponseTypeEnum typeEnum = AuthorizationResponseTypeEnum.findByCode(request.getGrantType());
        AuthorizationContext context;
        switch (typeEnum) {
            case CODE: // 授权码模式
                context = new AuthorizationContext(authorizationCodeStrategy);
                break;
            default:
                throw new BaseException(AuthorityStatus.AUTH_MODE_ERROR);
        }
        return context.token(request.getClientId(), request.getClientSecret(), request.getGrantType(), request.getCode(), request.getRefreshToken());
    }
}
