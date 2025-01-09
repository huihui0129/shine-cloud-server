package com.shine.security.context;

import com.shine.common.exception.BaseException;
import com.shine.security.http.SecurityStatus;

/**
 * @author huihui
 * @date 2024/11/22 14:25
 * @description SecurityContextHolder
 */
public class SecurityContextHolder {

    private static final ThreadLocal<SecurityContext> contextHolder = new ThreadLocal<>();

    public static SecurityContext getContext() {
        SecurityContext context = contextHolder.get();
        if (context == null) {
            throw new BaseException(SecurityStatus.EXPIRED_TOKEN);
        }
        return context;
    }

    public static void setContext(SecurityContext context) {
        contextHolder.set(context);
    }

    public static void clearContext() {
        contextHolder.remove();
    }

}
