package com.windtunnel.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.windtunnel.entity.Laboratory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实验室数据访问层
 * 
 * 提供实验室相关的数据库操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Mapper
public interface LaboratoryRepository extends BaseMapper<Laboratory> {

    /**
     * 根据实验室名称查询实验室
     * 
     * @param name 实验室名称
     * @return 实验室实体
     */
    Laboratory findByName(@Param("name") String name);

    /**
     * 查询所有启用的实验室
     * 
     * @return 实验室列表
     */
    List<Laboratory> findEnabledLaboratories();

    /**
     * 根据负责人ID查询实验室列表
     * 
     * @param managerId 负责人ID
     * @return 实验室列表
     */
    List<Laboratory> findByManagerId(@Param("managerId") Long managerId);

    /**
     * 更新实验室状态
     * 
     * @param laboratoryId 实验室ID
     * @param status 状态
     * @return 更新记录数
     */
    int updateLaboratoryStatus(@Param("laboratoryId") Long laboratoryId, @Param("status") Integer status);

}