package com.windtunnel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 试验项目实体类
 * 
 * 存储风洞试验项目的基本信息，包括试验名称、核心目的、计划周期、负责人等
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wt_experiment_project")
public class ExperimentProject extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 试验项目ID
     */
    private Long id;

    /**
     * 试验项目编号（唯一标识）
     */
    @TableField("project_no")
    private String projectNo;

    /**
     * 试验项目名称
     */
    @TableField("name")
    private String name;

    /**
     * 试验类型（空气动力学/结构强度/气动噪声等）
     */
    @TableField("experiment_type")
    private String experimentType;

    /**
     * 核心目的
     */
    @TableField("purpose")
    private String purpose;

    /**
     * 计划开始时间
     */
    @TableField("plan_start_time")
    private LocalDateTime planStartTime;

    /**
     * 计划结束时间
     */
    @TableField("plan_end_time")
    private LocalDateTime planEndTime;

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
     * 负责人ID
     */
    @TableField("manager_id")
    private Long managerId;

    /**
     * 参与团队（JSON格式存储团队成员信息）
     */
    @TableField("team_members")
    private String teamMembers;

    /**
     * 工况名称
     */
    @TableField("operating_condition")
    private String operatingCondition;

    /**
     * 实验室ID
     */
    @TableField("laboratory_id")
    private Long laboratoryId;

    /**
     * 项目状态（0-草稿，1-待审批，2-已审批，3-进行中，4-已完成，5-已取消）
     */
    @TableField("status")
    private Integer status;

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
     * 备注
     */
    @TableField("remark")
    private String remark;

}