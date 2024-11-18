package com.hh.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户角色 新增请求对象
 *
 * @author default
 * @date 2024-11-18
 */
@Data
public class UserRoleAddRequest {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "角色ID")
    private Long roleId;

    @Schema(description = "逻辑删除")
    private Boolean deleted;

}
