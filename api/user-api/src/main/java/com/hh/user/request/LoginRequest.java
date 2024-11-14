package com.hh.user.request;

import lombok.Data;

/**
 * @author huihui
 * @date 2024/11/14 14:04
 * @description LoginRequest
 */
@Data
public class LoginRequest {

    private String username;

    private String password;

    private String uuid;

    private String captcha;

}
