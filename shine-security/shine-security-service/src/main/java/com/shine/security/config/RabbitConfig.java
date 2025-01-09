package com.shine.security.config;

import com.shine.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huihui
 * @date 2024/12/2 15:52
 * @description RabbitConfig
 */
@Configuration
public class RabbitConfig {

    /**
     * 正常队列配置
     *
     * @return
     */
    @Bean
    public Queue authorizationCodeQueue() {
        return QueueBuilder.durable(RabbitConstant.Security.AUTHORIZATION_CODE_QUEUE)
                .withArgument("x-dead-letter-exchange", RabbitConstant.Security.AUTHORIZATION_CODE_DIE_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", RabbitConstant.Security.AUTHORIZATION_CODE_DIE_KEY)
                .withArgument("x-message-ttl", 60000) // 过期时间一分钟
                .build();
    }

    @Bean
    public DirectExchange authorizationCodeExchange() {
        return new DirectExchange(RabbitConstant.Security.AUTHORIZATION_CODE_EXCHANGE);
    }

    @Bean
    public Binding authorizationCodeBind() {
        return BindingBuilder.bind(authorizationCodeQueue()).to(authorizationCodeExchange()).with(RabbitConstant.Security.AUTHORIZATION_CODE_KEY);
    }

    /**
     * 死信队列
     */
    @Bean
    public Queue authorizationCodeDieQueue() {
        return new Queue(RabbitConstant.Security.AUTHORIZATION_CODE_DIE_QUEUE, true);
    }

    @Bean
    public DirectExchange authorizationCodeDieExchange() {
        return new DirectExchange(RabbitConstant.Security.AUTHORIZATION_CODE_DIE_EXCHANGE);
    }

    @Bean
    public Binding authorizationCodeDieBind() {
        return BindingBuilder.bind(authorizationCodeDieQueue()).to(authorizationCodeDieExchange()).with(RabbitConstant.Security.AUTHORIZATION_CODE_DIE_KEY);
    }

}
