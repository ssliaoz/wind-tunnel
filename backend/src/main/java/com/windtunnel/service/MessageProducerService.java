package com.windtunnel.service;

import com.windtunnel.config.MessageQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息生产者服务
 * 
 * 用于向消息队列发送消息
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Service
public class MessageProducerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送实时数据到消息队列
     * 
     * @param data 要发送的数据
     */
    public void sendRealTimeData(Object data) {
        log.info("发送实时数据到消息队列: {}", data);
        rabbitTemplate.convertAndSend(
            MessageQueueConfig.DATA_COLLECTION_EXCHANGE,
            MessageQueueConfig.DATA_COLLECTION_ROUTING_KEY,
            data
        );
        log.info("实时数据已发送到消息队列");
    }

    /**
     * 发送通知消息
     * 
     * @param message 通知消息
     * @param routingKey 路由键
     */
    public void sendNotification(Object message, String routingKey) {
        log.info("发送通知消息到队列: {}, 路由键: {}", message, routingKey);
        rabbitTemplate.convertAndSend(
            MessageQueueConfig.DATA_COLLECTION_EXCHANGE,
            routingKey,
            message
        );
        log.info("通知消息已发送到队列");
    }

}