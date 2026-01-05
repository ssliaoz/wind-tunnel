package com.windtunnel.repository.notification;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.windtunnel.entity.notification.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通知数据访问接口
 */
@Mapper
public interface NotificationRepository extends BaseMapper<Notification> {

    /**
     * 根据发送状态查询通知列表
     * @param sendStatus 发送状态
     * @return 通知列表
     */
    List<Notification> findBySendStatus(@Param("sendStatus") Integer sendStatus);

    /**
     * 根据通知类型查询通知列表
     * @param notificationType 通知类型
     * @return 通知列表
     */
    List<Notification> findByNotificationType(@Param("notificationType") Integer notificationType);

    /**
     * 根据接收者ID查询通知列表
     * @param receiverIds 接收者ID（JSON格式）
     * @return 通知列表
     */
    List<Notification> findByReceiverIds(@Param("receiverIds") String receiverIds);

    /**
     * 根据业务ID和业务类型查询通知
     * @param businessId 业务ID
     * @param businessType 业务类型
     * @return 通知列表
     */
    List<Notification> findByBusinessIdAndBusinessType(@Param("businessId") Long businessId, @Param("businessType") String businessType);

    /**
     * 查询待发送的通知（用于定时任务处理）
     * @return 待发送通知列表
     */
    List<Notification> findPendingNotifications();

    /**
     * 根据计划发送时间查询定时通知
     * @param scheduledTime 计划发送时间
     * @return 通知列表
     */
    List<Notification> findByScheduledTime(@Param("scheduledTime") java.time.LocalDateTime scheduledTime);
}