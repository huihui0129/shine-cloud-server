package com.hh.generator.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author huihui
 * @date 2024/11/18 16:55
 * @description GeneratorConfig
 */
@Data
@Component
@ConfigurationProperties(prefix = "generator")
public class GeneratorProperties {

    private Datasource datasource;

    @Data
    public static class Datasource {

        private String url;

        private String username;

        private String password;

    }

}
