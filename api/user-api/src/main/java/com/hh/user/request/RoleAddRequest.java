package com.hh.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 角色 新增请求对象
 *
 * @author default
 * @date 2024-11-18
 */
@Data
public class RoleAddRequest {

    @Schema(description = "应用ID")
    private Long appId;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "逻辑删除")
    private Boolean deleted;

}
