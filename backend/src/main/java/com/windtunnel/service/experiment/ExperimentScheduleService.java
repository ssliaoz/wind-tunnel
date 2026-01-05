package com.windtunnel.service.experiment;

import com.windtunnel.entity.experiment.ExperimentSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 试验排期服务接口
 * 
 * 定义试验排期相关的业务操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
public interface ExperimentScheduleService {

    /**
     * 保存试验排期
     * 
     * @param schedule 试验排期实体
     * @return 保存后的试验排期实体
     */
    ExperimentSchedule save(ExperimentSchedule schedule);

    /**
     * 根据ID查询试验排期
     * 
     * @param id 试验排期ID
     * @return 试验排期实体
     */
    ExperimentSchedule findById(Long id);

    /**
     * 查询所有试验排期
     * 
     * @return 试验排期列表
     */
    List<ExperimentSchedule> findAll();

    /**
     * 分页查询试验排期
     * 
     * @param pageable 分页参数
     * @return 分页结果
     */
    Page<ExperimentSchedule> findAll(Pageable pageable);

    /**
     * 根据试验项目ID查询排期
     * 
     * @param experimentProjectId 试验项目ID
     * @return 试验排期列表
     */
    List<ExperimentSchedule> findByExperimentProjectId(Long experimentProjectId);

    /**
     * 根据实验室ID查询排期
     * 
     * @param laboratoryId 实验室ID
     * @return 试验排期列表
     */
    List<ExperimentSchedule> findByLaboratoryId(Long laboratoryId);

    /**
     * 根据设备ID查询排期
     * 
     * @param equipmentId 设备ID
     * @return 试验排期列表
     */
    List<ExperimentSchedule> findByEquipmentId(Long equipmentId);

    /**
     * 根据时间范围查询排期
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 试验排期列表
     */
    List<ExperimentSchedule> findByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据排期状态查询排期
     * 
     * @param status 排期状态
     * @return 试验排期列表
     */
    List<ExperimentSchedule> findByStatus(Integer status);

    /**
     * 根据预约人ID查询排期
     * 
     * @param bookerId 预约人ID
     * @return 试验排期列表
     */
    List<ExperimentSchedule> findByBookerId(Long bookerId);

    /**
     * 删除试验排期
     * 
     * @param id 试验排期ID
     * @return 删除结果
     */
    boolean deleteById(Long id);

    /**
     * 预约试验
     * 
     * @param schedule 试验排期实体
     * @return 预约结果
     */
    ExperimentSchedule bookExperiment(ExperimentSchedule schedule);

    /**
     * 确认试验排期
     * 
     * @param id 排期ID
     * @param confirmerId 确认人ID
     * @return 确认结果
     */
    boolean confirmSchedule(Long id, Long confirmerId);

    /**
     * 取消试验排期
     * 
     * @param id 排期ID
     * @param cancellationReason 取消原因
     * @return 取消结果
     */
    boolean cancelSchedule(Long id, String cancellationReason);

    /**
     * 更新排期状态
     * 
     * @param id 排期ID
     * @param status 新状态
     * @return 更新结果
     */
    boolean updateStatus(Long id, Integer status);

    /**
     * 检查设备在指定时间段是否可用
     * 
     * @param equipmentId 设备ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 设备是否可用
     */
    boolean isEquipmentAvailable(Long equipmentId, LocalDateTime startTime, LocalDateTime endTime);

}