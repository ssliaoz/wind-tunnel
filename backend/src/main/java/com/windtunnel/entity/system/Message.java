package com.windtunnel.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.windtunnel.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 站内信实体类
 * 用于存储系统内部消息，支持用户间通信和系统通知
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wt_message")
public class Message extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送者ID
     */
    private Long senderId;

    /**
     * 发送者姓名
     */
    private String senderName;

    /**
     * 接收者ID
     */
    private Long receiverId;

    /**
     * 接收者姓名
     */
    private String receiverName;

    /**
     * 消息类型：1-系统通知，2-用户消息，3-任务提醒，4-审批通知
     */
    private Integer messageType;

    /**
     * 消息状态：0-未读，1-已读，2-已删除
     */
    private Integer status;

    /**
     * 是否重要：0-普通，1-重要
     */
    private Integer isImportant;

    /**
     * 附件路径（可选）
     */
    private String attachmentPath;

    /**
     * 关联业务ID（如审批ID、任务ID等）
     */
    private Long businessId;

    /**
     * 业务类型
     */
    private String businessType;
}