package com.shine.security.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author huihui
 * @date 2024/12/6 14:27
 * @description AccessTokenRequest
 */
@Data
public abstract class AccessTokenRequest {

    @Schema(description = "授权类型")
    private String grantType;

    @Schema(description = "客户端ID")
    private String clientId;

    @Schema(description = "客户端secret")
    private String clientSecret;

}
