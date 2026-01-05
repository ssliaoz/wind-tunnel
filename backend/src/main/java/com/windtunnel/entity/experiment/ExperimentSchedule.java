package com.windtunnel.entity.experiment;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.windtunnel.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 试验排期实体类
 * 
 * 存储试验排期信息
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("experiment_schedule")
public class ExperimentSchedule extends BaseEntity {

    /**
     * 试验项目ID
     */
    @TableField("experiment_project_id")
    private Long experimentProjectId;

    /**
     * 排期名称
     */
    @TableField("schedule_name")
    private String scheduleName;

    /**
     * 试验开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 试验结束时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
     * 试验状态（0-已预约，1-进行中，2-已完成，3-已取消）
     */
    @TableField("status")
    private Integer status;

    /**
     * 实验室ID
     */
    @TableField("laboratory_id")
    private Long laboratoryId;

    /**
     * 设备ID
     */
    @TableField("equipment_id")
    private Long equipmentId;

    /**
     * 参与人员ID列表（JSON格式）
     */
    @TableField("participant_ids")
    private String participantIds;

    /**
     * 预约人ID
     */
    @TableField("booker_id")
    private Long bookerId;

    /**
     * 预约时间
     */
    @TableField("booking_time")
    private LocalDateTime bookingTime;

    /**
     * 确认时间
     */
    @TableField("confirmation_time")
    private LocalDateTime confirmationTime;

    /**
     * 确认人ID
     */
    @TableField("confirmer_id")
    private Long confirmerId;

    /**
     * 取消时间
     */
    @TableField("cancellation_time")
    private LocalDateTime cancellationTime;

    /**
     * 取消原因
     */
    @TableField("cancellation_reason")
    private String cancellationReason;

    /**
     * 实际开始时间
     */
    @TableField("actual_start_time")
    private LocalDateTime actualStartTime;

    /**
     * 实际结束时间
     */
    @TableField("actual_end_time")
    private LocalDateTime actualEndTime;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

    /**
     * 主键ID
     */
    private Long id;

}