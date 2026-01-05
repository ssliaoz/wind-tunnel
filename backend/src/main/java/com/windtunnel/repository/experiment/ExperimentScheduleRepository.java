package com.windtunnel.repository.experiment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.windtunnel.entity.experiment.ExperimentSchedule;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 试验排期数据访问层
 * 
 * 提供试验排期相关的数据库操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Mapper
public interface ExperimentScheduleRepository extends BaseMapper<ExperimentSchedule> {

    /**
     * 根据试验项目ID查询排期列表
     * 
     * @param experimentProjectId 试验项目ID
     * @return 排期列表
     */
    List<ExperimentSchedule> findByExperimentProjectId(Long experimentProjectId);

    /**
     * 根据实验室ID查询排期列表
     * 
     * @param laboratoryId 实验室ID
     * @return 排期列表
     */
    List<ExperimentSchedule> findByLaboratoryId(Long laboratoryId);

    /**
     * 根据设备ID查询排期列表
     * 
     * @param equipmentId 设备ID
     * @return 排期列表
     */
    List<ExperimentSchedule> findByEquipmentId(Long equipmentId);

    /**
     * 根据时间范围查询排期列表
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 排期列表
     */
    List<ExperimentSchedule> findByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据排期状态查询排期列表
     * 
     * @param status 排期状态
     * @return 排期列表
     */
    List<ExperimentSchedule> findByStatus(Integer status);

    /**
     * 根据预约人ID查询排期列表
     * 
     * @param bookerId 预约人ID
     * @return 排期列表
     */
    List<ExperimentSchedule> findByBookerId(Long bookerId);

    /**
     * 查询指定时间范围内是否存在冲突的排期
     * 
     * @param equipmentId 设备ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 冲突的排期列表
     */
    List<ExperimentSchedule> findConflictingSchedules(Long equipmentId, LocalDateTime startTime, LocalDateTime endTime);

}