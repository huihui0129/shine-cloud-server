package com.hh.swagger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author huihui
 * @date 2024/11/4 10:16
 * @description SwaggerProperties
 */
@Data
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    // 是否开启配置
    private Boolean enable;

    // 标题
    private String title;

    // 描述
    private String description;

    // 版本
    private String version;

    // documentation描述
    private String documentationDescription;

    // url
    private String url;

}
