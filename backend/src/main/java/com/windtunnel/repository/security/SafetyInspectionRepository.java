package com.windtunnel.repository.security;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.windtunnel.entity.security.SafetyInspection;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 安全隐患排查数据访问层
 * 
 * 提供安全隐患排查相关的数据库操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Mapper
public interface SafetyInspectionRepository extends BaseMapper<SafetyInspection> {

    /**
     * 根据实验室ID查询隐患排查列表
     * 
     * @param laboratoryId 实验室ID
     * @return 隐患排查列表
     */
    List<SafetyInspection> findByLaboratoryId(Long laboratoryId);

    /**
     * 根据试验项目ID查询隐患排查列表
     * 
     * @param experimentProjectId 试验项目ID
     * @return 隐患排查列表
     */
    List<SafetyInspection> findByExperimentProjectId(Long experimentProjectId);

    /**
     * 根据设备ID查询隐患排查列表
     * 
     * @param equipmentId 设备ID
     * @return 隐患排查列表
     */
    List<SafetyInspection> findByEquipmentId(Long equipmentId);

    /**
     * 根据排查类型查询隐患排查列表
     * 
     * @param inspectionType 排查类型
     * @return 隐患排查列表
     */
    List<SafetyInspection> findByInspectionType(Integer inspectionType);

    /**
     * 根据排查状态查询隐患排查列表
     * 
     * @param inspectionStatus 排查状态
     * @return 隐患排查列表
     */
    List<SafetyInspection> findByInspectionStatus(Integer inspectionStatus);

    /**
     * 根据时间范围查询隐患排查列表
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 隐患排查列表
     */
    List<SafetyInspection> findByPlannedStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据排查负责人ID查询隐患排查列表
     * 
     * @param inspectorId 排查负责人ID
     * @return 隐患排查列表
     */
    List<SafetyInspection> findByInspectorId(Long inspectorId);

    /**
     * 根据整改状态查询隐患排查列表
     * 
     * @param rectificationStatus 整改状态
     * @return 隐患排查列表
     */
    List<SafetyInspection> findByRectificationStatus(Integer rectificationStatus);

    /**
     * 根据排查结果查询隐患排查列表
     * 
     * @param inspectionResult 排查结果
     * @return 隐患排查列表
     */
    List<SafetyInspection> findByInspectionResult(Integer inspectionResult);

}