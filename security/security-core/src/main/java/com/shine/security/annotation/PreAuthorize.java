package com.shine.security.annotation;

import java.lang.annotation.*;

/**
 * @author huihui
 * @date 2024/12/30 13:23
 * @description PreAuthorize
 * @see com.shine.security.utils.SecurityAuthentication
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PreAuthorize {

    /**
     * 权限表达式
     * hasRole
     * hasAuthority
     *
     * @return
     */
    String value();

}
