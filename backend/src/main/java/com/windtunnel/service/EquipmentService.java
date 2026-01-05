package com.windtunnel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.windtunnel.entity.Equipment;
import com.windtunnel.common.Result;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 设备服务接口
 * 
 * 提供设备相关的业务逻辑处理方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
public interface EquipmentService extends IService<Equipment> {

    /**
     * 根据设备名称查询设备
     * 
     * @param name 设备名称
     * @return 设备实体
     */
    Equipment findByName(String name);

    /**
     * 根据KKS编码查询设备
     * 
     * @param kksCode KKS编码
     * @return 设备实体
     */
    Equipment findByKksCode(String kksCode);

    /**
     * 创建设备
     * 
     * @param equipment 设备实体
     * @return 创建结果
     */
    Result<Equipment> createEquipment(Equipment equipment);

    /**
     * 更新设备信息
     * 
     * @param equipment 设备实体
     * @return 更新结果
     */
    Result<Equipment> updateEquipment(Equipment equipment);

    /**
     * 更新设备状态
     * 
     * @param equipmentId 设备ID
     * @param status 状态
     * @return 更新结果
     */
    Result<Boolean> updateEquipmentStatus(Long equipmentId, Integer status);

    /**
     * 根据实验室ID查询设备列表
     * 
     * @param laboratoryId 实验室ID
     * @return 设备列表
     */
    Result<List<Equipment>> findByLaboratoryId(Long laboratoryId);

    /**
     * 根据设备分类ID查询设备列表
     * 
     * @param categoryId 分类ID
     * @return 设备列表
     */
    Result<List<Equipment>> findByCategoryId(Long categoryId);

    /**
     * 根据设备状态查询设备列表
     * 
     * @param status 设备状态
     * @return 设备列表
     */
    Result<List<Equipment>> findByStatus(Integer status);

    /**
     * 根据采购日期范围查询设备列表
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 设备列表
     */
    Result<List<Equipment>> findByPurchaseDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 分页查询设备列表
     * 
     * @param page 页码
     * @param size 页面大小
     * @return 设备列表
     */
    Result<List<Equipment>> findEquipments(int page, int size);

    /**
     * 删除设备
     * 
     * @param equipmentId 设备ID
     * @return 删除结果
     */
    Result<Boolean> deleteEquipment(Long equipmentId);

    /**
     * 更新设备校准时间
     * 
     * @param equipmentId 设备ID
     * @param lastCalibrationTime 上次校准时间
     * @param nextCalibrationTime 下次校准时间
     * @return 更新结果
     */
    Result<Boolean> updateCalibrationTime(Long equipmentId, LocalDateTime lastCalibrationTime, LocalDateTime nextCalibrationTime);

}