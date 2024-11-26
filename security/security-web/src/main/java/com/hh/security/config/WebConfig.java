package com.hh.security.config;

import com.hh.security.interceptor.SecurityContextInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author huihui
 * @date 2024/11/22 16:53
 * @description WebConfig
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityContextInterceptor())
                .addPathPatterns("/**");
    }

}
