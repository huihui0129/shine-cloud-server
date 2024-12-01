package com.hh.security.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author huihui
 * @date 2024/11/28 22:35
 * @description AuthorizeTokenResponse
 */
@Data
public class AccessTokenResponse {

    @Schema(description = "访问令牌")
    private String accessToken;

    @Schema(description = "令牌类型")
    private String tokenType;

    @Schema(description = "过期时间")
    private Long expiresIn;

    @Schema(description = "刷新令牌")
    private String refreshToken;

    @Schema(description = "授权范围")
    private String scope;

}
