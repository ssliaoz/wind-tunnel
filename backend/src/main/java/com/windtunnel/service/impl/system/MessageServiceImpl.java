package com.windtunnel.service.impl.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.windtunnel.entity.system.Message;
import com.windtunnel.repository.system.MessageRepository;
import com.windtunnel.service.system.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 站内信服务实现类
 */
@Service
@Transactional
public class MessageServiceImpl extends ServiceImpl<MessageRepository, Message> implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Long sendMessage(Message message) {
        // 设置默认状态为未读
        if (message.getStatus() == null) {
            message.setStatus(0); // 未读
        }
        // 设置默认非重要
        if (message.getIsImportant() == null) {
            message.setIsImportant(0); // 普通
        }
        messageRepository.insert(message);
        return message.getId();
    }

    @Override
    public List<Message> getMessagesByReceiverId(Long receiverId, int pageNum, int pageSize) {
        Page<Message> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.eq("receiver_id", receiverId)
               .orderByDesc("create_time");
        IPage<Message> result = messageRepository.selectPage(page, wrapper);
        return result.getRecords();
    }

    @Override
    public List<Message> getMessagesByReceiverIdAndStatus(Long receiverId, Integer status, int pageNum, int pageSize) {
        Page<Message> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.eq("receiver_id", receiverId)
               .eq("status", status)
               .orderByDesc("create_time");
        IPage<Message> result = messageRepository.selectPage(page, wrapper);
        return result.getRecords();
    }

    @Override
    public List<Message> getMessagesBySenderId(Long senderId, int pageNum, int pageSize) {
        Page<Message> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.eq("sender_id", senderId)
               .orderByDesc("create_time");
        IPage<Message> result = messageRepository.selectPage(page, wrapper);
        return result.getRecords();
    }

    @Override
    public List<Message> getMessagesByMessageType(Integer messageType, int pageNum, int pageSize) {
        Page<Message> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.eq("message_type", messageType)
               .orderByDesc("create_time");
        IPage<Message> result = messageRepository.selectPage(page, wrapper);
        return result.getRecords();
    }

    @Override
    public boolean markAsRead(Long messageId) {
        Message message = messageRepository.selectById(messageId);
        if (message != null && message.getStatus() != 2) { // 状态不是已删除
            message.setStatus(1); // 设置为已读
            int result = messageRepository.updateById(message);
            return result > 0;
        }
        return false;
    }

    @Override
    public int markAsReadBatch(List<Long> messageIds) {
        int successCount = 0;
        for (Long messageId : messageIds) {
            if (markAsRead(messageId)) {
                successCount++;
            }
        }
        return successCount;
    }

    @Override
    public boolean deleteMessage(Long messageId) {
        Message message = messageRepository.selectById(messageId);
        if (message != null) {
            // 逻辑删除，设置状态为已删除
            message.setStatus(2); // 已删除
            int result = messageRepository.updateById(message);
            return result > 0;
        }
        return false;
    }

    @Override
    public int getUnreadCount(Long receiverId) {
        return messageRepository.countUnreadByReceiverId(receiverId);
    }

    @Override
    public List<Message> getMessagesByBusinessIdAndType(Long businessId, String businessType) {
        return messageRepository.findByBusinessIdAndBusinessType(businessId, businessType);
    }

    @Override
    public Map<String, Object> getMessageStatistics(Long receiverId) {
        Map<String, Object> statistics = new HashMap<>();
        
        // 总消息数
        QueryWrapper<Message> totalWrapper = new QueryWrapper<>();
        totalWrapper.eq("receiver_id", receiverId);
        int total = messageRepository.selectCount(totalWrapper).intValue();
        statistics.put("total", total);
        
        // 未读消息数
        int unread = getUnreadCount(receiverId);
        statistics.put("unread", unread);
        
        // 已读消息数
        int read = total - unread;
        statistics.put("read", read);
        
        // 重要消息数
        QueryWrapper<Message> importantWrapper = new QueryWrapper<>();
        importantWrapper.eq("receiver_id", receiverId)
                       .eq("is_important", 1);
        int important = messageRepository.selectCount(importantWrapper).intValue();
        statistics.put("important", important);
        
        // 各类型消息数
        for (int i = 1; i <= 4; i++) {
            QueryWrapper<Message> typeWrapper = new QueryWrapper<>();
            typeWrapper.eq("receiver_id", receiverId)
                      .eq("message_type", i);
            int count = messageRepository.selectCount(typeWrapper).intValue();
            statistics.put("type_" + i, count);
        }
        
        return statistics;
    }
}