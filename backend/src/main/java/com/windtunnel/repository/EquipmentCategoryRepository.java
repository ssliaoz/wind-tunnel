package com.windtunnel.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.windtunnel.entity.EquipmentCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备分类数据访问接口
 */
@Mapper
public interface EquipmentCategoryRepository extends BaseMapper<EquipmentCategory> {

    /**
     * 根据分类名称查询分类
     * @param name 分类名称
     * @return 分类实体
     */
    EquipmentCategory findByName(@Param("name") String name);

    /**
     * 根据父分类ID查询子分类列表
     * @param parentId 父分类ID
     * @return 分类列表
     */
    List<EquipmentCategory> findByParentId(@Param("parentId") Long parentId);

    /**
     * 根据分类编码查询分类
     * @param code 分类编码
     * @return 分类实体
     */
    EquipmentCategory findByCode(@Param("code") String code);

    /**
     * 查询所有启用的分类
     * @return 分类列表
     */
    List<EquipmentCategory> findAllEnabled();

    /**
     * 根据分类级别查询分类列表
     * @param level 分类级别
     * @return 分类列表
     */
    List<EquipmentCategory> findByLevel(@Param("level") Integer level);
}