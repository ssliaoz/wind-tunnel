package com.windtunnel.observer.impl;

import com.windtunnel.entity.RealTimeData;
import com.windtunnel.entity.notification.Notification;
import com.windtunnel.observer.Observer;
import com.windtunnel.observer.Subject;
import com.windtunnel.service.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通知观察者
 * 当实时数据更新时，发送通知
 */
@Slf4j
@Component
public class NotificationObserver implements Observer {
    
    @Autowired
    private NotificationService notificationService;
    
    @Override
    public void update(Subject subject, Object data) {
        if (data instanceof RealTimeData) {
            RealTimeData realTimeData = (RealTimeData) data;
            
            // 检查是否需要发送通知（例如，数据异常时）
            if (isNotificationRequired(realTimeData)) {
                sendNotification(realTimeData);
            }
        }
    }
    
    /**
     * 判断是否需要发送通知
     * @param realTimeData 实时数据
     * @return 是否需要发送通知
     */
    private boolean isNotificationRequired(RealTimeData realTimeData) {
        // 如果数据状态为异常，则需要发送通知
        return realTimeData.getStatus() != null && realTimeData.getStatus() == 1;
    }
    
    /**
     * 发送通知
     * @param realTimeData 实时数据
     */
    private void sendNotification(RealTimeData realTimeData) {
        log.info("发送通知: 检测到数据异常，设备: {}", realTimeData.getSource());
        
        // 创建通知对象
        Notification notification = new Notification();
        notification.setTitle("系统告警");
        notification.setContent("检测到设备 " + realTimeData.getSource() + " 数据异常: " + realTimeData.getAnomalyDescription());
        notification.setSenderId(0L); // 系统发送者ID
        notification.setSenderName("SYSTEM"); // 系统发送者名称
        notification.setNotificationType(1); // 系统通知
        notification.setSendStatus(0); // 待发送
        
        // 发送通知
        if (notificationService != null) {
            try {
                Long notificationId = notificationService.sendNotification(notification);
                log.info("通知发送成功，通知ID: {}", notificationId);
            } catch (Exception e) {
                log.error("发送通知失败: {}", e.getMessage(), e);
            }
        } else {
            log.warn("通知服务未初始化");
        }
    }
}