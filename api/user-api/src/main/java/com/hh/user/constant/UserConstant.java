package com.hh.user.constant;

import com.hh.common.constant.BaseConstant;

/**
 * @author huihui
 * @date 2024/11/14 09:54
 * @description UserConstant
 */
public interface UserConstant extends BaseConstant {

    /**
     * 验证码过期时间，单位：秒
     */
    long CAPTCHA_CATCH_TIMEOUT_SECONDS = 60L;

    /**
     * 图片验证码前缀
     */
    String CAPTCHA_CATCH_KEY_PREFIX = USER_SERVICE + "captcha:";

}
