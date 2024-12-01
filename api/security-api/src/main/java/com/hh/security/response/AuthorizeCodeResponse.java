package com.hh.security.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author huihui
 * @date 2024/11/27 18:05
 * @description AuthorizeResponse
 */
@Data
public class AuthorizeCodeResponse extends AuthorizeResponse {

    @Schema(description = "授权码")
    private String code;

}
