package com.shine.common.context;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author huihui
 * @date 2024/12/3 15:43
 * @description ShineRequestContext
 */
public class ShineRequestContext {

    private static final String TOKEN_HEADER_KEY = "Authorization";

    /**
     * 获取当前的请求
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }

    /**
     * 获取请求Token
     *
     * @return
     */
    public static String getToken() {
        HttpServletRequest request = getRequest();
        String token = request.getHeader(TOKEN_HEADER_KEY);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        if (token.startsWith("Bearer ")) {
            return token.replace("Bearer ", "");
        }
        return "";
    }

}
