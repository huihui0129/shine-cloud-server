package com.hh.security.config;

import com.hh.security.password.PasswordEncoder;
import com.hh.security.password.impl.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huihui
 * @date 2024/12/3 15:30
 * @description SecretEncodeConfig
 */
@Configuration
public class SecretEncodeConfig {

    @Bean
    public PasswordEncoder secretEncoder() {
        return new BCryptPasswordEncoder();
    }

}
