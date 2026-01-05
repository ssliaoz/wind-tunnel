package com.windtunnel.service.impl.experiment;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.windtunnel.entity.experiment.ExperimentSchedule;
import com.windtunnel.repository.experiment.ExperimentScheduleRepository;
import com.windtunnel.service.experiment.ExperimentScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 试验排期服务实现类
 * 
 * 实现试验排期相关的业务操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Service
public class ExperimentScheduleServiceImpl implements ExperimentScheduleService {

    @Autowired
    private ExperimentScheduleRepository experimentScheduleRepository;

    /**
     * 保存试验排期
     * 
     * @param schedule 试验排期实体
     * @return 保存后的试验排期实体
     */
    @Override
    public ExperimentSchedule save(ExperimentSchedule schedule) {
        log.debug("保存试验排期: {}", schedule.getScheduleName());
        if (schedule.getId() == null || schedule.getId() <= 0) {
            experimentScheduleRepository.insert(schedule);
        } else {
            experimentScheduleRepository.updateById(schedule);
        }
        return schedule;
    }

    /**
     * 根据ID查询试验排期
     * 
     * @param id 试验排期ID
     * @return 试验排期实体
     */
    @Override
    public ExperimentSchedule findById(Long id) {
        log.debug("根据ID查询试验排期: {}", id);
        return experimentScheduleRepository.selectById(id);
    }

    /**
     * 查询所有试验排期
     * 
     * @return 试验排期列表
     */
    @Override
    public List<ExperimentSchedule> findAll() {
        log.debug("查询所有试验排期");
        return experimentScheduleRepository.selectList(null);
    }

    /**
     * 分页查询试验排期
     * 
     * @param pageable 分页参数
     * @return 分页结果
     */
    @Override
    public org.springframework.data.domain.Page<ExperimentSchedule> findAll(Pageable pageable) {
        log.debug("分页查询试验排期");
        Page<ExperimentSchedule> mpPage = new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        Page<ExperimentSchedule> resultPage = experimentScheduleRepository.selectPage(mpPage, null);
        
        // 转换为Spring Data分页对象
        return new PageImpl<>(
            resultPage.getRecords(),
            pageable,
            resultPage.getTotal()
        );
    }

    /**
     * 根据试验项目ID查询排期
     * 
     * @param experimentProjectId 试验项目ID
     * @return 试验排期列表
     */
    @Override
    public List<ExperimentSchedule> findByExperimentProjectId(Long experimentProjectId) {
        log.debug("根据试验项目ID查询排期: {}", experimentProjectId);
        QueryWrapper<ExperimentSchedule> wrapper = new QueryWrapper<>();
        wrapper.eq("experiment_project_id", experimentProjectId);
        return experimentScheduleRepository.selectList(wrapper);
    }

    /**
     * 根据实验室ID查询排期
     * 
     * @param laboratoryId 实验室ID
     * @return 试验排期列表
     */
    @Override
    public List<ExperimentSchedule> findByLaboratoryId(Long laboratoryId) {
        log.debug("根据实验室ID查询排期: {}", laboratoryId);
        QueryWrapper<ExperimentSchedule> wrapper = new QueryWrapper<>();
        wrapper.eq("laboratory_id", laboratoryId);
        return experimentScheduleRepository.selectList(wrapper);
    }

    /**
     * 根据设备ID查询排期
     * 
     * @param equipmentId 设备ID
     * @return 试验排期列表
     */
    @Override
    public List<ExperimentSchedule> findByEquipmentId(Long equipmentId) {
        log.debug("根据设备ID查询排期: {}", equipmentId);
        QueryWrapper<ExperimentSchedule> wrapper = new QueryWrapper<>();
        wrapper.eq("equipment_id", equipmentId);
        return experimentScheduleRepository.selectList(wrapper);
    }

