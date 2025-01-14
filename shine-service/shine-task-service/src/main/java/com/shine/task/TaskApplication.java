package com.shine.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author huihui
 * @date 2025/1/14 11:37
 * @description TaskApplication
 */
@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
@ComponentScan(basePackages = { "com.shine" })
public class TaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }

}
