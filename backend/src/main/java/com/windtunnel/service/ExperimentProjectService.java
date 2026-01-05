package com.windtunnel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.windtunnel.entity.ExperimentProject;
import com.windtunnel.common.Result;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 试验项目服务接口
 * 
 * 提供试验项目相关的业务逻辑处理方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
public interface ExperimentProjectService extends IService<ExperimentProject> {

    /**
     * 根据项目编号查询试验项目
     * 
     * @param projectNo 项目编号
     * @return 试验项目实体
     */
    ExperimentProject findByProjectNo(String projectNo);

    /**
     * 创建试验项目
     * 
     * @param experimentProject 试验项目实体
     * @return 创建结果
     */
    Result<ExperimentProject> createExperimentProject(ExperimentProject experimentProject);

    /**
     * 更新试验项目信息
     * 
     * @param experimentProject 试验项目实体
     * @return 更新结果
     */
    Result<ExperimentProject> updateExperimentProject(ExperimentProject experimentProject);

    /**
     * 更新试验项目状态
     * 
     * @param projectId 项目ID
     * @param status 状态
     * @return 更新结果
     */
    Result<Boolean> updateProjectStatus(Long projectId, Integer status);

    /**
     * 根据实验室ID查询试验项目列表
     * 
     * @param laboratoryId 实验室ID
     * @return 试验项目列表
     */
    Result<List<ExperimentProject>> findByLaboratoryId(Long laboratoryId);

    /**
     * 根据负责人ID查询试验项目列表
     * 
     * @param managerId 负责人ID
     * @return 试验项目列表
     */
    Result<List<ExperimentProject>> findByManagerId(Long managerId);

    /**
     * 根据项目状态查询试验项目列表
     * 
     * @param status 项目状态
     * @return 试验项目列表
     */
    Result<List<ExperimentProject>> findByStatus(Integer status);

    /**
     * 根据时间范围查询试验项目列表
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 试验项目列表
     */
    Result<List<ExperimentProject>> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据试验类型查询试验项目列表
     * 
     * @param experimentType 试验类型
     * @return 试验项目列表
     */
    Result<List<ExperimentProject>> findByExperimentType(String experimentType);

    /**
     * 分页查询试验项目列表
     * 
     * @param page 页码
     * @param size 页面大小
     * @return 试验项目列表
     */
    Result<List<ExperimentProject>> findExperimentProjects(int page, int size);

    /**
     * 删除试验项目
     * 
     * @param projectId 项目ID
     * @return 删除结果
     */
    Result<Boolean> deleteExperimentProject(Long projectId);

    /**
     * 更新试验项目的实际花费
     * 
     * @param projectId 项目ID
     * @param actualCost 实际花费
     * @return 更新结果
     */
    Result<Boolean> updateActualCost(Long projectId, java.math.BigDecimal actualCost);

}