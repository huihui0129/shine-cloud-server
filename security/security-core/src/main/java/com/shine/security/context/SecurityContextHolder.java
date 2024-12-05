package com.shine.security.context;

/**
 * @author huihui
 * @date 2024/11/22 14:25
 * @description SecurityContextHolder
 */
public class SecurityContextHolder {

    private static final ThreadLocal<SecurityContext> contextHolder = new ThreadLocal<>();

    private static SecurityContext getContext() {
        return contextHolder.get();
    }

    public static void setContext(SecurityContext context) {
        contextHolder.set(context);
    }

    public static void clearContext() {
        contextHolder.remove();
    }

}