    /**
     * 根据时间范围查询排期
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 试验排期列表
     */
    @Override
    public List<ExperimentSchedule> findByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        log.debug("根据时间范围查询排期: {} - {}", startTime, endTime);
        QueryWrapper<ExperimentSchedule> wrapper = new QueryWrapper<>();
        wrapper.between("start_time", startTime, endTime);
        return experimentScheduleRepository.selectList(wrapper);
    }

    /**
     * 根据排期状态查询排期
     * 
     * @param status 排期状态
     * @return 试验排期列表
     */
    @Override
    public List<ExperimentSchedule> findByStatus(Integer status) {
        log.debug("根据排期状态查询排期: {}", status);
        QueryWrapper<ExperimentSchedule> wrapper = new QueryWrapper<>();
        wrapper.eq("status", status);
        return experimentScheduleRepository.selectList(wrapper);
    }

    /**
     * 根据预约人ID查询排期
     * 
     * @param bookerId 预约人ID
     * @return 试验排期列表
     */
    @Override
    public List<ExperimentSchedule> findByBookerId(Long bookerId) {
        log.debug("根据预约人ID查询排期: {}", bookerId);
        QueryWrapper<ExperimentSchedule> wrapper = new QueryWrapper<>();
        wrapper.eq("booker_id", bookerId);
        return experimentScheduleRepository.selectList(wrapper);
    }

    /**
     * 删除试验排期
     * 
     * @param id 试验排期ID
     * @return 删除结果
     */
    @Override
    public boolean deleteById(Long id) {
        log.debug("删除试验排期: {}", id);
        return experimentScheduleRepository.deleteById(id) > 0;
    }

    /**
     * 预约试验
     * 
     * @param schedule 试验排期实体
     * @return 预约结果
     */
    @Override
    public ExperimentSchedule bookExperiment(ExperimentSchedule schedule) {
        log.info("预约试验: {}", schedule.getScheduleName());
        // 设置为已预约状态
        schedule.setStatus(0); // 0-已预约
        schedule.setBookingTime(LocalDateTime.now());
        return save(schedule);
    }

    /**
     * 确认试验排期
     * 
     * @param id 排期ID
     * @param confirmerId 确认人ID
     * @return 确认结果
     */
    @Override
    public boolean confirmSchedule(Long id, Long confirmerId) {
        log.info("确认试验排期: {}, 确认人: {}", id, confirmerId);
        ExperimentSchedule schedule = findById(id);
        if (schedule != null) {
            schedule.setStatus(1); // 1-进行中
            schedule.setConfirmerId(confirmerId);
            schedule.setConfirmationTime(LocalDateTime.now());
            return experimentScheduleRepository.updateById(schedule) > 0;
        }
        return false;
    }

    /**
     * 取消试验排期
     * 
     * @param id 排期ID
     * @param cancellationReason 取消原因
     * @return 取消结果
     */
    @Override
    public boolean cancelSchedule(Long id, String cancellationReason) {
        log.info("取消试验排期: {}, 原因: {}", id, cancellationReason);
        ExperimentSchedule schedule = findById(id);
        if (schedule != null) {
            schedule.setStatus(3); // 3-已取消
            schedule.setCancellationReason(cancellationReason);
            schedule.setCancellationTime(LocalDateTime.now());
            return experimentScheduleRepository.updateById(schedule) > 0;
        }
        return false;
    }

    /**
     * 更新排期状态
     * 
     * @param id 排期ID
     * @param status 新状态
     * @return 更新结果
     */
    @Override
    public boolean updateStatus(Long id, Integer status) {
        log.info("更新排期状态: {}, 状态: {}", id, status);
        ExperimentSchedule schedule = findById(id);
        if (schedule != null) {
            schedule.setStatus(status);
            schedule.setUpdateTime(LocalDateTime.now());
            return experimentScheduleRepository.updateById(schedule) > 0;
        }
        return false;
    }

    /**
     * 检查设备在指定时间段是否可用
     * 
     * @param equipmentId 设备ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 设备是否可用
     */
    @Override
    public boolean isEquipmentAvailable(Long equipmentId, LocalDateTime startTime, LocalDateTime endTime) {
        log.debug("检查设备可用性: {}, 时间: {} - {}", equipmentId, startTime, endTime);
        QueryWrapper<ExperimentSchedule> wrapper = new QueryWrapper<>();
        wrapper.eq("equipment_id", equipmentId)
               .eq("status", 0) // 已预约状态
               .and(w -> w.between("start_time", startTime, endTime)
                         .or()
                         .between("end_time", startTime, endTime)
                         .or()
                         .and(sub -> sub.le("start_time", startTime).ge("end_time", endTime)));
        
        List<ExperimentSchedule> conflictingSchedules = experimentScheduleRepository.selectList(wrapper);
        return conflictingSchedules.isEmpty();
    }

}