package com.windtunnel.service;

import com.windtunnel.config.MessageQueueConfig;
import com.windtunnel.entity.RealTimeData;
import com.windtunnel.repository.RealTimeDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * 消息消费者服务
 * 
 * 用于从消息队列接收和处理消息
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Service
public class MessageConsumerService {

    @Autowired
    private RealTimeDataRepository realTimeDataRepository;

    /**
     * 消费实时数据消息
     * 
     * @param realTimeData 实时数据
     */
    @RabbitListener(queues = MessageQueueConfig.DATA_COLLECTION_QUEUE)
    public void consumeRealTimeData(@NonNull RealTimeData realTimeData) {
        log.info("接收到实时数据消息: {}", realTimeData);
        
        try {
            // 保存实时数据到数据库
            realTimeDataRepository.save(realTimeData);
            log.info("实时数据已保存到数据库，ID: {}", realTimeData.getId());
            
            // 进行数据处理和分析
            processData(realTimeData);
        } catch (Exception e) {
            log.error("处理实时数据消息失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 处理接收到的数据
     * 
     * @param realTimeData 实时数据
     */
    private void processData(@NonNull RealTimeData realTimeData) {
        // 在这里可以添加数据验证、异常检测等逻辑
        log.info("正在处理实时数据，来源: {}, 时间: {}", realTimeData.getSource(), realTimeData.getDataTime());
        
        // 示例：检查数据异常
        if (realTimeData.getWindSpeed() != null && realTimeData.getWindSpeed().compareTo(new java.math.BigDecimal("150")) > 0) {
            log.warn("检测到异常数据: 风速过高 - {}", realTimeData.getWindSpeed());
        }
    }

    /**
     * 消费通知消息
     * 
     * @param message 通知消息
     */
    @RabbitListener(queues = "notification.queue")
    public void consumeNotification(Object message) {
        log.info("接收到通知消息: {}", message);
        // 处理通知消息的逻辑
    }

}