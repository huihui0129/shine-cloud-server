package com.hh.security.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author huihui
 * @date 2024/11/27 18:05
 * @description AuthorizeResponse
 */
@Data
public class AuthorizeResponse {

    @Schema(description = "重定向地址")
    private String redirectUri;

    @Schema(description = "授权范围")
    private String scope;

    @Schema(description = "自定义字符串")
    private String state;

}
