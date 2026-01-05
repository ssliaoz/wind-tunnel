package com.windtunnel.entity.security;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.windtunnel.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 风险台账实体类
 * 
 * 存储风洞试验中的风险信息
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("risk_register")
public class RiskRegister extends BaseEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 风险编号
     */
    @TableField("risk_code")
    private String riskCode;

    /**
     * 风险名称
     */
    @TableField("risk_name")
    private String riskName;

    /**
     * 风险类型
     */
    @TableField("risk_type")
    private String riskType;

    /**
     * 风险描述
     */
    @TableField("risk_description")
    private String riskDescription;

    /**
     * 风险等级（1-低风险，2-中风险，3-高风险，4-极高风险）
     */
    @TableField("risk_level")
    private Integer riskLevel;

    /**
     * 风险概率（0.0-1.0）
     */
    @TableField("risk_probability")
    private BigDecimal riskProbability;

    /**
     * 风险影响程度（0.0-1.0）
     */
    @TableField("risk_impact")
    private BigDecimal riskImpact;

    /**
     * 风险值（概率*影响）
     */
    @TableField("risk_value")
    private BigDecimal riskValue;

    /**
     * 风险状态（0-待处理，1-处理中，2-已处理，3-已关闭）
     */
    @TableField("risk_status")
    private Integer riskStatus;

    /**
     * 风险责任人ID
     */
    @TableField("responsible_person_id")
    private Long responsiblePersonId;

    /**
     * 风险责任人姓名
     */
    @TableField("responsible_person_name")
    private String responsiblePersonName;

    /**
     * 风险所属实验室ID
     */
    @TableField("laboratory_id")
    private Long laboratoryId;

    /**
     * 风险所属试验项目ID
     */
    @TableField("experiment_project_id")
    private Long experimentProjectId;

    /**
     * 风险所属设备ID
     */
    @TableField("equipment_id")
    private Long equipmentId;

    /**
     * 风险识别时间
     */
    @TableField("identification_time")
    private LocalDateTime identificationTime;

    /**
     * 风险处理截止时间
     */
    @TableField("deadline")
    private LocalDateTime deadline;

    /**
     * 风险处理方案
     */
    @TableField("treatment_plan")
    private String treatmentPlan;

    /**
     * 风险处理结果
     */
    @TableField("treatment_result")
    private String treatmentResult;

    /**
     * 风险处理时间
     */
    @TableField("treatment_time")
    private LocalDateTime treatmentTime;

    /**
     * 风险处理人ID
     */
    @TableField("treatment_person_id")
    private Long treatmentPersonId;

    /**
     * 风险处理人姓名
     */
    @TableField("treatment_person_name")
    private String treatmentPersonName;

    /**
     * 预防措施
     */
    @TableField("preventive_measures")
    private String preventiveMeasures;

    /**
     * 应急预案
     */
    @TableField("emergency_plan")
    private String emergencyPlan;

    /**
     * 风险监控频率（分钟）
     */
    @TableField("monitoring_frequency")
    private Integer monitoringFrequency;

    /**
     * 风险监控开始时间
     */
    @TableField("monitoring_start_time")
    private LocalDateTime monitoringStartTime;

    /**
     * 风险监控结束时间
     */
    @TableField("monitoring_end_time")
    private LocalDateTime monitoringEndTime;

    /**
     * 风险监控状态（0-未开始，1-监控中，2-已停止）
     */
    @TableField("monitoring_status")
    private Integer monitoringStatus;

    /**
     * 风险评估报告ID
     */
    @TableField("assessment_report_id")
    private String assessmentReportId;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}