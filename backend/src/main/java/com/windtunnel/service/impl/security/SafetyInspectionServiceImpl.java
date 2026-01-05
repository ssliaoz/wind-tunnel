package com.windtunnel.service.impl.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.windtunnel.entity.security.SafetyInspection;
import com.windtunnel.repository.security.SafetyInspectionRepository;
import com.windtunnel.service.security.SafetyInspectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 安全隐患排查服务实现类
 * 
 * 实现安全隐患排查相关的业务操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Service
public class SafetyInspectionServiceImpl implements SafetyInspectionService {

    @Autowired
    private SafetyInspectionRepository safetyInspectionRepository;

    /**
     * 保存隐患排查记录
     * 
     * @param inspection 隐患排查实体
     * @return 保存后的隐患排查实体
     */
    @Override
    public SafetyInspection save(SafetyInspection inspection) {
        log.debug("保存隐患排查记录: {}", inspection.getInspectionName());
        if (inspection.getId() == null || inspection.getId() <= 0) {
            safetyInspectionRepository.insert(inspection);
        } else {
            safetyInspectionRepository.updateById(inspection);
        }
        return inspection;
    }

    /**
     * 根据ID查询隐患排查记录
     * 
     * @param id 隐患排查ID
     * @return 隐患排查实体
     */
    @Override
    public SafetyInspection findById(Long id) {
        log.debug("根据ID查询隐患排查记录: {}", id);
        return safetyInspectionRepository.selectById(id);
    }

    /**
     * 查询所有隐患排查记录
     * 
     * @return 隐患排查列表
     */
    @Override
    public List<SafetyInspection> findAll() {
        log.debug("查询所有隐患排查记录");
        return safetyInspectionRepository.selectList(null);
    }

    /**
     * 分页查询隐患排查记录
     * 
     * @param pageable 分页参数
     * @return 分页结果
     */
    @Override
    public org.springframework.data.domain.Page<SafetyInspection> findAll(Pageable pageable) {
        log.debug("分页查询隐患排查记录");
        Page<SafetyInspection> mpPage = new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        Page<SafetyInspection> resultPage = safetyInspectionRepository.selectPage(mpPage, null);
        
        // 转换为Spring Data分页对象
        return new PageImpl<>(
            resultPage.getRecords(),
            pageable,
            resultPage.getTotal()
        );
    }

    /**
     * 根据实验室ID查询隐患排查记录
     * 
     * @param laboratoryId 实验室ID
     * @return 隐患排查列表
     */
    @Override
    public List<SafetyInspection> findByLaboratoryId(Long laboratoryId) {
        log.debug("根据实验室ID查询隐患排查记录: {}", laboratoryId);
        QueryWrapper<SafetyInspection> wrapper = new QueryWrapper<>();
        wrapper.eq("laboratory_id", laboratoryId);
        return safetyInspectionRepository.selectList(wrapper);
    }

    /**
     * 根据试验项目ID查询隐患排查记录
     * 
     * @param experimentProjectId 试验项目ID
     * @return 隐患排查列表
     */
    @Override
    public List<SafetyInspection> findByExperimentProjectId(Long experimentProjectId) {
        log.debug("根据试验项目ID查询隐患排查记录: {}", experimentProjectId);
        QueryWrapper<SafetyInspection> wrapper = new QueryWrapper<>();
        wrapper.eq("experiment_project_id", experimentProjectId);
        return safetyInspectionRepository.selectList(wrapper);
    }

    /**
     * 根据设备ID查询隐患排查记录
     * 
     * @param equipmentId 设备ID
     * @return 隐患排查列表
     */
    @Override
    public List<SafetyInspection> findByEquipmentId(Long equipmentId) {
        log.debug("根据设备ID查询隐患排查记录: {}", equipmentId);
        QueryWrapper<SafetyInspection> wrapper = new QueryWrapper<>();
        wrapper.eq("equipment_id", equipmentId);
        return safetyInspectionRepository.selectList(wrapper);
    }

