package com.shine.security.context;

import com.shine.common.exception.BaseException;
import com.shine.security.authorization.Principal;
import com.shine.security.http.SecurityStatus;

/**
 * @author huihui
 * @date 2024/11/22 14:25
 * @description SecurityContextHolder
 */
public class SecurityContextHolder {

    private static final ThreadLocal<SecurityContext<? extends Principal>> contextHolder = new ThreadLocal<>();

    public static SecurityContext<? extends Principal> getContext() {
        SecurityContext<? extends Principal> context = contextHolder.get();
        if (context == null) {
            throw new BaseException(SecurityStatus.EXPIRED_TOKEN);
        }
        return context;
    }

    public static void setContext(SecurityContext<? extends Principal> context) {
        contextHolder.set(context);
    }

    public static void clearContext() {
        contextHolder.remove();
    }

}
