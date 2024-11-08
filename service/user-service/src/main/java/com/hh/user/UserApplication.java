package com.hh.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author huihui
 * @date 2024/10/31 16:49
 * @description UserApplication
 */
@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
@EnableFeignClients
@ComponentScan(basePackages = { "com.hh.common", "com.hh.user" })
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
