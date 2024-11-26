package com.hh.security.properties;

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

}
