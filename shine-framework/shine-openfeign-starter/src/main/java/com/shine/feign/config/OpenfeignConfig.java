package com.shine.feign.config;

import com.shine.feign.interceptor.FeignRequestTokenInterceptor;
import feign.Feign;
import feign.Request;
import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author huihui
 * @date 2024/11/9 20:15
 * @description OpenfeignConfig
 */
@Configuration
@EnableFeignClients(basePackages = "com.shine.*.*")
public class OpenfeignConfig {

    @Bean
    public Feign.Builder feignBuilder() {
        // 连接超时2秒，读取超时5秒
        return Feign.builder().options(new Request.Options(2, TimeUnit.SECONDS, 5, TimeUnit.SECONDS, true));
    }

    @Bean
    public RequestInterceptor oauthFeignRequestInterceptor() {
        return new FeignRequestTokenInterceptor();
    }

}