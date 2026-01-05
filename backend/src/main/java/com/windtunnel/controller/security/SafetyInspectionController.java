package com.windtunnel.controller.security;

import com.windtunnel.common.Result;
import com.windtunnel.entity.security.SafetyInspection;
import com.windtunnel.service.security.SafetyInspectionService;
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
 * 安全隐患排查控制器
 * 
 * 提供安全隐患排查相关的REST API接口
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/api/security/safety-inspection")
@Tag(name = "安全隐患排查管理", description = "安全隐患排查相关的API接口")
public class SafetyInspectionController {

    @Autowired
    private SafetyInspectionService safetyInspectionService;

    /**
     * 创建隐患排查记录
     * 
     * @param inspection 隐患排查实体
     * @return 创建结果
     */
    @Operation(summary = "创建隐患排查记录", description = "创建新的隐患排查记录")
    @PostMapping
    public Result<SafetyInspection> createSafetyInspection(@RequestBody SafetyInspection inspection) {
        log.info("创建隐患排查记录请求: {}", inspection.getInspectionName());
        try {
            SafetyInspection savedInspection = safetyInspectionService.save(inspection);
            return Result.success("隐患排查记录创建成功", savedInspection);
        } catch (Exception e) {
            log.error("创建隐患排查记录失败: {}", e.getMessage(), e);
            return Result.error("创建隐患排查记录失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取隐患排查记录
     * 
     * @param id 隐患排查ID
     * @return 隐患排查信息
     */
    @Operation(summary = "根据ID获取隐患排查记录", description = "根据ID获取隐患排查的详细信息")
    @GetMapping("/{id}")
    public Result<SafetyInspection> getSafetyInspectionById(@PathVariable Long id) {
        log.info("获取隐患排查记录请求，ID: {}", id);
        try {
            SafetyInspection inspection = safetyInspectionService.findById(id);
            if (inspection != null) {
                return Result.success("查询成功", inspection);
            } else {
                return Result.error("隐患排查记录不存在");
            }
        } catch (Exception e) {
            log.error("获取隐患排查记录失败: {}", e.getMessage(), e);
            return Result.error("获取隐患排查记录失败: " + e.getMessage());
        }
    }

    /**
     * 分页查询隐患排查记录
     * 
     * @param page 页码
     * @param size 页面大小
     * @param sortBy 排序字段
     * @param direction 排序方向
     * @return 隐患排查分页结果
     */
    @Operation(summary = "分页查询隐患排查记录", description = "分页获取隐患排查记录列表")
    @GetMapping
    public Result<Page<SafetyInspection>> getSafetyInspections(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createTime") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {
        log.info("分页查询隐患排查记录请求，页码: {}, 大小: {}", page, size);
        try {
            Sort sort = direction.equalsIgnoreCase("ASC") ? 
                Sort.by(Sort.Direction.ASC, sortBy) : 
                Sort.by(Sort.Direction.DESC, sortBy);
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<SafetyInspection> inspectionsPage = safetyInspectionService.findAll(pageable);
            return Result.success("查询成功", inspectionsPage);
        } catch (Exception e) {
            log.error("分页查询隐患排查记录失败: {}", e.getMessage(), e);
            return Result.error("分页查询隐患排查记录失败: " + e.getMessage());
        }
    }

    /**
     * 根据实验室ID查询隐患排查记录
     * 
     * @param laboratoryId 实验室ID
     * @return 隐患排查列表
     */
    @Operation(summary = "根据实验室查询隐患排查记录", description = "根据实验室ID获取隐患排查记录列表")
    @GetMapping("/laboratory/{laboratoryId}")
    public Result<List<SafetyInspection>> getSafetyInspectionsByLaboratoryId(@PathVariable Long laboratoryId) {
        log.info("根据实验室ID查询隐患排查记录请求，实验室ID: {}", laboratoryId);
        try {
            List<SafetyInspection> inspections = safetyInspectionService.findByLaboratoryId(laboratoryId);
            return Result.success("查询成功", inspections);
        } catch (Exception e) {
            log.error("查询隐患排查记录失败: {}", e.getMessage(), e);
            return Result.error("查询隐患排查记录失败: " + e.getMessage());
        }
    }

    /**
     * 根据试验项目ID查询隐患排查记录
     * 
     * @param experimentProjectId 试验项目ID
     * @return 隐患排查列表
     */
    @Operation(summary = "根据试验项目查询隐患排查记录", description = "根据试验项目ID获取隐患排查记录列表")
    @GetMapping("/project/{experimentProjectId}")
    public Result<List<SafetyInspection>> getSafetyInspectionsByProjectId(@PathVariable Long experimentProjectId) {
        log.info("根据试验项目ID查询隐患排查记录请求，项目ID: {}", experimentProjectId);
        try {
            List<SafetyInspection> inspections = safetyInspectionService.findByExperimentProjectId(experimentProjectId);
            return Result.success("查询成功", inspections);
        } catch (Exception e) {
            log.error("查询隐患排查记录失败: {}", e.getMessage(), e);
            return Result.error("查询隐患排查记录失败: " + e.getMessage());
        }
    }

    /**
     * 根据设备ID查询隐患排查记录
     * 
     * @param equipmentId 设备ID
     * @return 隐患排查列表
     */
    @Operation(summary = "根据设备查询隐患排查记录", description = "根据设备ID获取隐患排查记录列表")
    @GetMapping("/equipment/{equipmentId}")
    public Result<List<SafetyInspection>> getSafetyInspectionsByEquipmentId(@PathVariable Long equipmentId) {
        log.info("根据设备ID查询隐患排查记录请求，设备ID: {}", equipmentId);
        try {
            List<SafetyInspection> inspections = safetyInspectionService.findByEquipmentId(equipmentId);
            return Result.success("查询成功", inspections);
        } catch (Exception e) {
            log.error("查询隐患排查记录失败: {}", e.getMessage(), e);
            return Result.error("查询隐患排查记录失败: " + e.getMessage());
        }
    }

    /**
     * 根据排查类型查询隐患排查记录
     * 
     * @param inspectionType 排查类型
     * @return 隐患排查列表
     */
    @Operation(summary = "根据排查类型查询隐患排查记录", description = "根据排查类型获取隐患排查记录列表")
    @GetMapping("/type/{inspectionType}")
    public Result<List<SafetyInspection>> getSafetyInspectionsByType(@PathVariable Integer inspectionType) {
        log.info("根据排查类型查询隐患排查记录请求，类型: {}", inspectionType);
        try {
            List<SafetyInspection> inspections = safetyInspectionService.findByInspectionType(inspectionType);
            return Result.success("查询成功", inspections);
        } catch (Exception e) {
            log.error("查询隐患排查记录失败: {}", e.getMessage(), e);
            return Result.error("查询隐患排查记录失败: " + e.getMessage());
        }
    }

    /**
     * 根据排查状态查询隐患排查记录
     * 
     * @param inspectionStatus 排查状态
     * @return 隐患排查列表
     */
    @Operation(summary = "根据排查状态查询隐患排查记录", description = "根据排查状态获取隐患排查记录列表")
    @GetMapping("/status/{inspectionStatus}")
    public Result<List<SafetyInspection>> getSafetyInspectionsByStatus(@PathVariable Integer inspectionStatus) {
        log.info("根据排查状态查询隐患排查记录请求，状态: {}", inspectionStatus);
        try {
            List<SafetyInspection> inspections = safetyInspectionService.findByInspectionStatus(inspectionStatus);
            return Result.success("查询成功", inspections);
        } catch (Exception e) {
            log.error("查询隐患排查记录失败: {}", e.getMessage(), e);
            return Result.error("查询隐患排查记录失败: " + e.getMessage());
        }
    }

    /**
     * 根据整改状态查询隐患排查记录
     * 
     * @param rectificationStatus 整改状态
     * @return 隐患排查列表
     */
    @Operation(summary = "根据整改状态查询隐患排查记录", description = "根据整改状态获取隐患排查记录列表")
    @GetMapping("/rectification-status/{rectificationStatus}")
    public Result<List<SafetyInspection>> getSafetyInspectionsByRectificationStatus(@PathVariable Integer rectificationStatus) {
        log.info("根据整改状态查询隐患排查记录请求，整改状态: {}", rectificationStatus);
        try {
            List<SafetyInspection> inspections = safetyInspectionService.findByRectificationStatus(rectificationStatus);
            return Result.success("查询成功", inspections);
        } catch (Exception e) {
            log.error("查询隐患排查记录失败: {}", e.getMessage(), e);
            return Result.error("查询隐患排查记录失败: " + e.getMessage());
        }
    }

    /**
     * 根据排查结果查询隐患排查记录
     * 
     * @param inspectionResult 排查结果
     * @return 隐患排查列表
     */
    @Operation(summary = "根据排查结果查询隐患排查记录", description = "根据排查结果获取隐患排查记录列表")
    @GetMapping("/result/{inspectionResult}")
    public Result<List<SafetyInspection>> getSafetyInspectionsByResult(@PathVariable Integer inspectionResult) {
        log.info("根据排查结果查询隐患排查记录请求，结果: {}", inspectionResult);
        try {
            List<SafetyInspection> inspections = safetyInspectionService.findByInspectionResult(inspectionResult);
            return Result.success("查询成功", inspections);
        } catch (Exception e) {
            log.error("查询隐患排查记录失败: {}", e.getMessage(), e);
            return Result.error("查询隐患排查记录失败: " + e.getMessage());
        }
    }

    /**
     * 更新隐患排查记录
     * 
     * @param id 隐患排查ID
     * @param inspection 隐患排查实体
     * @return 更新结果
     */
    @Operation(summary = "更新隐患排查记录", description = "更新隐患排查记录信息")
    @PutMapping("/{id}")
    public Result<SafetyInspection> updateSafetyInspection(@PathVariable Long id, 
                                                         @RequestBody SafetyInspection inspection) {
        log.info("更新隐患排查记录请求，ID: {}", id);
        try {
            inspection.setId(id);
            SafetyInspection updatedInspection = safetyInspectionService.save(inspection);
            return Result.success("隐患排查记录更新成功", updatedInspection);
        } catch (Exception e) {
            log.error("更新隐患排查记录失败: {}", e.getMessage(), e);
            return Result.error("更新隐患排查记录失败: " + e.getMessage());
        }
    }

    /**
     * 删除隐患排查记录
     * 
     * @param id 隐患排查ID
     * @return 删除结果
     */
    @Operation(summary = "删除隐患排查记录", description = "根据ID删除隐患排查记录")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteSafetyInspection(@PathVariable Long id) {
        log.info("删除隐患排查记录请求，ID: {}", id);
        try {
            boolean result = safetyInspectionService.deleteById(id);
            if (result) {
                return Result.success("隐患排查记录删除成功", true);
            } else {
                return Result.error("隐患排查记录删除失败");
            }
        } catch (Exception e) {
            log.error("删除隐患排查记录失败: {}", e.getMessage(), e);
            return Result.error("删除隐患排查记录失败: " + e.getMessage());
        }
    }

    /**
     * 启动隐患排查
     * 
     * @param id 隐患排查ID
     * @return 启动结果
     */
    @Operation(summary = "启动隐患排查", description = "启动隐患排查")
    @PostMapping("/{id}/start")
    public Result<Boolean> startInspection(@PathVariable Long id) {
        log.info("启动隐患排查请求，ID: {}", id);
        try {
            boolean result = safetyInspectionService.startInspection(id);
            if (result) {
                return Result.success("隐患排查启动成功", true);
            } else {
                return Result.error("隐患排查启动失败");
            }
        } catch (Exception e) {
            log.error("启动隐患排查失败: {}", e.getMessage(), e);
            return Result.error("启动隐患排查失败: " + e.getMessage());
        }
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
    @Operation(summary = "完成隐患排查", description = "完成隐患排查")
    @PostMapping("/{id}/complete")
    public Result<Boolean> completeInspection(@PathVariable Long id,
                                            @RequestParam Integer inspectionResult,
                                            @RequestParam String hazardDescription,
                                            @RequestParam Integer hazardCount) {
        log.info("完成隐患排查请求，ID: {}, 结果: {}", id, inspectionResult);
        try {
            boolean result = safetyInspectionService.completeInspection(id, inspectionResult, hazardDescription, hazardCount);
            if (result) {
                return Result.success("隐患排查完成成功", true);
            } else {
                return Result.error("隐患排查完成失败");
            }
        } catch (Exception e) {
            log.error("完成隐患排查失败: {}", e.getMessage(), e);
            return Result.error("完成隐患排查失败: " + e.getMessage());
        }
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
    @Operation(summary = "启动整改", description = "启动隐患整改")
    @PostMapping("/{id}/start-rectification")
    public Result<Boolean> startRectification(@PathVariable Long id,
                                            @RequestParam String rectificationRequirements,
                                            @RequestParam String rectificationDeadline,
                                            @RequestParam Long rectificationPersonId) {
        log.info("启动整改请求，ID: {}, 整改负责人ID: {}", id, rectificationPersonId);
        try {
            java.time.LocalDateTime deadline = java.time.LocalDateTime.parse(rectificationDeadline);
            boolean result = safetyInspectionService.startRectification(id, rectificationRequirements, deadline, rectificationPersonId);
            if (result) {
                return Result.success("整改启动成功", true);
            } else {
                return Result.error("整改启动失败");
            }
        } catch (Exception e) {
            log.error("启动整改失败: {}", e.getMessage(), e);
            return Result.error("启动整改失败: " + e.getMessage());
        }
    }

    /**
     * 完成整改
     * 
     * @param id 隐患排查ID
     * @param acceptancePersonId 验收人ID
     * @param acceptanceResult 验收结果
     * @return 完成整改结果
     */
    @Operation(summary = "完成整改", description = "完成隐患整改")
    @PostMapping("/{id}/complete-rectification")
    public Result<Boolean> completeRectification(@PathVariable Long id,
                                              @RequestParam Long acceptancePersonId,
                                              @RequestParam String acceptanceResult) {
        log.info("完成整改请求，ID: {}, 验收人ID: {}", id, acceptancePersonId);
        try {
            boolean result = safetyInspectionService.completeRectification(id, acceptancePersonId, acceptanceResult);
            if (result) {
                return Result.success("整改完成成功", true);
            } else {
                return Result.error("整改完成失败");
            }
        } catch (Exception e) {
            log.error("完成整改失败: {}", e.getMessage(), e);
            return Result.error("完成整改失败: " + e.getMessage());
        }
    }

    /**
     * 生成隐患排查报告
     * 
     * @param laboratoryId 实验室ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 隐患排查报告
     */
    @Operation(summary = "生成隐患排查报告", description = "生成隐患排查报告")
    @GetMapping("/report")
    public Result<String> generateInspectionReport(@RequestParam(required = false) Long laboratoryId,
                                                 @RequestParam(required = false) String startTime,
                                                 @RequestParam(required = false) String endTime) {
        log.info("生成隐患排查报告请求，实验室ID: {}, 时间: {} - {}", laboratoryId, startTime, endTime);
        try {
            java.time.LocalDateTime start = startTime != null ? java.time.LocalDateTime.parse(startTime) : null;
            java.time.LocalDateTime end = endTime != null ? java.time.LocalDateTime.parse(endTime) : null;
            
            String report = safetyInspectionService.generateInspectionReport(laboratoryId, start, end);
            return Result.success("隐患排查报告生成成功", report);
        } catch (Exception e) {
            log.error("生成隐患排查报告失败: {}", e.getMessage(), e);
            return Result.error("生成隐患排查报告失败: " + e.getMessage());
        }
    }

}