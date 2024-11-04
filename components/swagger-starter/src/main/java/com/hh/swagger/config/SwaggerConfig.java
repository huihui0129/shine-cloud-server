package com.hh.swagger.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huihui
 * @date 2024/11/4 09:35
 * @description SwaggerConfig
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI();
        Info info = new Info();
        info.setTitle("Swagger接口文档");
        info.setDescription("SpringBoot3集成Swagger3接口文档");
        info.setVersion("1.0.0");

        ExternalDocumentation documentation = new ExternalDocumentation();
        documentation.setDescription("项目API文档");
        documentation.setUrl("/");

        openAPI.setInfo(info);
        openAPI.setExternalDocs(documentation);
        return openAPI;
    }

}
