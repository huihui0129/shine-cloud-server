package com.shine.admin.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BootAdminMonitorSecurityConfig {

    private final String adminContextPath;

    public BootAdminMonitorSecurityConfig(AdminServerProperties adminServerProperties) {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

}