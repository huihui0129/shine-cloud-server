package com.shine.security.response;

import lombok.Data;

/**
 * @author huihui
 * @date 2024/11/14 14:05
 * @description UserLoginResponse
 */
@Data
public class UserLoginResponse {

    private Long id;

    private String username;

    private String url;

    private String token;

}
