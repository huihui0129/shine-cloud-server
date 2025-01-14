package com.shine.system.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 菜单 新增请求对象
 *
 * @author default
 * @date 2024-11-18
 */
@Data
public class MenuAddRequest {

    @Schema(description = "应用ID")
    private Long appId;

    @Schema(description = "父ID")
    private Long parentId;

    @Schema(description = "菜单名称")
    private String menuName;

    @Schema(description = "菜单路由")
    private String path;

    @Schema(description = "权限")
    private String permission;

    @Schema(description = "类型 0：菜单 1：按钮")
    private Integer type;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "逻辑删除")
    private Boolean deleted;

}
