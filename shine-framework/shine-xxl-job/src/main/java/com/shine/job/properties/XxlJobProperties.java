package com.shine.job.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author huihui
 * @date 2025/1/14 11:00
 * @description XxlJobProperties
 */
@Data
@ConfigurationProperties(prefix = "xxl-job")
public class XxlJobProperties {

    private String accessToken;
    private Admin admin;
    private Executor executor;

    @Data
    public static class Admin {
        private String addresses;
    }

    @Data
    public static class Executor {
        private String appName;
        private String address;
        private String ip;
        private Integer port;
        private String logPath;
        private Integer logRetentionDays;
    }

}
