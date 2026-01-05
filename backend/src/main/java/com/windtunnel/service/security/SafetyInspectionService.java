package com.windtunnel.service.security;

import com.windtunnel.entity.security.SafetyInspection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 安全隐患排查服务接口
 * 
 * 定义安全隐患排查相关的业务操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
public interface SafetyInspectionService {

    /**
     * 保存隐患排查记录
     * 
     * @param inspection 隐患排查实体
     * @return 保存后的隐患排查实体
     */
    SafetyInspection save(SafetyInspection inspection);

    /**
     * 根据ID查询隐患排查记录
     * 
     * @param id 隐患排查ID
     * @return 隐患排查实体
     */
    SafetyInspection findById(Long id);

    /**
     * 查询所有隐患排查记录
     * 
     * @return 隐患排查列表
     */
    List<SafetyInspection> findAll();

    /**
     * 分页查询隐患排查记录
     * 
     * @param pageable 分页参数
     * @return 分页结果
     */
    Page<SafetyInspection> findAll(Pageable pageable);

    /**
     * 根据实验室ID查询隐患排查记录
     * 
     * @param laboratoryId 实验室ID
     * @return 隐患排查列表
     */
    List<SafetyInspection> findByLaboratoryId(Long laboratoryId);

    /**
     * 根据试验项目ID查询隐患排查记录
     * 
     * @param experimentProjectId 试验项目ID
     * @return 隐患排查列表
     */
    List<SafetyInspection> findByExperimentProjectId(Long experimentProjectId);

    /**
     * 根据设备ID查询隐患排查记录
     * 
     * @param equipmentId 设备ID
     * @return 隐患排查列表
     */
    List<SafetyInspection> findByEquipmentId(Long equipmentId);

    /**
     * 根据排查类型查询隐患排查记录
     * 
     * @param inspectionType 排查类型
     * @return 隐患排查列表
     */
    List<SafetyInspection> findByInspectionType(Integer inspectionType);

    /**
     * 根据排查状态查询隐患排查记录
     * 
     * @param inspectionStatus 排查状态
     * @return 隐患排查列表
     */
    List<SafetyInspection> findByInspectionStatus(Integer inspectionStatus);

    /**
     * 根据时间范围查询隐患排查记录
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 隐患排查列表
     */
    List<SafetyInspection> findByPlannedStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据排查负责人ID查询隐患排查记录
     * 
     * @param inspectorId 排查负责人ID
     * @return 隐患排查列表
     */
    List<SafetyInspection> findByInspectorId(Long inspectorId);

    /**
     * 根据整改状态查询隐患排查记录
     * 
     * @param rectificationStatus 整改状态
     * @return 隐患排查列表
     */
    List<SafetyInspection> findByRectificationStatus(Integer rectificationStatus);

    /**
     * 根据排查结果查询隐患排查记录
     * 
     * @param inspectionResult 排查结果
     * @return 隐患排查列表
     */
    List<SafetyInspection> findByInspectionResult(Integer inspectionResult);

    /**
     * 删除隐患排查记录
     * 
     * @param id 隐患排查ID
     * @return 删除结果
     */
    boolean deleteById(Long id);

    /**
     * 更新排查状态
     * 
     * @param id 隐患排查ID
     * @param inspectionStatus 新状态
     * @return 更新结果
     */
    boolean updateInspectionStatus(Long id, Integer inspectionStatus);

    /**
     * 更新整改状态
     * 
     * @param id 隐患排查ID
     * @param rectificationStatus 新整改状态
     * @return 更新结果
     */
    boolean updateRectificationStatus(Long id, Integer rectificationStatus);

    /**
     * 启动隐患排查
     * 
     * @param id 隐患排查ID
     * @return 启动结果
     */
    boolean startInspection(Long id);

    /**
     * 完成隐患排查
     * 
     * @param id 隐患排查ID
     * @param inspectionResult 排查结果
     * @param hazardDescription 隐患描述
     * @param hazardCount 隐患数量
     * @return 完成结果
     */
    boolean completeInspection(Long id, Integer inspectionResult, String hazardDescription, Integer hazardCount);

    /**
     * 启动整改
     * 
     * @param id 隐患排查ID
     * @param rectificationRequirements 整改要求
     * @param rectificationDeadline 整改期限
     * @param rectificationPersonId 整改负责人ID
     * @return 启动整改结果
     */
    boolean startRectification(Long id, String rectificationRequirements, LocalDateTime rectificationDeadline, Long rectificationPersonId);

    /**
     * 完成整改
     * 
     * @param id 隐患排查ID
     * @param acceptancePersonId 验收人ID
     * @param acceptanceResult 验收结果
     * @return 完成整改结果
     */
    boolean completeRectification(Long id, Long acceptancePersonId, String acceptanceResult);

    /**
     * 生成隐患排查报告
     * 
     * @param laboratoryId 实验室ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 隐患排查报告内容
     */
    String generateInspectionReport(Long laboratoryId, LocalDateTime startTime, LocalDateTime endTime);

}