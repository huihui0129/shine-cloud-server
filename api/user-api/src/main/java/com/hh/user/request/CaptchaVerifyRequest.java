package com.hh.user.request;

import lombok.Data;

/**
 * @author huihui
 * @date 2024/11/14 10:04
 * @description CaptchaVerifyRequest
 */
@Data
public class CaptchaVerifyRequest {

    private String uuid;

    private String captcha;

}
