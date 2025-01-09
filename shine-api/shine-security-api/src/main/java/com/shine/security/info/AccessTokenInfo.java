package com.shine.security.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccessTokenInfo {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "客户端ID")
    private String clientId;

    @Schema(description = "访问令牌")
    private String accessToken;

    @Schema(description = "刷新令牌")
    private String refreshToken;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "授权类型")
    private String grantType;

    @Schema(description = "授权范围")
    private String scope;

    @Schema(description = "token类型")
    private String tokenType;

    @Schema(description = "重定向地址")
    private String redirectUri;

    @Schema(description = "访问令牌过期时间")
    private LocalDateTime accessTokenExpireTime;

    @Schema(description = "刷新令牌过期时间")
    private LocalDateTime refreshTokenExpireTime;

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