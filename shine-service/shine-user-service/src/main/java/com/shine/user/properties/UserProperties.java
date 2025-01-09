package com.shine.user.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author huihui
 * @date 2024/11/9 20:05
 * @description UserProperties
 */
@Data
@Component
@ConfigurationProperties(prefix = "user")
public class UserProperties {

    /**
     * 测试获取配置
     */
    private String test;

}
