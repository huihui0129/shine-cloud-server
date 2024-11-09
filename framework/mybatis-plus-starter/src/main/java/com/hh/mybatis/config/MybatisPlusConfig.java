package com.hh.mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author huihui
 * @date 2024/11/9 22:14
 * @description Config
 */
@Configuration
@MapperScan("com.hh.*.mapper")
public class MybatisPlusConfig {

}
