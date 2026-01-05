package com.windtunnel.entity.experiment;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.windtunnel.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 试验项目实体类
 * 
 * 存储试验项目的基本信息
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("experiment_project")
public class ExperimentProject extends BaseEntity {

    /**
     * 项目名称
     */
    @TableField("project_name")
    private String projectName;

    /**
     * 项目编号
     */
    @TableField("project_code")
    private String projectCode;

    /**
     * 项目类型
     */
    @TableField("project_type")
    private String projectType;

    /**
     * 试验类型
     */
    @TableField("experiment_type")
    private String experimentType;

    /**
     * 试验目的
     */
    @TableField("experiment_purpose")
    private String experimentPurpose;

    /**
     * 试验内容
     */
    @TableField("experiment_content")
    private String experimentContent;

    /**
     * 试验负责人ID
     */
    @TableField("project_leader_id")
    private Long projectLeaderId;

    /**
     * 试验负责人姓名
     */
    @TableField("project_leader_name")
    private String projectLeaderName;

    /**
     * 试验设备ID
     */
    @TableField("equipment_id")
    private Long equipmentId;

    /**
     * 实验室ID
     */
    @TableField("laboratory_id")
    private Long laboratoryId;

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
     * 预算金额
     */
    @TableField("budget_amount")
    private BigDecimal budgetAmount;

    /**
     * 实际花费
     */
    @TableField("actual_cost")
    private BigDecimal actualCost;

    /**
     * 试验状态（0-待审批，1-已批准，2-进行中，3-已完成，4-已取消）
     */
    @TableField("status")
    private Integer status;

    /**
     * 申请时间
     */
    @TableField("application_time")
    private LocalDateTime applicationTime;

    /**
     * 审批时间
     */
    @TableField("approval_time")
    private LocalDateTime approvalTime;

    /**
     * 审批人ID
     */
    @TableField("approver_id")
    private Long approverId;

    /**
     * 审批意见
     */
    @TableField("approval_comments")
    private String approvalComments;

    /**
     * 试验报告ID
     */
    @TableField("report_id")
    private String reportId;

    /**
     * 试验报告状态（0-未生成，1-生成中，2-已完成）
     */
    @TableField("report_status")
    private Integer reportStatus;

    /**
     * 试验报告生成时间
     */
    @TableField("report_generation_time")
    private LocalDateTime reportGenerationTime;

    /**
     * 成本管控状态（0-正常，1-预警，2-超标）
     */
    @TableField("cost_control_status")
    private Integer costControlStatus;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 主键ID
     */
    private Long id;

}