package com.shine.security.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author huihui
 * @date 2025/1/9 14:41
 * @description PasswordTokenRequest
 */
@Data
public class PasswordTokenRequest extends AccessTokenRequest {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

}
