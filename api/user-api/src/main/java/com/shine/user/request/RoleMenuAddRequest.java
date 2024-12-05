package com.shine.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 角色菜单 新增请求对象
 *
 * @author default
 * @date 2024-11-18
 */
@Data
public class RoleMenuAddRequest {

    @Schema(description = "角色ID")
    private Long roleId;

    @Schema(description = "菜单ID")
    private Long menuId;

    @Schema(description = "逻辑删除")
    private Boolean deleted;

}
