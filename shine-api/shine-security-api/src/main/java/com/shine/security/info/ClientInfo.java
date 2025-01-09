package com.shine.security.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClientInfo {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "客户点ID")
    private String clientId;

    @Schema(description = "客户端密钥")
    private String clientSecret;

    @Schema(description = "客户端名称")
    private String clientName;

    @Schema(description = "访问令牌默认失效时间")
    private Integer accessTokenLefetime;

    @Schema(description = "刷新令牌默认失效时间")
    private Integer refreshTokenLefetime;

    @Schema(description = "枚举：客户端状态：0：禁用，1：启用")
    private Integer status;

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