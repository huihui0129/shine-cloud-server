package com.shine.rabbitmq.config;//package com.hh.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * @author 辉辉 2024/12/08 13:26
 */
@Component
@Slf4j
public class RabbitMqConfirmCallback implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    /**
     * 监听消息是否到达exchange
     *
     * @param correlationData
     * @param b
     * @param s
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        if (b) {
            log.info("消息投递成功！消息ID：{}", correlationData.getId());
        } else {
            log.error("消息投递失败！消息ID：{}，错误提示：{}", correlationData.getId(), s);
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        Message message = returnedMessage.getMessage();
        int replyCode = returnedMessage.getReplyCode();
        String replyText = returnedMessage.getReplyText();
        String exchange = returnedMessage.getExchange();
        String routingKey = returnedMessage.getRoutingKey();
        log.warn("消息没有路由到队列，获得返回的消息");
        Map map = byteToObject(message.getBody(), Map.class);
        log.warn("message body: {}", map == null ? "" : map.toString());
        log.warn("replyCode: {}", replyCode);
        log.warn("replyText: {}", replyText);
        log.warn("exchange: {}", exchange);
        log.warn("routingKey: {}", routingKey);
        log.warn("------------> end <------------");

    }

    @SuppressWarnings("unchecked")
    private <T> T byteToObject(byte[] bytes, Class<T> clazz) {
        T t;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            t = (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return t;
    }

}
