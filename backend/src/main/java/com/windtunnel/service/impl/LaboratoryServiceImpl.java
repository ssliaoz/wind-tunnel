package com.windtunnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.windtunnel.common.Constants;
import com.windtunnel.common.Result;
import com.windtunnel.entity.Laboratory;
import com.windtunnel.repository.LaboratoryRepository;
import com.windtunnel.service.LaboratoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 实验室服务实现类
 * 
 * 实现实验室相关的业务逻辑处理方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Service
public class LaboratoryServiceImpl extends ServiceImpl<LaboratoryRepository, Laboratory> implements LaboratoryService {

    @Autowired
    private LaboratoryRepository laboratoryRepository;

    @Override
    public Laboratory findByName(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }
        QueryWrapper<Laboratory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        queryWrapper.eq("deleted", 0); // 未删除的实验室
        return laboratoryRepository.selectOne(queryWrapper);
    }

    @Override
    public Result<Laboratory> createLaboratory(Laboratory laboratory) {
        log.info("创建实验室，实验室名称: {}", laboratory.getName());
        
        if (!StringUtils.hasText(laboratory.getName())) {
            return Result.error("实验室名称不能为空");
        }

        // 检查实验室名称是否已存在
        Laboratory existingLaboratory = findByName(laboratory.getName());
        if (existingLaboratory != null) {
            return Result.error("实验室名称已存在");
        }

        // 设置默认状态
        if (laboratory.getStatus() == null) {
            laboratory.setStatus(Constants.LaboratoryStatus.ENABLED);
        }

        int result = laboratoryRepository.insert(laboratory);
        if (result > 0) {
            log.info("实验室创建成功，实验室ID: {}", laboratory.getId());
            return Result.success("创建成功", laboratory);
        } else {
            log.error("实验室创建失败，实验室名称: {}", laboratory.getName());
            return Result.error("创建失败");
        }
    }

    @Override
    public Result<Laboratory> updateLaboratory(Laboratory laboratory) {
        log.info("更新实验室信息，实验室ID: {}", laboratory.getId());
        
        if (laboratory.getId() == null) {
            return Result.error("实验室ID不能为空");
        }

        // 查询原实验室信息
        Laboratory existingLaboratory = laboratoryRepository.selectById(laboratory.getId());
        if (existingLaboratory == null) {
            return Result.error("实验室不存在");
        }

        // 更新实验室信息
        existingLaboratory.setName(laboratory.getName());
        existingLaboratory.setCode(laboratory.getCode());
        existingLaboratory.setDescription(laboratory.getDescription());
        existingLaboratory.setManagerId(laboratory.getManagerId());
        existingLaboratory.setRemark(laboratory.getRemark());

        int result = laboratoryRepository.updateById(existingLaboratory);
        if (result > 0) {
            log.info("实验室信息更新成功，实验室ID: {}", existingLaboratory.getId());
            return Result.success("更新成功", existingLaboratory);
        } else {
            log.error("实验室信息更新失败，实验室ID: {}", existingLaboratory.getId());
            return Result.error("更新失败");
        }
    }

    @Override
    public Result<Boolean> updateLaboratoryStatus(Long laboratoryId, Integer status) {
        log.info("更新实验室状态，实验室ID: {}, 状态: {}", laboratoryId, status);
        
        if (laboratoryId == null || status == null) {
            return Result.error("实验室ID和状态不能为空");
        }

        // 检查状态值是否合法
        if (!Constants.LaboratoryStatus.ENABLED.equals(status) && !Constants.LaboratoryStatus.DISABLED.equals(status)) {
            return Result.error("状态值不合法");
        }

        Laboratory laboratory = new Laboratory();
        laboratory.setId(laboratoryId);
        laboratory.setStatus(status);

        int result = laboratoryRepository.updateById(laboratory);
        if (result > 0) {
            log.info("实验室状态更新成功，实验室ID: {}", laboratoryId);
            return Result.success("状态更新成功", true);
        } else {
            log.error("实验室状态更新失败，实验室ID: {}", laboratoryId);
            return Result.error("状态更新失败");
        }
    }

    @Override
    public Result<List<Laboratory>> findEnabledLaboratories() {
        log.info("查询所有启用的实验室");
        
        List<Laboratory> laboratories = laboratoryRepository.findEnabledLaboratories();
        return Result.success("查询成功", laboratories);
    }

    @Override
    public Result<List<Laboratory>> findByManagerId(Long managerId) {
        log.info("根据负责人ID查询实验室列表，负责人ID: {}", managerId);
        
        if (managerId == null) {
            return Result.error("负责人ID不能为空");
        }

        List<Laboratory> laboratories = laboratoryRepository.findByManagerId(managerId);
        return Result.success("查询成功", laboratories);
    }

    @Override
    public Result<List<Laboratory>> findLaboratories(int page, int size) {
        log.info("分页查询实验室列表，页码: {}, 页面大小: {}", page, size);
        
        if (page < 1) {
            page = 1;
        }
        if (size < 1) {
            size = Constants.DefaultValues.DEFAULT_PAGE_SIZE;
        }

        // 这里简化实现，实际项目中应使用分页插件
        QueryWrapper<Laboratory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0); // 未删除的实验室
        queryWrapper.orderByDesc("create_time");

        List<Laboratory> laboratories = laboratoryRepository.selectList(queryWrapper);
        return Result.success("查询成功", laboratories);
    }

    @Override
    public Result<Boolean> deleteLaboratory(Long laboratoryId) {
        log.info("删除实验室，实验室ID: {}", laboratoryId);
        
        if (laboratoryId == null) {
            return Result.error("实验室ID不能为空");
        }

        Laboratory laboratory = new Laboratory();
        laboratory.setId(laboratoryId);
        laboratory.setDeleted(1); // 逻辑删除

        int result = laboratoryRepository.updateById(laboratory);
        if (result > 0) {
            log.info("实验室删除成功，实验室ID: {}", laboratoryId);
            return Result.success("删除成功", true);
        } else {
            log.error("实验室删除失败，实验室ID: {}", laboratoryId);
            return Result.error("删除失败");
        }
    }

}