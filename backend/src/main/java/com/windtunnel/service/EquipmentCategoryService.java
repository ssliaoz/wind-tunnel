package com.windtunnel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.windtunnel.entity.EquipmentCategory;
import com.windtunnel.common.Result;

import java.util.List;

/**
 * 设备分类服务接口
 */
public interface EquipmentCategoryService extends IService<EquipmentCategory> {

    /**
     * 根据分类名称查询分类
     * @param name 分类名称
     * @return 分类实体
     */
    EquipmentCategory findByName(String name);

    /**
     * 根据分类编码查询分类
     * @param code 分类编码
     * @return 分类实体
     */
    EquipmentCategory findByCode(String code);

    /**
     * 创建设备分类
     * @param category 分类实体
     * @return 创建结果
     */
    Result<EquipmentCategory> createCategory(EquipmentCategory category);

    /**
     * 更新设备分类
     * @param category 分类实体
     * @return 更新结果
     */
    Result<EquipmentCategory> updateCategory(EquipmentCategory category);

    /**
     * 根据父分类ID查询子分类列表
     * @param parentId 父分类ID
     * @return 分类列表
     */
    Result<List<EquipmentCategory>> findByParentId(Long parentId);

    /**
     * 查询所有启用的分类
     * @return 分类列表
     */
    Result<List<EquipmentCategory>> findAllEnabled();

    /**
     * 根据分类级别查询分类列表
     * @param level 分类级别
     * @return 分类列表
     */
    Result<List<EquipmentCategory>> findByLevel(Integer level);

    /**
     * 构建分类树
     * @return 分类树
     */
    Result<List<EquipmentCategory>> buildCategoryTree();

    /**
     * 删除设备分类
     * @param categoryId 分类ID
     * @return 删除结果
     */
    Result<Boolean> deleteCategory(Long categoryId);
}