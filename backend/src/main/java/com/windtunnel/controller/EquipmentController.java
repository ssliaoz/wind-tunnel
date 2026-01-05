package com.windtunnel.controller;

import com.windtunnel.common.Result;
import com.windtunnel.entity.Equipment;
import com.windtunnel.service.EquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 设备控制器
 * 
 * 提供设备相关的REST API接口
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/api/equipments")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    /**
     * 创建设备
     * 
     * @param equipment 设备实体
     * @return 创建结果
     */
    @PostMapping
    public Result<Equipment> createEquipment(@RequestBody Equipment equipment) {
        log.info("创建设备请求，设备名称: {}", equipment.getName());
        return equipmentService.createEquipment(equipment);
    }

    /**
     * 根据ID获取设备信息
     * 
     * @param id 设备ID
     * @return 设备信息
     */
    @GetMapping("/{id}")
    public Result<Equipment> getEquipmentById(@PathVariable Long id) {
        log.info("获取设备信息请求，设备ID: {}", id);
        Equipment equipment = equipmentService.getById(id);
        if (equipment != null && equipment.getDeleted() == 0) {
            return Result.success("查询成功", equipment);
        } else {
            return Result.error("设备不存在");
        }
    }

    /**
     * 更新设备信息
     * 
     * @param equipment 设备实体
     * @return 更新结果
     */
    @PutMapping
    public Result<Equipment> updateEquipment(@RequestBody Equipment equipment) {
        log.info("更新设备信息请求，设备ID: {}", equipment.getId());
        return equipmentService.updateEquipment(equipment);
    }

    /**
     * 更新设备状态
     * 
     * @param equipmentId 设备ID
     * @param status 状态
     * @return 更新结果
     */
    @PutMapping("/status")
    public Result<Boolean> updateEquipmentStatus(@RequestParam Long equipmentId, @RequestParam Integer status) {
        log.info("更新设备状态请求，设备ID: {}, 状态: {}", equipmentId, status);
        return equipmentService.updateEquipmentStatus(equipmentId, status);
    }

    /**
     * 根据实验室ID查询设备列表
     * 
     * @param laboratoryId 实验室ID
     * @return 设备列表
     */
    @GetMapping("/laboratory/{laboratoryId}")
    public Result<List<Equipment>> getEquipmentsByLaboratoryId(@PathVariable Long laboratoryId) {
        log.info("根据实验室ID查询设备列表请求，实验室ID: {}", laboratoryId);
        return equipmentService.findByLaboratoryId(laboratoryId);
    }

    /**
     * 根据设备分类ID查询设备列表
     * 
     * @param categoryId 分类ID
     * @return 设备列表
     */
    @GetMapping("/category/{categoryId}")
    public Result<List<Equipment>> getEquipmentsByCategoryId(@PathVariable Long categoryId) {
        log.info("根据设备分类ID查询设备列表请求，分类ID: {}", categoryId);
        return equipmentService.findByCategoryId(categoryId);
    }

    /**
     * 根据设备状态查询设备列表
     * 
     * @param status 设备状态
     * @return 设备列表
     */
    @GetMapping("/status/{status}")
    public Result<List<Equipment>> getEquipmentsByStatus(@PathVariable Integer status) {
        log.info("根据设备状态查询设备列表请求，状态: {}", status);
        return equipmentService.findByStatus(status);
    }

    /**
     * 根据采购日期范围查询设备列表
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 设备列表
     */
    @GetMapping("/purchase-date-range")
    public Result<List<Equipment>> getEquipmentsByPurchaseDateRange(@RequestParam LocalDateTime startDate, 
                                                                  @RequestParam LocalDateTime endDate) {
        log.info("根据采购日期范围查询设备列表请求，开始日期: {}, 结束日期: {}", startDate, endDate);
        return equipmentService.findByPurchaseDateRange(startDate, endDate);
    }

    /**
     * 分页查询设备列表
     * 
     * @param page 页码
     * @param size 页面大小
     * @return 设备列表
     */
    @GetMapping
    public Result<List<Equipment>> getEquipments(@RequestParam(defaultValue = "1") int page, 
                                               @RequestParam(defaultValue = "10") int size) {
        log.info("分页查询设备列表请求，页码: {}, 页面大小: {}", page, size);
        return equipmentService.findEquipments(page, size);
    }

    /**
     * 删除设备
     * 
     * @param equipmentId 设备ID
     * @return 删除结果
     */
    @DeleteMapping("/{equipmentId}")
    public Result<Boolean> deleteEquipment(@PathVariable Long equipmentId) {
        log.info("删除设备请求，设备ID: {}", equipmentId);
        return equipmentService.deleteEquipment(equipmentId);
    }

    /**
     * 更新设备校准时间
     * 
     * @param equipmentId 设备ID
     * @param lastCalibrationTime 上次校准时间
     * @param nextCalibrationTime 下次校准时间
     * @return 更新结果
     */
    @PutMapping("/calibration-time")
    public Result<Boolean> updateCalibrationTime(@RequestParam Long equipmentId, 
                                               @RequestParam(required = false) LocalDateTime lastCalibrationTime,
                                               @RequestParam(required = false) LocalDateTime nextCalibrationTime) {
        log.info("更新设备校准时间请求，设备ID: {}, 上次校准时间: {}, 下次校准时间: {}", 
                equipmentId, lastCalibrationTime, nextCalibrationTime);
        return equipmentService.updateCalibrationTime(equipmentId, lastCalibrationTime, nextCalibrationTime);
    }

}