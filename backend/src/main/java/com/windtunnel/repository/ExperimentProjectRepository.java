package com.windtunnel.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.windtunnel.entity.ExperimentProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     * 根据项目编号查询试验项目
     * 
     * @param projectNo 项目编号
     * @return 试验项目实体
     */
    ExperimentProject findByProjectNo(@Param("projectNo") String projectNo);

    /**
     * 根据实验室ID查询试验项目列表
     * 
     * @param laboratoryId 实验室ID
     * @return 试验项目列表
     */
    List<ExperimentProject> findByLaboratoryId(@Param("laboratoryId") Long laboratoryId);

    /**
     * 根据负责人ID查询试验项目列表
     * 
     * @param managerId 负责人ID
     * @return 试验项目列表
     */
    List<ExperimentProject> findByManagerId(@Param("managerId") Long managerId);

    /**
     * 根据项目状态查询试验项目列表
     * 
     * @param status 项目状态
     * @return 试验项目列表
     */
    List<ExperimentProject> findByStatus(@Param("status") Integer status);

    /**
     * 根据时间范围查询试验项目列表
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 试验项目列表
     */
    List<ExperimentProject> findByTimeRange(@Param("startTime") LocalDateTime startTime, 
                                          @Param("endTime") LocalDateTime endTime);

    /**
     * 根据试验类型查询试验项目列表
     * 
     * @param experimentType 试验类型
     * @return 试验项目列表
     */
    List<ExperimentProject> findByExperimentType(@Param("experimentType") String experimentType);

    /**
     * 更新试验项目状态
     * 
     * @param projectId 项目ID
     * @param status 状态
     * @return 更新记录数
     */
    int updateProjectStatus(@Param("projectId") Long projectId, @Param("status") Integer status);

    /**
     * 更新试验项目的实际花费
     * 
     * @param projectId 项目ID
     * @param actualCost 实际花费
     * @return 更新记录数
     */
    int updateActualCost(@Param("projectId") Long projectId, @Param("actualCost") java.math.BigDecimal actualCost);

}