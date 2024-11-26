package com.hh.security.constant;

import com.hh.common.constant.BaseConstant;

/**
 * @author huihui
 * @date 2024/11/14 18:14
 * @description SecurityConstant
 */
public interface SecurityConstant extends BaseConstant {

    String HEADER_TOKEN_KEY = "Authorization";

    String HEADER_TOKEN_PREFIX = "Bearer ";

    String TOKEN_REDIS_PREFIX = SECURITY_SERVICE + "auth:token:";

    String USER_REDIS_PREFIX = SECURITY_SERVICE + "auth:user:";

    long AUTH_EXPIRE_TIME_SECONDS = 1440L;

}
