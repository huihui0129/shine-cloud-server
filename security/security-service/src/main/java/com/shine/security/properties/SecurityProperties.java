package com.shine.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author huihui
 * @date 2024/11/8 13:08
 * @description SecurityProperties
 */
@Data
@Component
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    /**
     * 客户端AccessToken有效期
     */
    private Integer clientAccessTokenExpireSeconds = 3600;

    /**
     * 客户端刷新令牌有效期
     */
    private Integer clientRefreshTokenExpireSeconds = 7200;

}
