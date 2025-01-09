package com.shine.async.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author huihui
 * @date 2024/11/26 13:20
 * @description ThreadPoolProperties
 */
@Data
@ConfigurationProperties(prefix = "async.thread")
public class ThreadPoolProperties {

    /**
     * 核心线程数
     */
    private int corePoolSize = 10;

    /**
     * 最大线程数
     */
    private int maxPoolSize = 20;
    private int queueCapacity = 100;
    private int keepAliveSeconds = 60;
    private String threadNamePrefix = "Async:";



}
