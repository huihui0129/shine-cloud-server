package com.shine.security.controller;

import com.shine.common.response.Result;
import com.shine.security.request.AuthorizationCodeRequest;
import com.shine.security.request.AuthorizationTokenRequest;
import com.shine.security.response.AccessTokenResponse;
import com.shine.security.response.AuthorizeResponse;
import com.shine.security.service.AuthorizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author huihui
 * @date 2024/11/27 18:02
 * @description AuthorizationController
 */
@Slf4j
@Tag(name = "OAuth Controller", description = "OAuth Controller")
@RestController
@RequestMapping("/oauth")
public class AuthorizationController {

    @Resource
    private AuthorizationService authorizationService;

    @Operation(summary = "获取授权码")
    @GetMapping("/authorize")
    public Result<AuthorizeResponse> authorize(
            @RequestParam("response_type") String responseType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam(value = "scope", required = false) String scope,
            @RequestParam(value = "state", required = false) String state
    ) {
        return Result.success(authorizationService.authorize(responseType, clientId, redirectUri, scope, state));
    }

    @Operation(summary = "获取访问令牌")
    @PostMapping("/token")
    public Result<AccessTokenResponse> token(@RequestBody AuthorizationCodeRequest request) {
        return Result.success(authorizationService.token(request));
    }

}
