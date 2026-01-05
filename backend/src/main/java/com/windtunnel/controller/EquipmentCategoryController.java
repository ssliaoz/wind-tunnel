package com.windtunnel.controller;

import com.windtunnel.common.Result;
import com.windtunnel.entity.EquipmentCategory;
import com.windtunnel.service.EquipmentCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备分类控制器
 * 提供设备分类相关的REST API接口
 */
@Slf4j
@RestController
@RequestMapping("/api/equipment-categories")
public class EquipmentCategoryController {

    @Autowired
    private EquipmentCategoryService equipmentCategoryService;

    /**
     * 创建设备分类
     */
    @PostMapping
    public Result<EquipmentCategory> createCategory(@RequestBody EquipmentCategory category) {
        log.info("创建设备分类请求，分类名称: {}", category.getName());
        return equipmentCategoryService.createCategory(category);
    }

    /**
     * 根据ID获取设备分类信息
     */
    @GetMapping("/{id}")
    public Result<EquipmentCategory> getCategoryById(@PathVariable Long id) {
        log.info("获取设备分类信息请求，分类ID: {}", id);
        EquipmentCategory category = equipmentCategoryService.getById(id);
        if (category != null && category.getDeleted() == 0) {
            return Result.success("查询成功", category);
        } else {
            return Result.error("分类不存在");
        }
    }

    /**
     * 更新设备分类信息
     */
    @PutMapping
    public Result<EquipmentCategory> updateCategory(@RequestBody EquipmentCategory category) {
        log.info("更新设备分类信息请求，分类ID: {}", category.getId());
        return equipmentCategoryService.updateCategory(category);
    }

    /**
     * 根据父分类ID查询子分类列表
     */
    @GetMapping("/parent/{parentId}")
    public Result<List<EquipmentCategory>> getCategoriesByParentId(@PathVariable Long parentId) {
        log.info("根据父分类ID查询子分类列表请求，父分类ID: {}", parentId);
        return equipmentCategoryService.findByParentId(parentId);
    }

    /**
     * 查询所有启用的分类
     */
    @GetMapping("/enabled")
    public Result<List<EquipmentCategory>> getAllEnabledCategories() {
        log.info("查询所有启用的分类请求");
        return equipmentCategoryService.findAllEnabled();
    }

    /**
     * 根据分类级别查询分类列表
     */
    @GetMapping("/level/{level}")
    public Result<List<EquipmentCategory>> getCategoriesByLevel(@PathVariable Integer level) {
        log.info("根据分类级别查询分类列表请求，级别: {}", level);
        return equipmentCategoryService.findByLevel(level);
    }

    /**
     * 构建分类树
     */
    @GetMapping("/tree")
    public Result<List<EquipmentCategory>> buildCategoryTree() {
        log.info("构建分类树请求");
        return equipmentCategoryService.buildCategoryTree();
    }

    /**
     * 删除设备分类
     */
    @DeleteMapping("/{categoryId}")
    public Result<Boolean> deleteCategory(@PathVariable Long categoryId) {
        log.info("删除设备分类请求，分类ID: {}", categoryId);
        return equipmentCategoryService.deleteCategory(categoryId);
    }
}