package com.shine.security.request;

import com.shine.mybatis.request.BaseQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthorizationCodePageRequest extends BaseQueryRequest {

    @Schema(description = "客户端ID")
    private String clientId;

    @Schema(description = "授权码")
    private String authorizationCode;

    @Schema(description = "重定向地址")
    private String redirectUri;

    @Schema(description = "授权范围")
    private String scope;

    @Schema(description = "过期时间")
    private LocalDateTime expireAt;

    @Schema(description = "枚举 授权码状态：1-未使用|2-已使用|3-已过期")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

}