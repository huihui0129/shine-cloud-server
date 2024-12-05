package com.shine.security.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClientScopeInfo {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "客户点ID")
    private String clientId;

    @Schema(description = "授权范围")
    private String scope;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "创建人")
    private Long createUser;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    @Schema(description = "修改人")
    private Long updateUser;

    @Schema(description = "逻辑删除")
    private Integer deleted;

    @Schema(description = "备注")
    private String remark;

}