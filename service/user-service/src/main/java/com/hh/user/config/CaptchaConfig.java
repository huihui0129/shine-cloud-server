package com.hh.user.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author huihui
 * @date 2024/11/13 13:53
 * @description CaptchaConfig
 */
@Configuration
public class CaptchaConfig {

    @Bean
    public DefaultKaptcha captchaProducer() {
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties properties = new Properties();

        // 设置边框颜色
        properties.setProperty("kaptcha.border", "yes");
        properties.setProperty("kaptcha.border.color", "105,179,90");  // 边框颜色

        // 设置图片大小
        properties.setProperty("kaptcha.image.width", "160");
        properties.setProperty("kaptcha.image.height", "60");

        // 设置文本颜色（随机色）
        properties.setProperty("kaptcha.textproducer.font.color", "blue");  // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.size", "40");

        // 设置验证码字符长度
        properties.setProperty("kaptcha.textproducer.char.length", "5");

        // 设置字符间距
        properties.setProperty("kaptcha.textproducer.char.space", "5");

        // 设置背景色（渐变效果）
        properties.setProperty("kaptcha.background.clear.from", "lightGray");
        properties.setProperty("kaptcha.background.clear.to", "white");

        // 设置干扰线颜色
        properties.setProperty("kaptcha.noise.color", "red");

        // 设置字体
        properties.setProperty("kaptcha.textproducer.font.names", "Arial, Courier, Times New Roman");

        // 设置会话key（验证时使用）
        properties.setProperty("kaptcha.session.key", "captchaCode");

        kaptcha.setConfig(new Config(properties));
        return kaptcha;
    }

}
