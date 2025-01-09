package com.shine.security.constant;

import com.shine.common.constant.BaseConstant;

/**
 * @author huihui
 * @date 2024/11/14 18:14
 * @description SecurityConstant
 */
public interface SecurityConstant extends BaseConstant {

    String HEADER_TOKEN_KEY = "Authorization";

    String HEADER_TOKEN_PREFIX = "Bearer ";

    String TOKEN_REDIS_PREFIX = SECURITY_SERVICE + "token:";

    String ACCESS_TOKEN_REDIS_PREFIX = SECURITY_SERVICE + "access:token:";

    String REFRESH_TOKEN_REDIS_PREFIX = SECURITY_SERVICE + "refresh:token:";

    String OFFLINE_REDIS_PREFIX = SECURITY_SERVICE + "status:offline:";

    String USER_REDIS_PREFIX = SECURITY_SERVICE + "user:";

    long AUTH_EXPIRE_TIME_SECONDS = 1440L;

    /**
     * 验证码过期时间，单位：秒
     */
    long CAPTCHA_CATCH_TIMEOUT_SECONDS = 60L;

    /**
     * 图片验证码前缀
     */
    String CAPTCHA_CATCH_KEY_PREFIX = USER_SERVICE + "captcha:";

}
