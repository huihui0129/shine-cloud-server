package com.shine.async.config;

import com.shine.async.contsant.AsyncConstant;
import com.shine.async.properties.ThreadPoolProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author huihui
 * @date 2024/11/26 13:26
 * @description AsyncAutoConfiguration
 */
@Configuration
@EnableAsync
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class AsyncAutoConfiguration {

    private final ThreadPoolProperties properties;

    public AsyncAutoConfiguration(ThreadPoolProperties properties) {
        this.properties = properties;
    }

    @Bean(AsyncConstant.DEFAULT_ASYNC_EXECUTOR)
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getCorePoolSize());
        executor.setMaxPoolSize(properties.getMaxPoolSize());
        executor.setQueueCapacity(properties.getQueueCapacity());
        executor.setKeepAliveSeconds(properties.getKeepAliveSeconds());
        executor.setThreadNamePrefix(properties.getThreadNamePrefix());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
