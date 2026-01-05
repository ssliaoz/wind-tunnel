package com.windtunnel.entity.security;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.windtunnel.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 安全隐患排查实体类
 * 
 * 存储安全隐患排查记录信息
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("safety_inspection")
public class SafetyInspection extends BaseEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 排查编号
     */
    @TableField("inspection_code")
    private String inspectionCode;

    /**
     * 排查名称
     */
    @TableField("inspection_name")
    private String inspectionName;

    /**
     * 排查类型（1-日常检查，2-专项检查，3-节假日检查，4-事故后检查）
     */
    @TableField("inspection_type")
    private Integer inspectionType;

    /**
     * 排查描述
     */
    @TableField("inspection_description")
    private String inspectionDescription;

    /**
     * 排查状态（0-计划中，1-进行中，2-已完成，3-已取消）
     */
    @TableField("inspection_status")
    private Integer inspectionStatus;

    /**
     * 排查所属实验室ID
     */
    @TableField("laboratory_id")
    private Long laboratoryId;

    /**
     * 排查所属试验项目ID
     */
    @TableField("experiment_project_id")
    private Long experimentProjectId;

    /**
     * 排查所属设备ID
     */
    @TableField("equipment_id")
    private Long equipmentId;

    /**
     * 排查负责人ID
     */
    @TableField("inspector_id")
    private Long inspectorId;

    /**
     * 排查负责人姓名
     */
    @TableField("inspector_name")
    private String inspectorName;

    /**
     * 排查计划开始时间
     */
    @TableField("planned_start_time")
    private LocalDateTime plannedStartTime;

    /**
     * 排查计划结束时间
     */
    @TableField("planned_end_time")
    private LocalDateTime plannedEndTime;

    /**
     * 排查实际开始时间
     */
    @TableField("actual_start_time")
    private LocalDateTime actualStartTime;

    /**
     * 排查实际结束时间
     */
    @TableField("actual_end_time")
    private LocalDateTime actualEndTime;

    /**
     * 排查计划制定人ID
     */
    @TableField("planner_id")
    private Long plannerId;

    /**
     * 排查计划制定人姓名
     */
    @TableField("planner_name")
    private String plannerName;

    /**
     * 排查计划制定时间
     */
    @TableField("planning_time")
    private LocalDateTime planningTime;

    /**
     * 排查标准/检查表
     */
    @TableField("inspection_standard")
    private String inspectionStandard;

    /**
     * 排查发现的隐患总数
     */
    @TableField("hazard_count")
    private Integer hazardCount;

    /**
     * 排查发现的隐患描述
     */
    @TableField("hazard_description")
    private String hazardDescription;

    /**
     * 排查结果（1-合格，2-基本合格，3-不合格）
     */
    @TableField("inspection_result")
    private Integer inspectionResult;

    /**
     * 排查报告ID
     */
    @TableField("report_id")
    private String reportId;

    /**
     * 整改要求
     */
    @TableField("rectification_requirements")
    private String rectificationRequirements;

    /**
     * 整改期限
     */
    @TableField("rectification_deadline")
    private LocalDateTime rectificationDeadline;

    /**
     * 整改状态（0-未整改，1-整改中，2-已整改，3-无需整改）
     */
    @TableField("rectification_status")
    private Integer rectificationStatus;

    /**
     * 整改完成时间
     */
    @TableField("rectification_completion_time")
    private LocalDateTime rectificationCompletionTime;

    /**
     * 整改负责人ID
     */
    @TableField("rectification_person_id")
    private Long rectificationPersonId;

    /**
     * 整改负责人姓名
     */
    @TableField("rectification_person_name")
    private String rectificationPersonName;

    /**
     * 整改验收人ID
     */
    @TableField("acceptance_person_id")
    private Long acceptancePersonId;

    /**
     * 整改验收人姓名
     */
    @TableField("acceptance_person_name")
    private String acceptancePersonName;

    /**
     * 整改验收时间
     */
    @TableField("acceptance_time")
    private LocalDateTime acceptanceTime;

    /**
     * 整改验收结果
     */
    @TableField("acceptance_result")
    private String acceptanceResult;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}