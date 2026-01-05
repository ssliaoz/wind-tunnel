package com.windtunnel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备分类实体类
 * 用于存储设备分类信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wt_equipment_category")
public class EquipmentCategory extends BaseEntity {

    /**
     * 分类ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类编码
     */
    private String code;

    /**
     * 父分类ID（用于构建分类树）
     */
    private Long parentId;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 分类级别
     */
    private Integer level;

    /**
     * 是否启用：0-禁用，1-启用
     */
    private Integer enabled;
    
    /**
     * 子分类列表
     */
    private java.util.List<EquipmentCategory> children;

    public java.util.List<EquipmentCategory> getChildren() {
        return children;
    }

    public void setChildren(java.util.List<EquipmentCategory> children) {
        this.children = children;
    }
}