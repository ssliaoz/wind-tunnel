package com.windtunnel.controller.experiment;

import com.windtunnel.common.Result;
import com.windtunnel.entity.experiment.ExperimentSchedule;
import com.windtunnel.service.experiment.ExperimentScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 试验排期控制器
 * 
 * 提供试验排期相关的REST API接口
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/api/experiments/schedules")
@Tag(name = "试验排期管理", description = "试验排期相关的API接口")
public class ExperimentScheduleController {

    @Autowired
    private ExperimentScheduleService experimentScheduleService;

    /**
     * 创建试验排期
     * 
     * @param schedule 试验排期实体
     * @return 创建结果
     */
    @Operation(summary = "创建试验排期", description = "创建新的试验排期")
    @PostMapping
    public Result<ExperimentSchedule> createExperimentSchedule(@RequestBody ExperimentSchedule schedule) {
        log.info("创建试验排期请求: {}", schedule.getScheduleName());
        try {
            ExperimentSchedule savedSchedule = experimentScheduleService.bookExperiment(schedule);
            return Result.success("试验排期创建成功", savedSchedule);
        } catch (Exception e) {
            log.error("创建试验排期失败: {}", e.getMessage(), e);
            return Result.error("创建试验排期失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取试验排期
     * 
     * @param id 试验排期ID
     * @return 试验排期信息
     */
    @Operation(summary = "根据ID获取试验排期", description = "根据ID获取试验排期的详细信息")
    @GetMapping("/{id}")
    public Result<ExperimentSchedule> getExperimentScheduleById(@PathVariable Long id) {
        log.info("获取试验排期请求，ID: {}", id);
        try {
            ExperimentSchedule schedule = experimentScheduleService.findById(id);
            if (schedule != null) {
                return Result.success("查询成功", schedule);
            } else {
                return Result.error("试验排期不存在");
            }
        } catch (Exception e) {
            log.error("获取试验排期失败: {}", e.getMessage(), e);
            return Result.error("获取试验排期失败: " + e.getMessage());
        }
    }

    /**
     * 分页查询试验排期
     * 
     * @param page 页码
     * @param size 页面大小
     * @param sortBy 排序字段
     * @param direction 排序方向
     * @return 试验排期分页结果
     */
    @Operation(summary = "分页查询试验排期", description = "分页获取试验排期列表")
    @GetMapping
    public Result<Page<ExperimentSchedule>> getExperimentSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createTime") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {
        log.info("分页查询试验排期请求，页码: {}, 大小: {}", page, size);
        try {
            Sort sort = direction.equalsIgnoreCase("ASC") ? 
                Sort.by(Sort.Direction.ASC, sortBy) : 
                Sort.by(Sort.Direction.DESC, sortBy);
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<ExperimentSchedule> schedulesPage = experimentScheduleService.findAll(pageable);
            return Result.success("查询成功", schedulesPage);
        } catch (Exception e) {
            log.error("分页查询试验排期失败: {}", e.getMessage(), e);
            return Result.error("分页查询试验排期失败: " + e.getMessage());
        }
    }

    /**
     * 根据试验项目ID查询排期
     * 
     * @param experimentProjectId 试验项目ID
     * @return 试验排期列表
     */
    @Operation(summary = "根据试验项目查询排期", description = "根据试验项目ID获取排期列表")
    @GetMapping("/project/{experimentProjectId}")
    public Result<List<ExperimentSchedule>> getExperimentSchedulesByProjectId(@PathVariable Long experimentProjectId) {
        log.info("根据试验项目ID查询排期请求，项目ID: {}", experimentProjectId);
        try {
            List<ExperimentSchedule> schedules = experimentScheduleService.findByExperimentProjectId(experimentProjectId);
            return Result.success("查询成功", schedules);
        } catch (Exception e) {
            log.error("查询试验排期失败: {}", e.getMessage(), e);
            return Result.error("查询试验排期失败: " + e.getMessage());
        }
    }

    /**
     * 根据实验室ID查询排期
     * 
     * @param laboratoryId 实验室ID
     * @return 试验排期列表
     */
    @Operation(summary = "根据实验室查询排期", description = "根据实验室ID获取排期列表")
    @GetMapping("/laboratory/{laboratoryId}")
    public Result<List<ExperimentSchedule>> getExperimentSchedulesByLaboratoryId(@PathVariable Long laboratoryId) {
        log.info("根据实验室ID查询排期请求，实验室ID: {}", laboratoryId);
        try {
            List<ExperimentSchedule> schedules = experimentScheduleService.findByLaboratoryId(laboratoryId);
            return Result.success("查询成功", schedules);
        } catch (Exception e) {
            log.error("查询试验排期失败: {}", e.getMessage(), e);
            return Result.error("查询试验排期失败: " + e.getMessage());
        }
    }

    /**
     * 根据设备ID查询排期
     * 
     * @param equipmentId 设备ID
     * @return 试验排期列表
     */
    @Operation(summary = "根据设备查询排期", description = "根据设备ID获取排期列表")
    @GetMapping("/equipment/{equipmentId}")
    public Result<List<ExperimentSchedule>> getExperimentSchedulesByEquipmentId(@PathVariable Long equipmentId) {
        log.info("根据设备ID查询排期请求，设备ID: {}", equipmentId);
        try {
            List<ExperimentSchedule> schedules = experimentScheduleService.findByEquipmentId(equipmentId);
            return Result.success("查询成功", schedules);
        } catch (Exception e) {
            log.error("查询试验排期失败: {}", e.getMessage(), e);
            return Result.error("查询试验排期失败: " + e.getMessage());
        }
    }

    /**
     * 根据排期状态查询排期
     * 
     * @param status 排期状态
     * @return 试验排期列表
     */
    @Operation(summary = "根据状态查询排期", description = "根据排期状态获取排期列表")
    @GetMapping("/status/{status}")
    public Result<List<ExperimentSchedule>> getExperimentSchedulesByStatus(@PathVariable Integer status) {
        log.info("根据状态查询排期请求，状态: {}", status);
        try {
            List<ExperimentSchedule> schedules = experimentScheduleService.findByStatus(status);
            return Result.success("查询成功", schedules);
        } catch (Exception e) {
            log.error("查询试验排期失败: {}", e.getMessage(), e);
            return Result.error("查询试验排期失败: " + e.getMessage());
        }
    }

    /**
     * 更新试验排期
     * 
     * @param id 排期ID
     * @param schedule 试验排期实体
     * @return 更新结果
     */
    @Operation(summary = "更新试验排期", description = "更新试验排期信息")
    @PutMapping("/{id}")
    public Result<ExperimentSchedule> updateExperimentSchedule(@PathVariable Long id, 
                                                            @RequestBody ExperimentSchedule schedule) {
        log.info("更新试验排期请求，ID: {}", id);
        try {
            schedule.setId(id);
            ExperimentSchedule updatedSchedule = experimentScheduleService.save(schedule);
            return Result.success("试验排期更新成功", updatedSchedule);
        } catch (Exception e) {
            log.error("更新试验排期失败: {}", e.getMessage(), e);
            return Result.error("更新试验排期失败: " + e.getMessage());
        }
    }

    /**
     * 删除试验排期
     * 
     * @param id 排期ID
     * @return 删除结果
     */
    @Operation(summary = "删除试验排期", description = "根据ID删除试验排期")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteExperimentSchedule(@PathVariable Long id) {
        log.info("删除试验排期请求，ID: {}", id);
        try {
            boolean result = experimentScheduleService.deleteById(id);
            if (result) {
                return Result.success("试验排期删除成功", true);
            } else {
                return Result.error("试验排期删除失败");
            }
        } catch (Exception e) {
            log.error("删除试验排期失败: {}", e.getMessage(), e);
            return Result.error("删除试验排期失败: " + e.getMessage());
        }
    }

    /**
     * 确认试验排期
     * 
     * @param id 排期ID
     * @param confirmerId 确认人ID
     * @return 确认结果
     */
    @Operation(summary = "确认试验排期", description = "确认试验排期")
    @PostMapping("/{id}/confirm")
    public Result<Boolean> confirmSchedule(@PathVariable Long id,
                                         @RequestParam Long confirmerId) {
        log.info("确认试验排期请求，ID: {}, 确认人ID: {}", id, confirmerId);
        try {
            boolean result = experimentScheduleService.confirmSchedule(id, confirmerId);
            if (result) {
                return Result.success("试验排期确认成功", true);
            } else {
                return Result.error("试验排期确认失败");
            }
        } catch (Exception e) {
            log.error("确认试验排期失败: {}", e.getMessage(), e);
            return Result.error("确认试验排期失败: " + e.getMessage());
        }
    }

    /**
     * 取消试验排期
     * 
     * @param id 排期ID
     * @param cancellationReason 取消原因
     * @return 取消结果
     */
    @Operation(summary = "取消试验排期", description = "取消试验排期")
    @PostMapping("/{id}/cancel")
    public Result<Boolean> cancelSchedule(@PathVariable Long id,
                                        @RequestParam String cancellationReason) {
        log.info("取消试验排期请求，ID: {}, 原因: {}", id, cancellationReason);
        try {
            boolean result = experimentScheduleService.cancelSchedule(id, cancellationReason);
            if (result) {
                return Result.success("试验排期取消成功", true);
            } else {
                return Result.error("试验排期取消失败");
            }
        } catch (Exception e) {
            log.error("取消试验排期失败: {}", e.getMessage(), e);
            return Result.error("取消试验排期失败: " + e.getMessage());
        }
    }

    /**
     * 检查设备在指定时间段是否可用
     * 
     * @param equipmentId 设备ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 设备可用性结果
     */
    @Operation(summary = "检查设备可用性", description = "检查设备在指定时间段是否可用")
    @GetMapping("/equipment/{equipmentId}/available")
    public Result<Boolean> checkEquipmentAvailability(@PathVariable Long equipmentId,
                                                    @RequestParam String startTime,
                                                    @RequestParam String endTime) {
        log.info("检查设备可用性请求，设备ID: {}, 时间: {} - {}", equipmentId, startTime, endTime);
        try {
            // 这里需要解析时间字符串为LocalDateTime，实际实现中需要使用合适的日期时间解析方法
            boolean available = experimentScheduleService.isEquipmentAvailable(equipmentId, 
                java.time.LocalDateTime.parse(startTime), java.time.LocalDateTime.parse(endTime));
            return Result.success("设备可用性检查完成", available);
        } catch (Exception e) {
            log.error("检查设备可用性失败: {}", e.getMessage(), e);
            return Result.error("检查设备可用性失败: " + e.getMessage());
        }
    }

}