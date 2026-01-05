package com.windtunnel.repository.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.windtunnel.entity.system.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 站内信数据访问接口
 */
@Mapper
public interface MessageRepository extends BaseMapper<Message> {

    /**
     * 根据接收者ID查询消息列表
     * @param receiverId 接收者ID
     * @return 消息列表
     */
    List<Message> findByReceiverId(@Param("receiverId") Long receiverId);

    /**
     * 根据接收者ID和消息状态查询消息列表
     * @param receiverId 接收者ID
     * @param status 消息状态
     * @return 消息列表
     */
    List<Message> findByReceiverIdAndStatus(@Param("receiverId") Long receiverId, @Param("status") Integer status);

    /**
     * 根据发送者ID查询消息列表
     * @param senderId 发送者ID
     * @return 消息列表
     */
    List<Message> findBySenderId(@Param("senderId") Long senderId);

    /**
     * 根据消息类型查询消息列表
     * @param messageType 消息类型
     * @return 消息列表
     */
    List<Message> findByMessageType(@Param("messageType") Integer messageType);

    /**
     * 查询未读消息数量
     * @param receiverId 接收者ID
     * @return 未读消息数量
     */
    int countUnreadByReceiverId(@Param("receiverId") Long receiverId);

    /**
     * 根据业务ID和业务类型查询消息
     * @param businessId 业务ID
     * @param businessType 业务类型
     * @return 消息列表
     */
    List<Message> findByBusinessIdAndBusinessType(@Param("businessId") Long businessId, @Param("businessType") String businessType);
}