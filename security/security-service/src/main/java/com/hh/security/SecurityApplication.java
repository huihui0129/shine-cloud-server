package com.hh.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author huihui
 * @date 2024/11/27 17:46
 * @description SecurityApplication
 */
@RefreshScope
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = { "com.hh" })
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

}
