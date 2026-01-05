package com.windtunnel.entity.notification;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.windtunnel.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知实体类
 * 用于存储系统通知信息，支持多种通知方式
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wt_notification")
public class Notification extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知内容
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
     * 接收者ID（JSON格式，支持多个接收者）
     */
    private String receiverIds;

    /**
     * 接收者姓名（JSON格式，支持多个接收者）
     */
    private String receiverNames;

    /**
     * 通知类型：1-系统通知，2-邮件通知，3-短信通知，4-站内信，5-微信通知
     */
    private Integer notificationType;

    /**
     * 发送状态：0-待发送，1-发送中，2-已发送，3-发送失败
     */
    private Integer sendStatus;

    /**
     * 优先级：0-低，1-中，2-高，3-紧急
     */
    private Integer priority;

    /**
     * 通知渠道：JSON格式，如["email", "sms", "message"]，表示同时通过多种渠道发送
     */
    private String channels;

    /**
     * 发送时间
     */
    private java.time.LocalDateTime sendTime;

    /**
     * 计划发送时间（用于定时发送）
     */
    private java.time.LocalDateTime scheduledTime;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 最大重试次数
     */
    private Integer maxRetryCount;

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

    /**
     * 扩展属性（JSON格式）
     */
    private String extraProperties;
}