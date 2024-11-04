package com.hh.user.info;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author huihui
 * @date 2024/11/4 10:30
 * @description UserInfo
 */
public class UserInfo {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

}
