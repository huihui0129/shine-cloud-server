package com.shine.security.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author huihui
 * @date 2024/11/28 22:34
 * @description AuthorizationTokenRequest
 */
@Data
public class AuthorizationCodeRequest extends AccessTokenRequest {

    @Schema(description = "授权码")
    private String code;

}
