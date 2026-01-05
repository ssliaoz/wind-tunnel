package com.windtunnel.tcp;

import com.windtunnel.entity.RealTimeData;
import com.windtunnel.repository.RealTimeDataRepository;
import com.windtunnel.factory.DataParsingStrategyFactory;
import com.windtunnel.factory.AnomalyDetectionStrategyFactory;
import com.windtunnel.strategy.DataParsingStrategy;
import com.windtunnel.strategy.AnomalyDetectionStrategy;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TCP服务器处理器
 * 
 * 处理来自CWT1 PC、CWT2 PC、CWT3 PC、AAWT PC、公共动力系统PC的数据
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Component
public class TcpServerHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private RealTimeDataRepository realTimeDataRepository;

    /**
     * 通道激活时触发
     * 
     * @param ctx 通道处理器上下文
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端连接: {}", ctx.channel().remoteAddress());
        super.channelActive(ctx);
    }

    /**
     * 通道非激活时触发
     * 
     * @param ctx 通道处理器上下文
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端断开连接: {}", ctx.channel().remoteAddress());
        super.channelInactive(ctx);
    }

    /**
     * 读取数据时触发
     * 
     * @param ctx 通道处理器上下文
     * @param msg 接收到的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String data = (String) msg;
        log.info("接收到数据: {}", data);
        
        // 解析数据并保存到数据库
        RealTimeData realTimeData = parseData(data, ctx.channel().remoteAddress().toString());
        if (realTimeData != null) {
            // 保存实时数据
            realTimeDataRepository.save(realTimeData);
            log.info("实时数据已保存，数据ID: {}", realTimeData.getId());
            
            // 检查数据中的异常
            checkForAnomalies(realTimeData);
        }
        
        // 回复确认消息
        ctx.writeAndFlush("数据接收成功\n");
    }

    /**
     * 用户事件触发时
     * 
     * @param ctx 通道处理器上下文
     * @param evt 事件
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.warn("客户端读超时: {}", ctx.channel().remoteAddress());
                ctx.close();
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    /**
     * 发生异常时
     * 
     * @param ctx 通道处理器上下文
     * @param cause 异常原因
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("发生异常: {}", cause.getMessage(), cause);
        ctx.close();
    }

    /**
     * 解析接收到的数据
     * 
     * @param data 原始数据
     * @param clientAddress 客户端地址
     * @return 解析后的实时数据对象
     */
    private RealTimeData parseData(String data, String clientAddress) {
        try {
            // 使用策略模式获取对应的数据解析策略
            DataParsingStrategy strategy = DataParsingStrategyFactory.getStrategy(clientAddress);
            if (strategy != null) {
                return strategy.parseData(data, clientAddress);
            } else {
                log.warn("未找到适用于客户端地址 {} 的解析策略", clientAddress);
                return null;
            }
        } catch (Exception e) {
            log.error("解析数据失败: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 检查数据中的异常
     * 
     * @param realTimeData 实时数据
     */
    private void checkForAnomalies(RealTimeData realTimeData) {
        // 使用策略模式进行异常检测
        boolean hasAnomaly = false;
        StringBuilder anomalyDescription = new StringBuilder();
        
        // 获取所有异常检测策略并逐一应用
        for (AnomalyDetectionStrategy strategy : AnomalyDetectionStrategyFactory.getAllStrategies()) {
            if (strategy.detectAnomaly(realTimeData)) {
                hasAnomaly = true;
                anomalyDescription.append(strategy.getAnomalyDescription(realTimeData)).append(";");
            }
        }
        
        if (hasAnomaly) {
            realTimeData.setStatus(1); // 设置为异常状态
            realTimeData.setAnomalyDescription(anomalyDescription.toString());
            log.warn("检测到数据异常: {}", anomalyDescription.toString());
            
            // 可以在这里添加告警逻辑
            triggerAlert(realTimeData);
        }
    }

    /**
     * 触发告警
     * 
     * @param realTimeData 实时数据
     */
    private void triggerAlert(RealTimeData realTimeData) {
        // 实现告警逻辑，如发送邮件、短信或站内信
        log.warn("触发告警，设备: {}, 异常: {}", realTimeData.getSource(), realTimeData.getAnomalyDescription());
    }

}