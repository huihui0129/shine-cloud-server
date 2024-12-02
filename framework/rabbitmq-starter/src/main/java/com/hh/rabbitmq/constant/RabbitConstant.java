package com.hh.rabbitmq.constant;

/**
 * @author huihui
 * @date 2024/12/2 15:35
 * @description RabbitConstant
 */
public interface RabbitConstant {

    interface Security {

        /**
         * 授权码检查是否过期
         */
        String AUTHORIZATION_CODE_QUEUE = "auth:code:queue";
        String AUTHORIZATION_CODE_EXCHANGE = "auth:code:exchange";
        String AUTHORIZATION_CODE_KEY = "auth:code:key";
        /**
         * 授权码检查是否过期死信队列
         */
        String AUTHORIZATION_CODE_DIE_QUEUE = "auth:code:die:queue";
        String AUTHORIZATION_CODE_DIE_EXCHANGE = "auth:code:die:exchange";
        String AUTHORIZATION_CODE_DIE_KEY = "auth:code:die:key";

    }

}
