package com.shine.generator.properties;

import com.shine.generator.enums.GeneratorEnum;
import com.shine.generator.enums.MethodEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

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

    private Package packageConfig;

    private Generator generator;

    @Data
    public static class Datasource {

        private String url;
        private String username;
        private String password;

    }

    @Data
    public static class Package {

        private String modalName;
        private String basePackage;

    }

    @Data
    public static class Generator {

        private String database;
        private List<String> tableName;
        private String tablePrefix;
        private List<GeneratorEnum> code;
        private List<MethodEnum> methods;

    }

}
