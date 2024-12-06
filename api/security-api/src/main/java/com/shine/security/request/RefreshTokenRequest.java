package com.shine.security.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author huihui
 * @date 2024/12/6 14:28
 * @description RefreshTokenRequest
 */
@Data
public class RefreshTokenRequest extends AccessTokenRequest {

    @Schema(description = "刷新令牌")
    private String refreshToken;

}
