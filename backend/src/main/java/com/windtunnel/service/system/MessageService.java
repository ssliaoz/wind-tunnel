package com.windtunnel.service.system;

import com.windtunnel.entity.system.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 站内信服务接口
 */
public interface MessageService extends IService<Message> {

    /**
     * 发送站内信
     * @param message 消息对象
     * @return 消息ID
     */
    Long sendMessage(Message message);

    /**
     * 根据接收者ID查询消息列表
     * @param receiverId 接收者ID
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @return 消息列表
     */
    List<Message> getMessagesByReceiverId(Long receiverId, int pageNum, int pageSize);

    /**
     * 根据接收者ID和消息状态查询消息列表
     * @param receiverId 接收者ID
     * @param status 消息状态
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @return 消息列表
     */
    List<Message> getMessagesByReceiverIdAndStatus(Long receiverId, Integer status, int pageNum, int pageSize);

    /**
     * 根据发送者ID查询消息列表
     * @param senderId 发送者ID
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @return 消息列表
     */
    List<Message> getMessagesBySenderId(Long senderId, int pageNum, int pageSize);

    /**
     * 根据消息类型查询消息列表
     * @param messageType 消息类型
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @return 消息列表
     */
    List<Message> getMessagesByMessageType(Integer messageType, int pageNum, int pageSize);

    /**
     * 标记消息为已读
     * @param messageId 消息ID
     * @return 是否成功
     */
    boolean markAsRead(Long messageId);

    /**
     * 批量标记消息为已读
     * @param messageIds 消息ID列表
     * @return 成功标记的数量
     */
    int markAsReadBatch(List<Long> messageIds);

    /**
     * 删除消息（逻辑删除）
     * @param messageId 消息ID
     * @return 是否成功
     */
    boolean deleteMessage(Long messageId);

    /**
     * 查询未读消息数量
     * @param receiverId 接收者ID
     * @return 未读消息数量
     */
    int getUnreadCount(Long receiverId);

    /**
     * 根据业务ID和业务类型查询消息
     * @param businessId 业务ID
     * @param businessType 业务类型
     * @return 消息列表
     */
    List<Message> getMessagesByBusinessIdAndType(Long businessId, String businessType);

    /**
     * 获取用户消息统计信息
     * @param receiverId 接收者ID
     * @return 统计信息Map
     */
    java.util.Map<String, Object> getMessageStatistics(Long receiverId);
}