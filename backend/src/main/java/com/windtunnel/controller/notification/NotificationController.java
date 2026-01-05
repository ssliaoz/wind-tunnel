package com.windtunnel.controller.notification;

import com.windtunnel.entity.notification.Notification;
import com.windtunnel.service.notification.NotificationService;
import com.windtunnel.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 通知控制器
 * 提供通知相关的REST API接口
 */
@RestController
@RequestMapping("/api/notification")
@Tag(name = "通知管理", description = "提供通知相关的增删改查及发送功能")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * 发送通知
     */
    @PostMapping("/send")
    @Operation(summary = "发送通知", description = "发送新的通知")
    public Result<Long> sendNotification(@RequestBody Notification notification) {
        try {
            Long notificationId = notificationService.sendNotification(notification);
            return Result.success(notificationId);
        } catch (Exception e) {
            return Result.error("发送通知失败: " + e.getMessage());
        }
    }

    /**
     * 批量发送通知
     */
    @PostMapping("/send-batch")
    @Operation(summary = "批量发送通知", description = "批量发送通知")
    public Result<Integer> sendNotificationBatch(@RequestBody List<Notification> notifications) {
        try {
            int count = notificationService.sendNotificationBatch(notifications);
            return Result.success(count);
        } catch (Exception e) {
            return Result.error("批量发送通知失败: " + e.getMessage());
        }
    }

    /**
     * 根据发送状态查询通知列表
     */
    @GetMapping("/by-status/{sendStatus}")
    @Operation(summary = "根据发送状态查询通知", description = "根据发送状态查询通知列表")
    public Result<List<Notification>> getNotificationsBySendStatus(
            @PathVariable Integer sendStatus,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        try {
            List<Notification> notifications = notificationService.getNotificationsBySendStatus(sendStatus, pageNum, pageSize);
            return Result.success(notifications);
        } catch (Exception e) {
            return Result.error("查询通知失败: " + e.getMessage());
        }
    }

    /**
     * 根据通知类型查询通知列表
     */
    @GetMapping("/by-type/{notificationType}")
    @Operation(summary = "根据类型查询通知", description = "根据通知类型查询通知列表")
    public Result<List<Notification>> getNotificationsByNotificationType(
            @PathVariable Integer notificationType,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        try {
            List<Notification> notifications = notificationService.getNotificationsByNotificationType(notificationType, pageNum, pageSize);
            return Result.success(notifications);
        } catch (Exception e) {
            return Result.error("查询通知失败: " + e.getMessage());
        }
    }

    /**
     * 根据接收者ID查询通知列表
     */
    @GetMapping("/by-receiver/{receiverIds}")
    @Operation(summary = "根据接收者查询通知", description = "根据接收者ID查询通知列表")
    public Result<List<Notification>> getNotificationsByReceiverIds(
            @PathVariable String receiverIds,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        try {
            List<Notification> notifications = notificationService.getNotificationsByReceiverIds(receiverIds, pageNum, pageSize);
            return Result.success(notifications);
        } catch (Exception e) {
            return Result.error("查询通知失败: " + e.getMessage());
        }
    }

    /**
     * 根据业务ID和业务类型查询通知
     */
    @GetMapping("/by-business/{businessId}/{businessType}")
    @Operation(summary = "根据业务查询通知", description = "根据业务ID和业务类型查询通知")
    public Result<List<Notification>> getNotificationsByBusinessIdAndType(
            @PathVariable Long businessId,
            @PathVariable String businessType) {
        try {
            List<Notification> notifications = notificationService.getNotificationsByBusinessIdAndType(businessId, businessType);
            return Result.success(notifications);
        } catch (Exception e) {
            return Result.error("查询通知失败: " + e.getMessage());
        }
    }

    /**
     * 重新发送失败的通知
     */
    @PutMapping("/resend/{notificationId}")
    @Operation(summary = "重新发送通知", description = "重新发送失败的通知")
    public Result<Boolean> resendNotification(@PathVariable Long notificationId) {
        try {
            boolean result = notificationService.resendNotification(notificationId);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("重新发送通知失败: " + e.getMessage());
        }
    }

    /**
     * 批量重新发送失败的通知
     */
    @PutMapping("/resend-batch")
    @Operation(summary = "批量重新发送通知", description = "批量重新发送失败的通知")
    public Result<Integer> resendNotificationBatch(@RequestBody List<Long> notificationIds) {
        try {
            int count = notificationService.resendNotificationBatch(notificationIds);
            return Result.success(count);
        } catch (Exception e) {
            return Result.error("批量重新发送通知失败: " + e.getMessage());
        }
    }

    /**
     * 处理待发送的通知（用于定时任务）
     */
    @PutMapping("/process-pending")
    @Operation(summary = "处理待发送通知", description = "处理待发送的通知")
    public Result<Integer> processPendingNotifications() {
        try {
            int count = notificationService.processPendingNotifications();
            return Result.success(count);
        } catch (Exception e) {
            return Result.error("处理待发送通知失败: " + e.getMessage());
        }
    }

    /**
     * 获取通知统计信息
     */
    @GetMapping("/statistics/{userId}")
    @Operation(summary = "获取通知统计信息", description = "获取指定用户的通知统计信息")
    public Result<Map<String, Object>> getNotificationStatistics(@PathVariable Long userId) {
        try {
            Map<String, Object> statistics = notificationService.getNotificationStatistics(userId);
            return Result.success(statistics);
        } catch (Exception e) {
            return Result.error("获取通知统计信息失败: " + e.getMessage());
        }
    }
}