package com.windtunnel.controller;

import com.windtunnel.common.Result;
import com.windtunnel.entity.Laboratory;
import com.windtunnel.service.LaboratoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 实验室控制器
 * 
 * 提供实验室相关的REST API接口
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/api/laboratories")
public class LaboratoryController {

    @Autowired
    private LaboratoryService laboratoryService;

    /**
     * 创建实验室
     * 
     * @param laboratory 实验室实体
     * @return 创建结果
     */
    @PostMapping
    public Result<Laboratory> createLaboratory(@RequestBody Laboratory laboratory) {
        log.info("创建实验室请求，实验室名称: {}", laboratory.getName());
        return laboratoryService.createLaboratory(laboratory);
    }

    /**
     * 根据ID获取实验室信息
     * 
     * @param id 实验室ID
     * @return 实验室信息
     */
    @GetMapping("/{id}")
    public Result<Laboratory> getLaboratoryById(@PathVariable Long id) {
        log.info("获取实验室信息请求，实验室ID: {}", id);
        Laboratory laboratory = laboratoryService.getById(id);
        if (laboratory != null && laboratory.getDeleted() == 0) {
            return Result.success("查询成功", laboratory);
        } else {
            return Result.error("实验室不存在");
        }
    }

    /**
     * 更新实验室信息
     * 
     * @param laboratory 实验室实体
     * @return 更新结果
     */
    @PutMapping
    public Result<Laboratory> updateLaboratory(@RequestBody Laboratory laboratory) {
        log.info("更新实验室信息请求，实验室ID: {}", laboratory.getId());
        return laboratoryService.updateLaboratory(laboratory);
    }

    /**
     * 更新实验室状态
     * 
     * @param laboratoryId 实验室ID
     * @param status 状态
     * @return 更新结果
     */
    @PutMapping("/status")
    public Result<Boolean> updateLaboratoryStatus(@RequestParam Long laboratoryId, @RequestParam Integer status) {
        log.info("更新实验室状态请求，实验室ID: {}, 状态: {}", laboratoryId, status);
        return laboratoryService.updateLaboratoryStatus(laboratoryId, status);
    }

    /**
     * 根据负责人ID查询实验室列表
     * 
     * @param managerId 负责人ID
     * @return 实验室列表
     */
    @GetMapping("/manager/{managerId}")
    public Result<List<Laboratory>> getLaboratoriesByManagerId(@PathVariable Long managerId) {
        log.info("根据负责人ID查询实验室列表请求，负责人ID: {}", managerId);
        return laboratoryService.findByManagerId(managerId);
    }

    /**
     * 查询所有启用的实验室
     * 
     * @return 实验室列表
     */
    @GetMapping("/enabled")
    public Result<List<Laboratory>> getEnabledLaboratories() {
        log.info("查询所有启用的实验室请求");
        return laboratoryService.findEnabledLaboratories();
    }

    /**
     * 分页查询实验室列表
     * 
     * @param page 页码
     * @param size 页面大小
     * @return 实验室列表
     */
    @GetMapping
    public Result<List<Laboratory>> getLaboratories(@RequestParam(defaultValue = "1") int page, 
                                                   @RequestParam(defaultValue = "10") int size) {
        log.info("分页查询实验室列表请求，页码: {}, 页面大小: {}", page, size);
        return laboratoryService.findLaboratories(page, size);
    }

    /**
     * 删除实验室
     * 
     * @param laboratoryId 实验室ID
     * @return 删除结果
     */
    @DeleteMapping("/{laboratoryId}")
    public Result<Boolean> deleteLaboratory(@PathVariable Long laboratoryId) {
        log.info("删除实验室请求，实验室ID: {}", laboratoryId);
        return laboratoryService.deleteLaboratory(laboratoryId);
    }

}