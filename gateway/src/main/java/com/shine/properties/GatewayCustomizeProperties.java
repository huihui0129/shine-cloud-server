package com.shine.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author huihui
 * @date 2024/11/4 11:38
 * @description GatewayProperties
 */
@Data
@Component
@ConfigurationProperties(prefix = "hh.gateway")
public class GatewayCustomizeProperties {

    /**
     * 不需要认证的路径
     */
    private List<String> authorizationExcludePath;

}
