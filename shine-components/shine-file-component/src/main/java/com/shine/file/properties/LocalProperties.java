package com.shine.file.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author huihui
 * @date 2025/1/22 14:08
 * @description LocalProperties
 */
@Data
@Component
@ConfigurationProperties(prefix = "file")
public class LocalProperties {

    private String localFilePath = "G:/upload";

}
