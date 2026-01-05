package com.windtunnel.repository.experiment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.windtunnel.entity.experiment.ExperimentProject;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 试验项目数据访问层
 * 
 * 提供试验项目相关的数据库操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Mapper
public interface ExperimentProjectRepository extends BaseMapper<ExperimentProject> {

    /**
     * 根据实验室ID查询试验项目列表
     * 
     * @param laboratoryId 实验室ID
     * @return 试验项目列表
     */
    List<ExperimentProject> findByLaboratoryId(Long laboratoryId);

    /**
     * 根据试验负责人ID查询试验项目列表
     * 
     * @param projectLeaderId 试验负责人ID
     * @return 试验项目列表
     */
    List<ExperimentProject> findByProjectLeaderId(Long projectLeaderId);

    /**
     * 根据试验状态查询试验项目列表
     * 
     * @param status 试验状态
     * @return 试验项目列表
     */
    List<ExperimentProject> findByStatus(Integer status);

    /**
     * 根据时间范围查询试验项目列表
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 试验项目列表
     */
    List<ExperimentProject> findByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据项目类型查询试验项目列表
     * 
     * @param projectType 项目类型
     * @return 试验项目列表
     */
    List<ExperimentProject> findByProjectType(String projectType);

    /**
     * 根据成本管控状态查询试验项目列表
     * 
     * @param costControlStatus 成本管控状态
     * @return 试验项目列表
     */
    List<ExperimentProject> findByCostControlStatus(Integer costControlStatus);

}