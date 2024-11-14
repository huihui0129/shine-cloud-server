package com.hh.security.config;

import com.hh.security.password.PasswordEncoder;
import com.hh.security.password.impl.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huihui
 * @date 2024/11/12 17:03
 * @description PasswordEncode
 */
@Configuration
public class PasswordEncodeConfig {

    /**
     * 默认的密码加密器
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
