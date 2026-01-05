package com.windtunnel.service.impl.notification;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.windtunnel.entity.notification.Notification;
import com.windtunnel.repository.notification.NotificationRepository;
import com.windtunnel.service.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通知服务实现类
 */
@Service
@Transactional
public class NotificationServiceImpl extends ServiceImpl<NotificationRepository, Notification> implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Long sendNotification(Notification notification) {
        // 设置默认值
        if (notification.getSendStatus() == null) {
            notification.setSendStatus(0); // 待发送
        }
        if (notification.getPriority() == null) {
            notification.setPriority(1); // 中等优先级
        }
        if (notification.getRetryCount() == null) {
            notification.setRetryCount(0);
        }
        if (notification.getMaxRetryCount() == null) {
            notification.setMaxRetryCount(3); // 默认最大重试3次
        }
        
        // 如果没有计划发送时间，则立即发送
        if (notification.getScheduledTime() == null) {
            notification.setScheduledTime(LocalDateTime.now());
        }
        
        notificationRepository.insert(notification);
        return notification.getId();
    }

    @Override
    public int sendNotificationBatch(List<Notification> notifications) {
        int successCount = 0;
        for (Notification notification : notifications) {
            try {
                sendNotification(notification);
                successCount++;
            } catch (Exception e) {
                // 记录错误但继续处理其他通知
                System.err.println("发送通知失败: " + e.getMessage());
            }
        }
        return successCount;
    }

    @Override
    public List<Notification> getNotificationsBySendStatus(Integer sendStatus, int pageNum, int pageSize) {
        Page<Notification> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.eq("send_status", sendStatus)
               .orderByDesc("create_time");
        IPage<Notification> result = notificationRepository.selectPage(page, wrapper);
        return result.getRecords();
    }

    @Override
    public List<Notification> getNotificationsByNotificationType(Integer notificationType, int pageNum, int pageSize) {
        Page<Notification> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.eq("notification_type", notificationType)
               .orderByDesc("create_time");
        IPage<Notification> result = notificationRepository.selectPage(page, wrapper);
        return result.getRecords();
    }

    @Override
    public List<Notification> getNotificationsByReceiverIds(String receiverIds, int pageNum, int pageSize) {
        Page<Notification> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.like("receiver_ids", receiverIds)
               .orderByDesc("create_time");
        IPage<Notification> result = notificationRepository.selectPage(page, wrapper);
        return result.getRecords();
    }

    @Override
    public List<Notification> getNotificationsByBusinessIdAndType(Long businessId, String businessType) {
        return notificationRepository.findByBusinessIdAndBusinessType(businessId, businessType);
    }

    @Override
    public boolean resendNotification(Long notificationId) {
        Notification notification = notificationRepository.selectById(notificationId);
        if (notification != null && (notification.getSendStatus() == 3 || // 发送失败
                                   notification.getSendStatus() == 0)) { // 待发送
            // 重置发送状态和重试次数
            notification.setSendStatus(0); // 待发送
            notification.setRetryCount(notification.getRetryCount() + 1);
            notification.setScheduledTime(LocalDateTime.now()); // 重新设置为立即发送
            int result = notificationRepository.updateById(notification);
            return result > 0;
        }
        return false;
    }

    @Override
    public int resendNotificationBatch(List<Long> notificationIds) {
        int successCount = 0;
        for (Long notificationId : notificationIds) {
            if (resendNotification(notificationId)) {
                successCount++;
            }
        }
        return successCount;
    }

    @Override
    public int processPendingNotifications() {
        // 查询待发送且未超过最大重试次数的通知
        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.eq("send_status", 0) // 待发送
               .lt("retry_count", 3) // 重试次数未超过最大值
               .le("scheduled_time", LocalDateTime.now()); // 计划发送时间已到
        List<Notification> pendingNotifications = notificationRepository.selectList(wrapper);
        
        int processedCount = 0;
        for (Notification notification : pendingNotifications) {
            // 在实际实现中，这里会调用具体的通知发送逻辑
            // 如邮件发送、短信发送、站内信发送等
            // 暂时只更新状态为"发送中"
            notification.setSendStatus(1); // 发送中
            notification.setSendTime(LocalDateTime.now());
            int result = notificationRepository.updateById(notification);
            if (result > 0) {
                processedCount++;
            }
        }
        
        return processedCount;
    }

    @Override
    public Map<String, Object> getNotificationStatistics(Long userId) {
        Map<String, Object> statistics = new HashMap<>();
        
        // 总通知数
        QueryWrapper<Notification> totalWrapper = new QueryWrapper<>();
        totalWrapper.like("receiver_ids", userId.toString());
        int total = notificationRepository.selectCount(totalWrapper).intValue();
        statistics.put("total", total);
        
        // 已发送通知数
        QueryWrapper<Notification> sentWrapper = new QueryWrapper<>();
        sentWrapper.like("receiver_ids", userId.toString())
                  .eq("send_status", 2);
        int sent = notificationRepository.selectCount(sentWrapper).intValue();
        statistics.put("sent", sent);
        
        // 待发送通知数
        QueryWrapper<Notification> pendingWrapper = new QueryWrapper<>();
        pendingWrapper.like("receiver_ids", userId.toString())
                     .eq("send_status", 0);
        int pending = notificationRepository.selectCount(pendingWrapper).intValue();
        statistics.put("pending", pending);
        
        // 发送失败通知数
        QueryWrapper<Notification> failedWrapper = new QueryWrapper<>();
        failedWrapper.like("receiver_ids", userId.toString())
                    .eq("send_status", 3);
        int failed = notificationRepository.selectCount(failedWrapper).intValue();
        statistics.put("failed", failed);
        
        // 各类型通知数
        for (int i = 1; i <= 5; i++) {
            QueryWrapper<Notification> typeWrapper = new QueryWrapper<>();
            typeWrapper.like("receiver_ids", userId.toString())
                      .eq("notification_type", i);
            int count = notificationRepository.selectCount(typeWrapper).intValue();
            statistics.put("type_" + i, count);
        }
        
        return statistics;
    }
}