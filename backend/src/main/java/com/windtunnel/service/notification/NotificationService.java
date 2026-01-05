package com.windtunnel.service.notification;

import com.windtunnel.entity.notification.Notification;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 通知服务接口
 */
public interface NotificationService extends IService<Notification> {

    /**
     * 发送通知
     * @param notification 通知对象
     * @return 通知ID
     */
    Long sendNotification(Notification notification);

    /**
     * 批量发送通知
     * @param notifications 通知列表
     * @return 发送成功的数量
     */
    int sendNotificationBatch(List<Notification> notifications);

    /**
     * 根据发送状态查询通知列表
     * @param sendStatus 发送状态
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @return 通知列表
     */
    List<Notification> getNotificationsBySendStatus(Integer sendStatus, int pageNum, int pageSize);

    /**
     * 根据通知类型查询通知列表
     * @param notificationType 通知类型
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @return 通知列表
     */
    List<Notification> getNotificationsByNotificationType(Integer notificationType, int pageNum, int pageSize);

    /**
     * 根据接收者ID查询通知列表
     * @param receiverIds 接收者ID
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @return 通知列表
     */
    List<Notification> getNotificationsByReceiverIds(String receiverIds, int pageNum, int pageSize);

    /**
     * 根据业务ID和业务类型查询通知
     * @param businessId 业务ID
     * @param businessType 业务类型
     * @return 通知列表
     */
    List<Notification> getNotificationsByBusinessIdAndType(Long businessId, String businessType);

    /**
     * 重新发送失败的通知
     * @param notificationId 通知ID
     * @return 是否成功
     */
    boolean resendNotification(Long notificationId);

    /**
     * 批量重新发送失败的通知
     * @param notificationIds 通知ID列表
     * @return 重新发送成功的数量
     */
    int resendNotificationBatch(List<Long> notificationIds);

    /**
     * 处理待发送的通知（用于定时任务）
     * @return 处理成功的数量
     */
    int processPendingNotifications();

    /**
     * 获取通知统计信息
     * @param userId 用户ID
     * @return 统计信息Map
     */
    java.util.Map<String, Object> getNotificationStatistics(Long userId);
}