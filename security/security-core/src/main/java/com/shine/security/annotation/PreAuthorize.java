package com.shine.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author huihui
 * @date 2024/12/30 13:23
 * @description PreAuthorize
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
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
