package com.shine.security.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author huihui
 * @date 2025/1/10 17:19
 * @description OnlineUserResponse
 */
@Data
public class OnlineUserResponse {

    @Schema(description = "所属服务")
    private String service;

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "头像")
    private String headImage;

}
