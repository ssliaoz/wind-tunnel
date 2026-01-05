package com.windtunnel.controller.system;

import com.windtunnel.entity.system.Message;
import com.windtunnel.service.system.MessageService;
import com.windtunnel.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 站内信控制器
 * 提供站内信相关的REST API接口
 */
@RestController
@RequestMapping("/api/system/message")
@Tag(name = "站内信管理", description = "提供站内信相关的增删改查功能")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 发送站内信
     */
    @PostMapping("/send")
    @Operation(summary = "发送站内信", description = "发送新的站内信")
    public Result<Long> sendMessage(@RequestBody Message message) {
        try {
            Long messageId = messageService.sendMessage(message);
            return Result.success(messageId);
        } catch (Exception e) {
            return Result.error("发送消息失败: " + e.getMessage());
        }
    }

    /**
     * 根据接收者ID查询消息列表
     */
    @GetMapping("/by-receiver/{receiverId}")
    @Operation(summary = "根据接收者查询消息", description = "根据接收者ID查询消息列表")
    public Result<List<Message>> getMessagesByReceiverId(
            @PathVariable Long receiverId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        try {
            List<Message> messages = messageService.getMessagesByReceiverId(receiverId, pageNum, pageSize);
            return Result.success(messages);
        } catch (Exception e) {
            return Result.error("查询消息失败: " + e.getMessage());
        }
    }

    /**
     * 根据接收者ID和消息状态查询消息列表
     */
    @GetMapping("/by-receiver/{receiverId}/status/{status}")
    @Operation(summary = "根据接收者和状态查询消息", description = "根据接收者ID和消息状态查询消息列表")
    public Result<List<Message>> getMessagesByReceiverIdAndStatus(
            @PathVariable Long receiverId,
            @PathVariable Integer status,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        try {
            List<Message> messages = messageService.getMessagesByReceiverIdAndStatus(receiverId, status, pageNum, pageSize);
            return Result.success(messages);
        } catch (Exception e) {
            return Result.error("查询消息失败: " + e.getMessage());
        }
    }

    /**
     * 根据发送者ID查询消息列表
     */
    @GetMapping("/by-sender/{senderId}")
    @Operation(summary = "根据发送者查询消息", description = "根据发送者ID查询消息列表")
    public Result<List<Message>> getMessagesBySenderId(
            @PathVariable Long senderId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        try {
            List<Message> messages = messageService.getMessagesBySenderId(senderId, pageNum, pageSize);
            return Result.success(messages);
        } catch (Exception e) {
            return Result.error("查询消息失败: " + e.getMessage());
        }
    }

    /**
     * 根据消息类型查询消息列表
     */
    @GetMapping("/by-type/{messageType}")
    @Operation(summary = "根据类型查询消息", description = "根据消息类型查询消息列表")
    public Result<List<Message>> getMessagesByMessageType(
            @PathVariable Integer messageType,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        try {
            List<Message> messages = messageService.getMessagesByMessageType(messageType, pageNum, pageSize);
            return Result.success(messages);
        } catch (Exception e) {
            return Result.error("查询消息失败: " + e.getMessage());
        }
    }

    /**
     * 标记消息为已读
     */
    @PutMapping("/mark-read/{messageId}")
    @Operation(summary = "标记消息为已读", description = "将指定消息标记为已读状态")
    public Result<Boolean> markAsRead(@PathVariable Long messageId) {
        try {
            boolean result = messageService.markAsRead(messageId);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("标记消息失败: " + e.getMessage());
        }
    }

    /**
     * 批量标记消息为已读
     */
    @PutMapping("/mark-read-batch")
    @Operation(summary = "批量标记消息为已读", description = "批量将指定消息标记为已读状态")
    public Result<Integer> markAsReadBatch(@RequestBody List<Long> messageIds) {
        try {
            int count = messageService.markAsReadBatch(messageIds);
            return Result.success(count);
        } catch (Exception e) {
            return Result.error("批量标记消息失败: " + e.getMessage());
        }
    }

    /**
     * 删除消息
     */
    @DeleteMapping("/{messageId}")
    @Operation(summary = "删除消息", description = "删除指定的消息（逻辑删除）")
    public Result<Boolean> deleteMessage(@PathVariable Long messageId) {
        try {
            boolean result = messageService.deleteMessage(messageId);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("删除消息失败: " + e.getMessage());
        }
    }

    /**
     * 查询未读消息数量
     */
    @GetMapping("/unread-count/{receiverId}")
    @Operation(summary = "查询未读消息数量", description = "查询指定用户的未读消息数量")
    public Result<Integer> getUnreadCount(@PathVariable Long receiverId) {
        try {
            int count = messageService.getUnreadCount(receiverId);
            return Result.success(count);
        } catch (Exception e) {
            return Result.error("查询未读消息数量失败: " + e.getMessage());
        }
    }

    /**
     * 获取用户消息统计信息
     */
    @GetMapping("/statistics/{receiverId}")
    @Operation(summary = "获取消息统计信息", description = "获取指定用户的消息统计信息")
    public Result<Map<String, Object>> getMessageStatistics(@PathVariable Long receiverId) {
        try {
            Map<String, Object> statistics = messageService.getMessageStatistics(receiverId);
            return Result.success(statistics);
        } catch (Exception e) {
            return Result.error("获取消息统计信息失败: " + e.getMessage());
        }
    }
}