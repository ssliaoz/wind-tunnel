package com.windtunnel.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列配置类
 * 
 * 配置RabbitMQ和Kafka消息队列
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Configuration
public class MessageQueueConfig {

    // RabbitMQ配置
    public static final String DATA_COLLECTION_QUEUE = "data.collection.queue";
    public static final String DATA_COLLECTION_EXCHANGE = "data.collection.exchange";
    public static final String DATA_COLLECTION_ROUTING_KEY = "data.collection.routing.key";

    /**
     * 创建数据收集队列
     * 
     * @return 队列实例
     */
    @Bean
    public Queue dataCollectionQueue() {
        return new Queue(DATA_COLLECTION_QUEUE, true);
    }

    /**
     * 创建数据收集交换机
     * 
     * @return 直连交换机实例
     */
    @Bean
    public DirectExchange dataCollectionExchange() {
        return new DirectExchange(DATA_COLLECTION_EXCHANGE);
    }

    /**
     * 绑定队列和交换机
     * 
     * @param queue 队列
     * @param exchange 交换机
     * @return 绑定实例
     */
    @Bean
    public Binding dataCollectionBinding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(DATA_COLLECTION_ROUTING_KEY);
    }

    /**
     * 配置消息转换器
     * 
     * @return 消息转换器
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 配置RabbitTemplate
     * 
     * @param connectionFactory 连接工厂
     * @return RabbitTemplate实例
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    // Kafka配置相关的Bean将在需要时添加
}