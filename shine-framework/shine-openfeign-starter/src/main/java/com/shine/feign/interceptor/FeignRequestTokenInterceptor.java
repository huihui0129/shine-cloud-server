package com.shine.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignRequestTokenInterceptor implements RequestInterceptor {

    /**
     * 将Token传递给下游服务
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        // 获取当前请求上下文
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest currentRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
            // 从当前请求的头中获取 token
            String token = currentRequest.getHeader("Authorization");
            if (StringUtils.isNotBlank(token)) {
                // 将 token 添加到 Feign 请求的头中
                template.header("Authorization", token);
            }
        }
    }

}
