package com.windtunnel.controller.security;

import com.windtunnel.common.Result;
import com.windtunnel.entity.security.RiskRegister;
import com.windtunnel.service.security.RiskRegisterService;
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
 * 风险台账控制器
 * 
 * 提供风险台账相关的REST API接口
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/api/security/risk-register")
@Tag(name = "风险台账管理", description = "风险台账相关的API接口")
public class RiskRegisterController {

    @Autowired
    private RiskRegisterService riskRegisterService;

    /**
     * 创建风险台账
     * 
     * @param riskRegister 风险台账实体
     * @return 创建结果
     */
    @Operation(summary = "创建风险台账", description = "创建新的风险台账记录")
    @PostMapping
    public Result<RiskRegister> createRiskRegister(@RequestBody RiskRegister riskRegister) {
        log.info("创建风险台账请求: {}", riskRegister.getRiskName());
        try {
            RiskRegister savedRisk = riskRegisterService.save(riskRegister);
            return Result.success("风险台账创建成功", savedRisk);
        } catch (Exception e) {
            log.error("创建风险台账失败: {}", e.getMessage(), e);
            return Result.error("创建风险台账失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取风险台账
     * 
     * @param id 风险台账ID
     * @return 风险台账信息
     */
    @Operation(summary = "根据ID获取风险台账", description = "根据ID获取风险台账的详细信息")
    @GetMapping("/{id}")
    public Result<RiskRegister> getRiskRegisterById(@PathVariable Long id) {
        log.info("获取风险台账请求，ID: {}", id);
        try {
            RiskRegister risk = riskRegisterService.findById(id);
            if (risk != null) {
                return Result.success("查询成功", risk);
            } else {
                return Result.error("风险台账不存在");
            }
        } catch (Exception e) {
            log.error("获取风险台账失败: {}", e.getMessage(), e);
            return Result.error("获取风险台账失败: " + e.getMessage());
        }
    }

    /**
     * 分页查询风险台账
     * 
     * @param page 页码
     * @param size 页面大小
     * @param sortBy 排序字段
     * @param direction 排序方向
     * @return 风险台账分页结果
     */
    @Operation(summary = "分页查询风险台账", description = "分页获取风险台账列表")
    @GetMapping
    public Result<Page<RiskRegister>> getRiskRegisters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createTime") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {
        log.info("分页查询风险台账请求，页码: {}, 大小: {}", page, size);
        try {
            Sort sort = direction.equalsIgnoreCase("ASC") ? 
                Sort.by(Sort.Direction.ASC, sortBy) : 
                Sort.by(Sort.Direction.DESC, sortBy);
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<RiskRegister> risksPage = riskRegisterService.findAll(pageable);
            return Result.success("查询成功", risksPage);
        } catch (Exception e) {
            log.error("分页查询风险台账失败: {}", e.getMessage(), e);
            return Result.error("分页查询风险台账失败: " + e.getMessage());
        }
    }

    /**
     * 根据实验室ID查询风险台账
     * 
     * @param laboratoryId 实验室ID
     * @return 风险台账列表
     */
    @Operation(summary = "根据实验室查询风险台账", description = "根据实验室ID获取风险台账列表")
    @GetMapping("/laboratory/{laboratoryId}")
    public Result<List<RiskRegister>> getRiskRegistersByLaboratoryId(@PathVariable Long laboratoryId) {
        log.info("根据实验室ID查询风险台账请求，实验室ID: {}", laboratoryId);
        try {
            List<RiskRegister> risks = riskRegisterService.findByLaboratoryId(laboratoryId);
            return Result.success("查询成功", risks);
        } catch (Exception e) {
            log.error("查询风险台账失败: {}", e.getMessage(), e);
            return Result.error("查询风险台账失败: " + e.getMessage());
        }
    }

    /**
     * 根据试验项目ID查询风险台账
     * 
     * @param experimentProjectId 试验项目ID
     * @return 风险台账列表
     */
    @Operation(summary = "根据试验项目查询风险台账", description = "根据试验项目ID获取风险台账列表")
    @GetMapping("/project/{experimentProjectId}")
    public Result<List<RiskRegister>> getRiskRegistersByProjectId(@PathVariable Long experimentProjectId) {
        log.info("根据试验项目ID查询风险台账请求，项目ID: {}", experimentProjectId);
        try {
            List<RiskRegister> risks = riskRegisterService.findByExperimentProjectId(experimentProjectId);
            return Result.success("查询成功", risks);
        } catch (Exception e) {
            log.error("查询风险台账失败: {}", e.getMessage(), e);
            return Result.error("查询风险台账失败: " + e.getMessage());
        }
    }

    /**
     * 根据设备ID查询风险台账
     * 
     * @param equipmentId 设备ID
     * @return 风险台账列表
     */
    @Operation(summary = "根据设备查询风险台账", description = "根据设备ID获取风险台账列表")
    @GetMapping("/equipment/{equipmentId}")
    public Result<List<RiskRegister>> getRiskRegistersByEquipmentId(@PathVariable Long equipmentId) {
        log.info("根据设备ID查询风险台账请求，设备ID: {}", equipmentId);
        try {
            List<RiskRegister> risks = riskRegisterService.findByEquipmentId(equipmentId);
            return Result.success("查询成功", risks);
        } catch (Exception e) {
            log.error("查询风险台账失败: {}", e.getMessage(), e);
            return Result.error("查询风险台账失败: " + e.getMessage());
        }
    }

    /**
     * 根据风险等级查询风险台账
     * 
     * @param riskLevel 风险等级
     * @return 风险台账列表
     */
    @Operation(summary = "根据风险等级查询风险台账", description = "根据风险等级获取风险台账列表")
    @GetMapping("/level/{riskLevel}")
    public Result<List<RiskRegister>> getRiskRegistersByLevel(@PathVariable Integer riskLevel) {
        log.info("根据风险等级查询风险台账请求，等级: {}", riskLevel);
        try {
            List<RiskRegister> risks = riskRegisterService.findByRiskLevel(riskLevel);
            return Result.success("查询成功", risks);
        } catch (Exception e) {
            log.error("查询风险台账失败: {}", e.getMessage(), e);
            return Result.error("查询风险台账失败: " + e.getMessage());
        }
    }

    /**
     * 根据风险状态查询风险台账
     * 
     * @param riskStatus 风险状态
     * @return 风险台账列表
     */
    @Operation(summary = "根据风险状态查询风险台账", description = "根据风险状态获取风险台账列表")
    @GetMapping("/status/{riskStatus}")
    public Result<List<RiskRegister>> getRiskRegistersByStatus(@PathVariable Integer riskStatus) {
        log.info("根据风险状态查询风险台账请求，状态: {}", riskStatus);
        try {
            List<RiskRegister> risks = riskRegisterService.findByRiskStatus(riskStatus);
            return Result.success("查询成功", risks);
        } catch (Exception e) {
            log.error("查询风险台账失败: {}", e.getMessage(), e);
            return Result.error("查询风险台账失败: " + e.getMessage());
        }
    }

    /**
     * 更新风险台账
     * 
     * @param id 风险台账ID
     * @param riskRegister 风险台账实体
     * @return 更新结果
     */
    @Operation(summary = "更新风险台账", description = "更新风险台账信息")
    @PutMapping("/{id}")
    public Result<RiskRegister> updateRiskRegister(@PathVariable Long id, 
                                                 @RequestBody RiskRegister riskRegister) {
        log.info("更新风险台账请求，ID: {}", id);
        try {
            riskRegister.setId(id);
            RiskRegister updatedRisk = riskRegisterService.save(riskRegister);
            return Result.success("风险台账更新成功", updatedRisk);
        } catch (Exception e) {
            log.error("更新风险台账失败: {}", e.getMessage(), e);
            return Result.error("更新风险台账失败: " + e.getMessage());
        }
    }

    /**
     * 删除风险台账
     * 
     * @param id 风险台账ID
     * @return 删除结果
     */
    @Operation(summary = "删除风险台账", description = "根据ID删除风险台账")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteRiskRegister(@PathVariable Long id) {
        log.info("删除风险台账请求，ID: {}", id);
        try {
            boolean result = riskRegisterService.deleteById(id);
            if (result) {
                return Result.success("风险台账删除成功", true);
            } else {
                return Result.error("风险台账删除失败");
            }
        } catch (Exception e) {
            log.error("删除风险台账失败: {}", e.getMessage(), e);
            return Result.error("删除风险台账失败: " + e.getMessage());
        }
    }

    /**
     * 更新风险状态
     * 
     * @param id 风险台账ID
     * @param riskStatus 新状态
     * @return 更新结果
     */
    @Operation(summary = "更新风险状态", description = "更新风险台账状态")
    @PutMapping("/{id}/status")
    public Result<Boolean> updateRiskStatus(@PathVariable Long id,
                                          @RequestParam Integer riskStatus) {
        log.info("更新风险台账状态请求，ID: {}, 状态: {}", id, riskStatus);
        try {
            boolean result = riskRegisterService.updateRiskStatus(id, riskStatus);
            if (result) {
                return Result.success("风险状态更新成功", true);
            } else {
                return Result.error("风险状态更新失败");
            }
        } catch (Exception e) {
            log.error("更新风险状态失败: {}", e.getMessage(), e);
            return Result.error("更新风险状态失败: " + e.getMessage());
        }
    }

    /**
     * 更新风险等级
     * 
     * @param id 风险台账ID
     * @param riskLevel 新等级
     * @return 更新结果
     */
    @Operation(summary = "更新风险等级", description = "更新风险台账等级")
    @PutMapping("/{id}/level")
    public Result<Boolean> updateRiskLevel(@PathVariable Long id,
                                         @RequestParam Integer riskLevel) {
        log.info("更新风险台账等级请求，ID: {}, 等级: {}", id, riskLevel);
        try {
            boolean result = riskRegisterService.updateRiskLevel(id, riskLevel);
            if (result) {
                return Result.success("风险等级更新成功", true);
            } else {
                return Result.error("风险等级更新失败");
            }
        } catch (Exception e) {
            log.error("更新风险等级失败: {}", e.getMessage(), e);
            return Result.error("更新风险等级失败: " + e.getMessage());
        }
    }

    /**
     * 生成风险评估报告
     * 
     * @param laboratoryId 实验室ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 风险评估报告
     */
    @Operation(summary = "生成风险评估报告", description = "生成风险评估报告")
    @GetMapping("/report")
    public Result<String> generateRiskAssessmentReport(@RequestParam(required = false) Long laboratoryId,
                                                     @RequestParam(required = false) String startTime,
                                                     @RequestParam(required = false) String endTime) {
        log.info("生成风险评估报告请求，实验室ID: {}, 时间: {} - {}", laboratoryId, startTime, endTime);
        try {
            java.time.LocalDateTime start = startTime != null ? java.time.LocalDateTime.parse(startTime) : null;
            java.time.LocalDateTime end = endTime != null ? java.time.LocalDateTime.parse(endTime) : null;
            
            String report = riskRegisterService.generateRiskAssessmentReport(laboratoryId, start, end);
            return Result.success("风险评估报告生成成功", report);
        } catch (Exception e) {
            log.error("生成风险评估报告失败: {}", e.getMessage(), e);
            return Result.error("生成风险评估报告失败: " + e.getMessage());
        }
    }

}