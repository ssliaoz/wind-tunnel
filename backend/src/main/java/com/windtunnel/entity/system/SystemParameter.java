package com.windtunnel.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.windtunnel.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 系统参数实体类
 * 
 * 存储系统基础参数配置信息
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("system_parameter")
public class SystemParameter extends BaseEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 参数键
     */
    @TableField("param_key")
    private String paramKey;

    /**
     * 参数值
     */
    @TableField("param_value")
    private String paramValue;

    /**
     * 参数名称
     */
    @TableField("param_name")
    private String paramName;

    /**
     * 参数类型（1-字符串，2-数字，3-布尔值，4-日期，5-JSON）
     */
    @TableField("param_type")
    private Integer paramType;

    /**
     * 参数描述
     */
    @TableField("param_description")
    private String paramDescription;

    /**
     * 参数分组
     */
    @TableField("param_group")
    private String paramGroup;

    /**
     * 参数排序
     */
    @TableField("param_sort")
    private Integer paramSort;

    /**
     * 是否必填（0-否，1-是）
     */
    @TableField("required")
    private Integer required;

    /**
     * 参数验证规则
     */
    @TableField("validation_rule")
    private String validationRule;

    /**
     * 参数选项（JSON格式）
     */
    @TableField("options")
    private String options;

    /**
     * 是否启用（0-禁用，1-启用）
     */
    @TableField("enabled")
    private Integer enabled;

    /**
     * 默认值
     */
    @TableField("default_value")
    private String defaultValue;

    /**
     * 最小值（数字类型参数）
     */
    @TableField("min_value")
    private String minValue;

    /**
     * 最大值（数字类型参数）
     */
    @TableField("max_value")
    private String maxValue;

    /**
     * 参数长度限制
     */
    @TableField("length_limit")
    private Integer lengthLimit;

    /**
     * 更新用户ID
     */
    @TableField("update_user_id")
    private Long updateUserId;

    /**
     * 更新用户姓名
     */
    @TableField("update_user_name")
    private String updateUserName;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}