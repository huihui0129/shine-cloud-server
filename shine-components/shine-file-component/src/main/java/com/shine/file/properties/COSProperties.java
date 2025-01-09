package com.shine.file.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author huihui
 * @date 2024/11/5 13:07
 * @description COSProperties
 */
@Data
@Component
@ConfigurationProperties(prefix = "cos")
public class COSProperties {

    private String secretId;

    private String secretKey;

    private String appId;

    private String bucketName;

    private String region;

    private String url;

    private String prefix;

}
