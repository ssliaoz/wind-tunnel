package com.windtunnel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实验室实体类
 * 
 * 存储实验室的基本信息，用于试验管理和数据权限控制
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wt_laboratory")
public class Laboratory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 实验室ID
     */
    private Long id;

    /**
     * 实验室名称
     */
    @TableField("name")
    private String name;

    /**
     * 实验室编码
     */
    @TableField("code")
    private String code;

    /**
     * 实验室描述
     */
    @TableField("description")
    private String description;

    /**
     * 负责人ID
     */
    @TableField("manager_id")
    private Long managerId;

    /**
     * 实验室状态（0-禁用，1-启用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}