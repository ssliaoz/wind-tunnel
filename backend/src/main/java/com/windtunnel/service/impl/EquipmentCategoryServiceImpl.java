package com.windtunnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.windtunnel.common.Constants;
import com.windtunnel.common.Result;
import com.windtunnel.entity.EquipmentCategory;
import com.windtunnel.repository.EquipmentCategoryRepository;
import com.windtunnel.service.EquipmentCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 设备分类服务实现类
 */
@Slf4j
@Service
public class EquipmentCategoryServiceImpl extends ServiceImpl<EquipmentCategoryRepository, EquipmentCategory> implements EquipmentCategoryService {

    @Autowired
    private EquipmentCategoryRepository equipmentCategoryRepository;

    @Override
    public EquipmentCategory findByName(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }
        QueryWrapper<EquipmentCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        queryWrapper.eq("deleted", 0); // 未删除的分类
        return equipmentCategoryRepository.selectOne(queryWrapper);
    }

    @Override
    public EquipmentCategory findByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return null;
        }
        QueryWrapper<EquipmentCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        queryWrapper.eq("deleted", 0); // 未删除的分类
        return equipmentCategoryRepository.selectOne(queryWrapper);
    }

    @Override
    public Result<EquipmentCategory> createCategory(EquipmentCategory category) {
        log.info("创建设备分类，分类名称: {}", category.getName());

        if (!StringUtils.hasText(category.getName())) {
            return Result.error("分类名称不能为空");
        }

        if (!StringUtils.hasText(category.getCode())) {
            return Result.error("分类编码不能为空");
        }

        // 检查分类名称是否已存在
        EquipmentCategory existingCategory = findByName(category.getName());
        if (existingCategory != null) {
            return Result.error("分类名称已存在");
        }

        // 检查分类编码是否已存在
        EquipmentCategory existingCategoryByCode = findByCode(category.getCode());
        if (existingCategoryByCode != null) {
            return Result.error("分类编码已存在");
        }

        // 设置默认值
        if (category.getEnabled() == null) {
            category.setEnabled(1); // 默认启用
        }
        if (category.getSort() == null) {
            category.setSort(0); // 默认排序
        }
        if (category.getParentId() == null) {
            category.setParentId(0L); // 默认为顶级分类
        }
        if (category.getLevel() == null) {
            // 如果是顶级分类，级别为1，否则根据父分类级别+1
            if (category.getParentId() == 0) {
                category.setLevel(1);
            } else {
                EquipmentCategory parentCategory = equipmentCategoryRepository.selectById(category.getParentId());
                if (parentCategory != null) {
                    category.setLevel(parentCategory.getLevel() + 1);
                } else {
                    category.setLevel(1);
                }
            }
        }

        // 设置创建时间
        category.setCreateTime(LocalDateTime.now());

        int result = equipmentCategoryRepository.insert(category);
        if (result > 0) {
            log.info("设备分类创建成功，分类ID: {}", category.getId());
            return Result.success("创建成功", category);
        } else {
            log.error("设备分类创建失败，分类名称: {}", category.getName());
            return Result.error("创建失败");
        }
    }

    @Override
    public Result<EquipmentCategory> updateCategory(EquipmentCategory category) {
        log.info("更新设备分类信息，分类ID: {}", category.getId());

        if (category.getId() == null) {
            return Result.error("分类ID不能为空");
        }

        // 查询原分类信息
        EquipmentCategory existingCategory = equipmentCategoryRepository.selectById(category.getId());
        if (existingCategory == null) {
            return Result.error("分类不存在");
        }

        // 检查分类名称是否与其他分类冲突
        EquipmentCategory existingCategoryByName = findByName(category.getName());
        if (existingCategoryByName != null && !existingCategoryByName.getId().equals(category.getId())) {
            return Result.error("分类名称已存在");
        }

        // 检查分类编码是否与其他分类冲突
        EquipmentCategory existingCategoryByCode = findByCode(category.getCode());
        if (existingCategoryByCode != null && !existingCategoryByCode.getId().equals(category.getId())) {
            return Result.error("分类编码已存在");
        }

        // 更新分类信息
        existingCategory.setName(category.getName());
        existingCategory.setCode(category.getCode());
        existingCategory.setParentId(category.getParentId());
        existingCategory.setDescription(category.getDescription());
        existingCategory.setSort(category.getSort());
        existingCategory.setEnabled(category.getEnabled());

        int result = equipmentCategoryRepository.updateById(existingCategory);
        if (result > 0) {
            log.info("设备分类信息更新成功，分类ID: {}", existingCategory.getId());
            return Result.success("更新成功", existingCategory);
        } else {
            log.error("设备分类信息更新失败，分类ID: {}", existingCategory.getId());
            return Result.error("更新失败");
        }
    }

    @Override
    public Result<List<EquipmentCategory>> findByParentId(Long parentId) {
        log.info("根据父分类ID查询子分类列表，父分类ID: {}", parentId);

        if (parentId == null) {
            parentId = 0L; // 默认查询顶级分类
        }

        List<EquipmentCategory> categories = equipmentCategoryRepository.findByParentId(parentId);
        return Result.success("查询成功", categories);
    }

    @Override
    public Result<List<EquipmentCategory>> findAllEnabled() {
        log.info("查询所有启用的分类");

        List<EquipmentCategory> categories = equipmentCategoryRepository.findAllEnabled();
        return Result.success("查询成功", categories);
    }

    @Override
    public Result<List<EquipmentCategory>> findByLevel(Integer level) {
        log.info("根据分类级别查询分类列表，级别: {}", level);

        if (level == null) {
            return Result.error("分类级别不能为空");
        }

        List<EquipmentCategory> categories = equipmentCategoryRepository.findByLevel(level);
        return Result.success("查询成功", categories);
    }

    @Override
    public Result<List<EquipmentCategory>> buildCategoryTree() {
        log.info("构建分类树");

        try {
            // 获取所有启用的分类
            QueryWrapper<EquipmentCategory> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("deleted", 0); // 未删除
            queryWrapper.eq("enabled", 1); // 启用的
            queryWrapper.orderByAsc("sort"); // 按排序号升序
            List<EquipmentCategory> allCategories = equipmentCategoryRepository.selectList(queryWrapper);

            // 构建分类树
            List<EquipmentCategory> rootCategories = allCategories.stream()
                    .filter(category -> category.getParentId() == null || category.getParentId() == 0)
                    .collect(Collectors.toList());

            // 为每个根分类构建子树
            for (EquipmentCategory rootCategory : rootCategories) {
                buildSubTree(rootCategory, allCategories);
            }

            return Result.success("构建分类树成功", rootCategories);
        } catch (Exception e) {
            log.error("构建分类树失败", e);
            return Result.error("构建分类树失败: " + e.getMessage());
        }
    }

    /**
     * 递归构建子树
     */
    private void buildSubTree(EquipmentCategory parentCategory, List<EquipmentCategory> allCategories) {
        List<EquipmentCategory> children = allCategories.stream()
                .filter(category -> parentCategory.getId().equals(category.getParentId()))
                .collect(Collectors.toList());

        if (!children.isEmpty()) {
            parentCategory.setChildren(children);
            for (EquipmentCategory child : children) {
                buildSubTree(child, allCategories);
            }
        }
    }

    @Override
    public Result<Boolean> deleteCategory(Long categoryId) {
        log.info("删除设备分类，分类ID: {}", categoryId);

        if (categoryId == null) {
            return Result.error("分类ID不能为空");
        }

        // 检查该分类下是否有子分类
        List<EquipmentCategory> subCategories = equipmentCategoryRepository.findByParentId(categoryId);
        if (!subCategories.isEmpty()) {
            return Result.error("该分类下存在子分类，无法删除");
        }

        // 检查该分类下是否有设备
        // 注意：这里需要关联设备表检查，暂时简化处理
        // 在实际应用中，应该检查wt_equipment表中是否有该分类ID的设备

        EquipmentCategory category = new EquipmentCategory();
        category.setId(categoryId);
        category.setDeleted(1); // 逻辑删除

        int result = equipmentCategoryRepository.updateById(category);
        if (result > 0) {
            log.info("设备分类删除成功，分类ID: {}", categoryId);
            return Result.success("删除成功", true);
        } else {
            log.error("设备分类删除失败，分类ID: {}", categoryId);
            return Result.error("删除失败");
        }
    }
}