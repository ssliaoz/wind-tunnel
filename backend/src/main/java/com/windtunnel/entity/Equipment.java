package com.windtunnel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 设备实体类
 * 
 * 存储风洞设备的核心信息，包括名称、型号、技术参数、生产厂商等
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wt_equipment")
public class Equipment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 设备名称
     */
    @TableField("name")
    private String name;

    /**
     * 设备型号
     */
    @TableField("model")
    private String model;

    /**
     * KKS编码
     */
    @TableField("kks_code")
    private String kksCode;

    /**
     * 设备分类ID
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 技术参数
     */
    @TableField("technical_params")
    private String technicalParams;

    /**
     * 生产厂商
     */
    @TableField("manufacturer")
    private String manufacturer;

    /**
     * 安装位置
     */
    @TableField("installation_location")
    private String installationLocation;

    /**
     * 采购日期
     */
    @TableField("purchase_date")
    private LocalDateTime purchaseDate;

    /**
     * 质保期
     */
    @TableField("warranty_period")
    private String warrantyPeriod;

    /**
     * 校准周期（单位：月）
     */
    @TableField("calibration_cycle")
    private Integer calibrationCycle;

    /**
     * 上次校准时间
     */
    @TableField("last_calibration_time")
    private LocalDateTime lastCalibrationTime;

    /**
     * 下次校准时间
     */
    @TableField("next_calibration_time")
    private LocalDateTime nextCalibrationTime;

    /**
     * 设备状态（0-停用，1-运行中，2-维护中，3-故障）
     */
    @TableField("status")
    private Integer status;

    /**
     * 购买价格
     */
    @TableField("purchase_price")
    private BigDecimal purchasePrice;

    /**
     * 当前价值
     */
    @TableField("current_value")
    private BigDecimal currentValue;

    /**
     * 实验室ID
     */
    @TableField("laboratory_id")
    private Long laboratoryId;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}