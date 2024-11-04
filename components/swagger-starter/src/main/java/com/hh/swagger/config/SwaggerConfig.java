package com.hh.swagger.config;

import com.hh.swagger.properties.SwaggerProperties;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huihui
 * @date 2024/11/4 09:35
 * @description SwaggerConfig
 */
@Configuration
public class SwaggerConfig {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI();
        Info info = new Info();
        info.setTitle(swaggerProperties.getTitle());
        info.setDescription(swaggerProperties.getDescription());
        info.setVersion(swaggerProperties.getVersion());

        ExternalDocumentation documentation = new ExternalDocumentation();
        documentation.setDescription(swaggerProperties.getDocumentationDescription());
        documentation.setUrl(swaggerProperties.getUrl());

        openAPI.setInfo(info);
        openAPI.setExternalDocs(documentation);
        return openAPI;
    }

}
