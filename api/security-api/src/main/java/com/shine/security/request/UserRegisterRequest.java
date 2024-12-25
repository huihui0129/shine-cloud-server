package com.shine.security.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author huihui
 * @date 2024/12/25 16:54
 * @description UserRegisterRequest
 */
@Data
public class UserRegisterRequest {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "头像")
    private String headImage;

    @Schema(description = "备注")
    private String remark;

}
