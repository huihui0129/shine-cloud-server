package com.hh.user.response;

import lombok.Data;

/**
 * @author huihui
 * @date 2024/11/13 13:28
 * @description CaptchaModel
 */
@Data
public class CaptchaResponse {

    private String uuid;

    private String imageBase64;

}