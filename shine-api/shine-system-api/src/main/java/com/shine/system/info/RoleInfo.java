package com.shine.system.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;

/**
 * 角色 INFO实体
 *
 * @author default
 * @date 2024-11-18
 */
@Data
public class RoleInfo {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "应用ID")
    private Long appId;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "创建人")
    private Long createUser;

    @Schema(description = "修改时间")
    private Date updateTime;

    @Schema(description = "修改人")
    private Long updateUser;

    @Schema(description = "逻辑删除")
    private Boolean deleted;

    @Schema(description = "备注")
    private String remark;

}
