package com.hh.security.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author huihui
 * @date 2024/11/28 22:34
 * @description AuthorizationTokenRequest
 */
@Data
public class AuthorizationTokenRequest {

    @Schema(description = "授权码")
    private String code;

    @Schema(description = "授权类型")
    private String grantType;

    @Schema(description = "刷新令牌")
    private String refreshToken;

    @Schema(description = "客户端ID")
    private String clientId;

    @Schema(description = "客户端secret")
    private String clientSecret;

}
