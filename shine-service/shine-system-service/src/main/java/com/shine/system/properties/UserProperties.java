package com.shine.system.properties;

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
@ConfigurationProperties(prefix = "system")
public class UserProperties {

}
