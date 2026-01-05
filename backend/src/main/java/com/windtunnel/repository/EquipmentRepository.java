package com.windtunnel.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.windtunnel.entity.Equipment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 设备数据访问层
 * 
 * 提供设备相关的数据库操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Mapper
public interface EquipmentRepository extends BaseMapper<Equipment> {

    /**
     * 根据设备名称查询设备
     * 
     * @param name 设备名称
     * @return 设备实体
     */
    Equipment findByName(@Param("name") String name);

    /**
     * 根据设备型号查询设备
     * 
     * @param model 设备型号
     * @return 设备实体
     */
    Equipment findByModel(@Param("model") String model);

    /**
     * 根据KKS编码查询设备
     * 
     * @param kksCode KKS编码
     * @return 设备实体
     */
    Equipment findByKksCode(@Param("kksCode") String kksCode);

    /**
     * 根据实验室ID查询设备列表
     * 
     * @param laboratoryId 实验室ID
     * @return 设备列表
     */
    List<Equipment> findByLaboratoryId(@Param("laboratoryId") Long laboratoryId);

    /**
     * 根据设备分类ID查询设备列表
     * 
     * @param categoryId 分类ID
     * @return 设备列表
     */
    List<Equipment> findByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * 根据设备状态查询设备列表
     * 
     * @param status 设备状态
     * @return 设备列表
     */
    List<Equipment> findByStatus(@Param("status") Integer status);

    /**
     * 根据采购日期范围查询设备列表
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 设备列表
     */
    List<Equipment> findByPurchaseDateRange(@Param("startDate") LocalDateTime startDate, 
                                         @Param("endDate") LocalDateTime endDate);

    /**
     * 更新设备状态
     * 
     * @param equipmentId 设备ID
     * @param status 状态
     * @return 更新记录数
     */
    int updateEquipmentStatus(@Param("equipmentId") Long equipmentId, @Param("status") Integer status);

    /**
     * 更新设备校准时间
     * 
     * @param equipmentId 设备ID
     * @param lastCalibrationTime 上次校准时间
     * @param nextCalibrationTime 下次校准时间
     * @return 更新记录数
     */
    int updateCalibrationTime(@Param("equipmentId") Long equipmentId, 
                             @Param("lastCalibrationTime") LocalDateTime lastCalibrationTime,
                             @Param("nextCalibrationTime") LocalDateTime nextCalibrationTime);

}