    /**
     * 根据排查类型查询隐患排查记录
     * 
     * @param inspectionType 排查类型
     * @return 隐患排查列表
     */
    @Override
    public List<SafetyInspection> findByInspectionType(Integer inspectionType) {
        log.debug("根据排查类型查询隐患排查记录: {}", inspectionType);
        QueryWrapper<SafetyInspection> wrapper = new QueryWrapper<>();
        wrapper.eq("inspection_type", inspectionType);
        return safetyInspectionRepository.selectList(wrapper);
    }

    /**
     * 根据排查状态查询隐患排查记录
     * 
     * @param inspectionStatus 排查状态
     * @return 隐患排查列表
     */
    @Override
    public List<SafetyInspection> findByInspectionStatus(Integer inspectionStatus) {
        log.debug("根据排查状态查询隐患排查记录: {}", inspectionStatus);
        QueryWrapper<SafetyInspection> wrapper = new QueryWrapper<>();
        wrapper.eq("inspection_status", inspectionStatus);
        return safetyInspectionRepository.selectList(wrapper);
    }

    /**
     * 根据时间范围查询隐患排查记录
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 隐患排查列表
     */
    @Override
    public List<SafetyInspection> findByPlannedStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        log.debug("根据时间范围查询隐患排查记录: {} - {}", startTime, endTime);
        QueryWrapper<SafetyInspection> wrapper = new QueryWrapper<>();
        wrapper.between("planned_start_time", startTime, endTime);
        return safetyInspectionRepository.selectList(wrapper);
    }

    /**
     * 根据排查负责人ID查询隐患排查记录
     * 
     * @param inspectorId 排查负责人ID
     * @return 隐患排查列表
     */
    @Override
    public List<SafetyInspection> findByInspectorId(Long inspectorId) {
        log.debug("根据排查负责人ID查询隐患排查记录: {}", inspectorId);
        QueryWrapper<SafetyInspection> wrapper = new QueryWrapper<>();
        wrapper.eq("inspector_id", inspectorId);
        return safetyInspectionRepository.selectList(wrapper);
    }

    /**
     * 根据整改状态查询隐患排查记录
     * 
     * @param rectificationStatus 整改状态
     * @return 隐患排查列表
     */
    @Override
    public List<SafetyInspection> findByRectificationStatus(Integer rectificationStatus) {
        log.debug("根据整改状态查询隐患排查记录: {}", rectificationStatus);
        QueryWrapper<SafetyInspection> wrapper = new QueryWrapper<>();
        wrapper.eq("rectification_status", rectificationStatus);
        return safetyInspectionRepository.selectList(wrapper);
    }

    /**
     * 根据排查结果查询隐患排查记录
     * 
     * @param inspectionResult 排查结果
     * @return 隐患排查列表
     */
    @Override
    public List<SafetyInspection> findByInspectionResult(Integer inspectionResult) {
        log.debug("根据排查结果查询隐患排查记录: {}", inspectionResult);
        QueryWrapper<SafetyInspection> wrapper = new QueryWrapper<>();
        wrapper.eq("inspection_result", inspectionResult);
        return safetyInspectionRepository.selectList(wrapper);
    }

    /**
     * 删除隐患排查记录
     * 
     * @param id 隐患排查ID
     * @return 删除结果
     */
    @Override
    public boolean deleteById(Long id) {
        log.debug("删除隐患排查记录: {}", id);
        return safetyInspectionRepository.deleteById(id) > 0;
    }

    /**
     * 更新排查状态
     * 
     * @param id 隐患排查ID
     * @param inspectionStatus 新状态
     * @return 更新结果
     */
    @Override
    public boolean updateInspectionStatus(Long id, Integer inspectionStatus) {
        log.info("更新隐患排查状态: {}, 状态: {}", id, inspectionStatus);
        SafetyInspection inspection = findById(id);
        if (inspection != null) {
            inspection.setInspectionStatus(inspectionStatus);
            inspection.setUpdateTime(LocalDateTime.now());
            return safetyInspectionRepository.updateById(inspection) > 0;
        }
        return false;
    }

    /**
     * 更新整改状态
     * 
     * @param id 隐患排查ID
     * @param rectificationStatus 新整改状态
     * @return 更新结果
     */
    @Override
    public boolean updateRectificationStatus(Long id, Integer rectificationStatus) {
        log.info("更新隐患排查整改状态: {}, 状态: {}", id, rectificationStatus);
        SafetyInspection inspection = findById(id);
        if (inspection != null) {
            inspection.setRectificationStatus(rectificationStatus);
            inspection.setUpdateTime(LocalDateTime.now());
            return safetyInspectionRepository.updateById(inspection) > 0;
        }
        return false;
    }

    /**
     * 启动隐患排查
     * 
     * @param id 隐患排查ID
     * @return 启动结果
     */
    @Override
    public boolean startInspection(Long id) {
        log.info("启动隐患排查: {}", id);
        SafetyInspection inspection = findById(id);
        if (inspection != null) {
            inspection.setInspectionStatus(1); // 1-进行中
            inspection.setActualStartTime(LocalDateTime.now());
            return safetyInspectionRepository.updateById(inspection) > 0;
        }
        return false;
    }

    /**
     * 完成隐患排查
     * 
     * @param id 隐患排查ID
     * @param inspectionResult 排查结果
     * @param hazardDescription 隐患描述
     * @param hazardCount 隐患数量
     * @return 完成结果
     */
    @Override
    public boolean completeInspection(Long id, Integer inspectionResult, String hazardDescription, Integer hazardCount) {
        log.info("完成隐患排查: {}, 结果: {}", id, inspectionResult);
        SafetyInspection inspection = findById(id);
        if (inspection != null) {
            inspection.setInspectionStatus(2); // 2-已完成
            inspection.setInspectionResult(inspectionResult);
            inspection.setHazardDescription(hazardDescription);
            inspection.setHazardCount(hazardCount);
            inspection.setActualEndTime(LocalDateTime.now());
            return safetyInspectionRepository.updateById(inspection) > 0;
        }
        return false;
    }

    /**
     * 启动整改
     * 
     * @param id 隐患排查ID
     * @param rectificationRequirements 整改要求
     * @param rectificationDeadline 整改期限
     * @param rectificationPersonId 整改负责人ID
     * @return 启动整改结果
     */
    @Override
    public boolean startRectification(Long id, String rectificationRequirements, LocalDateTime rectificationDeadline, Long rectificationPersonId) {
        log.info("启动隐患整改: {}, 整改负责人ID: {}", id, rectificationPersonId);
        SafetyInspection inspection = findById(id);
        if (inspection != null) {
            inspection.setRectificationRequirements(rectificationRequirements);
            inspection.setRectificationDeadline(rectificationDeadline);
            inspection.setRectificationPersonId(rectificationPersonId);
            inspection.setRectificationStatus(1); // 1-整改中
            return safetyInspectionRepository.updateById(inspection) > 0;
        }
        return false;
    }

    /**
     * 完成整改
     * 
     * @param id 隐患排查ID
     * @param acceptancePersonId 验收人ID
     * @param acceptanceResult 验收结果
     * @return 完成整改结果
     */
    @Override
    public boolean completeRectification(Long id, Long acceptancePersonId, String acceptanceResult) {
        log.info("完成隐患整改: {}, 验收人ID: {}", id, acceptancePersonId);
        SafetyInspection inspection = findById(id);
        if (inspection != null) {
            inspection.setRectificationStatus(2); // 2-已整改
            inspection.setAcceptancePersonId(acceptancePersonId);
            inspection.setAcceptanceResult(acceptanceResult);
            inspection.setAcceptanceTime(LocalDateTime.now());
            inspection.setRectificationCompletionTime(LocalDateTime.now());
            return safetyInspectionRepository.updateById(inspection) > 0;
        }
        return false;
    }

    /**
     * 生成隐患排查报告
     * 
     * @param laboratoryId 实验室ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 隐患排查报告内容
     */
    @Override
    public String generateInspectionReport(Long laboratoryId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("生成隐患排查报告，实验室ID: {}, 时间范围: {} - {}", laboratoryId, startTime, endTime);
        
        // 查询相关隐患排查数据
        List<SafetyInspection> inspections;
        if (laboratoryId != null) {
            QueryWrapper<SafetyInspection> wrapper = new QueryWrapper<>();
            wrapper.eq("laboratory_id", laboratoryId);
            if (startTime != null && endTime != null) {
                wrapper.between("planned_start_time", startTime, endTime);
            }
            inspections = safetyInspectionRepository.selectList(wrapper);
        } else if (startTime != null && endTime != null) {
            inspections = findByPlannedStartTimeBetween(startTime, endTime);
        } else {
            inspections = findAll();
        }
        
        // 生成报告内容
        StringBuilder report = new StringBuilder();
        report.append("隐患排查报告\n");
        report.append("生成时间: ").append(LocalDateTime.now()).append("\n");
        report.append("实验室ID: ").append(laboratoryId).append("\n");
        report.append("时间范围: ").append(startTime).append(" - ").append(endTime).append("\n\n");
        
        report.append("排查统计:\n");
        int plannedCount = 0;
        int inProgressCount = 0;
        int completedCount = 0;
        int cancelledCount = 0;
        int qualifiedCount = 0;
        int partiallyQualifiedCount = 0;
        int unqualifiedCount = 0;
        int rectificationCount = 0;
        
        for (SafetyInspection inspection : inspections) {
            if (inspection.getInspectionStatus() != null) {
                switch (inspection.getInspectionStatus()) {
                    case 0: plannedCount++; break;
                    case 1: inProgressCount++; break;
                    case 2: completedCount++; break;
                    case 3: cancelledCount++; break;
                }
            }
            if (inspection.getInspectionResult() != null) {
                switch (inspection.getInspectionResult()) {
                    case 1: qualifiedCount++; break;
                    case 2: partiallyQualifiedCount++; break;
                    case 3: unqualifiedCount++; break;
                }
            }
            if (inspection.getRectificationStatus() != null && inspection.getRectificationStatus() > 0) {
                rectificationCount++;
            }
        }
        
        report.append("计划中: ").append(plannedCount).append("\n");
        report.append("进行中: ").append(inProgressCount).append("\n");
        report.append("已完成: ").append(completedCount).append("\n");
        report.append("已取消: ").append(cancelledCount).append("\n\n");
        
        report.append("合格: ").append(qualifiedCount).append("\n");
        report.append("基本合格: ").append(partiallyQualifiedCount).append("\n");
        report.append("不合格: ").append(unqualifiedCount).append("\n\n");
        
        report.append("需要整改: ").append(rectificationCount).append("\n\n");
        
        report.append("排查详情:\n");
        for (SafetyInspection inspection : inspections) {
            report.append("排查名称: ").append(inspection.getInspectionName()).append("\n");
            report.append("排查编号: ").append(inspection.getInspectionCode()).append("\n");
            report.append("排查类型: ").append(inspection.getInspectionType()).append("\n");
            report.append("排查状态: ").append(inspection.getInspectionStatus()).append("\n");
            report.append("排查结果: ").append(inspection.getInspectionResult()).append("\n");
            report.append("隐患数量: ").append(inspection.getHazardCount()).append("\n");
            report.append("隐患描述: ").append(inspection.getHazardDescription()).append("\n");
            report.append("----------\n");
        }
        
        return report.toString();
    }

}