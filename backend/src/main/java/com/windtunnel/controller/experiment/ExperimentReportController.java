package com.windtunnel.controller.experiment;

import com.windtunnel.common.Result;
import com.windtunnel.entity.experiment.ExperimentReport;
import com.windtunnel.service.experiment.ExperimentReportService;
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
 * 试验报告控制器
 * 
 * 提供试验报告相关的REST API接口
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/api/experiments/reports")
@Tag(name = "试验报告管理", description = "试验报告相关的API接口")
public class ExperimentReportController {

    @Autowired
    private ExperimentReportService experimentReportService;

    /**
     * 创建试验报告
     * 
     * @param report 试验报告实体
     * @return 创建结果
     */
    @Operation(summary = "创建试验报告", description = "创建新的试验报告")
    @PostMapping
    public Result<ExperimentReport> createExperimentReport(@RequestBody ExperimentReport report) {
        log.info("创建试验报告请求: {}", report.getReportTitle());
        try {
            ExperimentReport savedReport = experimentReportService.save(report);
            return Result.success("试验报告创建成功", savedReport);
        } catch (Exception e) {
            log.error("创建试验报告失败: {}", e.getMessage(), e);
            return Result.error("创建试验报告失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取试验报告
     * 
     * @param id 试验报告ID
     * @return 试验报告信息
     */
    @Operation(summary = "根据ID获取试验报告", description = "根据ID获取试验报告的详细信息")
    @GetMapping("/{id}")
    public Result<ExperimentReport> getExperimentReportById(@PathVariable Long id) {
        log.info("获取试验报告请求，ID: {}", id);
        try {
            ExperimentReport report = experimentReportService.findById(id);
            if (report != null) {
                return Result.success("查询成功", report);
            } else {
                return Result.error("试验报告不存在");
            }
        } catch (Exception e) {
            log.error("获取试验报告失败: {}", e.getMessage(), e);
            return Result.error("获取试验报告失败: " + e.getMessage());
        }
    }

    /**
     * 分页查询试验报告
     * 
     * @param page 页码
     * @param size 页面大小
     * @param sortBy 排序字段
     * @param direction 排序方向
     * @return 试验报告分页结果
     */
    @Operation(summary = "分页查询试验报告", description = "分页获取试验报告列表")
    @GetMapping
    public Result<Page<ExperimentReport>> getExperimentReports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createTime") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {
        log.info("分页查询试验报告请求，页码: {}, 大小: {}", page, size);
        try {
            Sort sort = direction.equalsIgnoreCase("ASC") ? 
                Sort.by(Sort.Direction.ASC, sortBy) : 
                Sort.by(Sort.Direction.DESC, sortBy);
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<ExperimentReport> reportsPage = experimentReportService.findAll(pageable);
            return Result.success("查询成功", reportsPage);
        } catch (Exception e) {
            log.error("分页查询试验报告失败: {}", e.getMessage(), e);
            return Result.error("分页查询试验报告失败: " + e.getMessage());
        }
    }

    /**
     * 根据试验项目ID查询报告
     * 
     * @param experimentProjectId 试验项目ID
     * @return 试验报告列表
     */
    @Operation(summary = "根据试验项目查询报告", description = "根据试验项目ID获取报告列表")
    @GetMapping("/project/{experimentProjectId}")
    public Result<List<ExperimentReport>> getExperimentReportsByProjectId(@PathVariable Long experimentProjectId) {
        log.info("根据试验项目ID查询报告请求，项目ID: {}", experimentProjectId);
        try {
            List<ExperimentReport> reports = experimentReportService.findByExperimentProjectId(experimentProjectId);
            return Result.success("查询成功", reports);
        } catch (Exception e) {
            log.error("查询试验报告失败: {}", e.getMessage(), e);
            return Result.error("查询试验报告失败: " + e.getMessage());
        }
    }

    /**
     * 根据报告状态查询报告
     * 
     * @param reportStatus 报告状态
     * @return 试验报告列表
     */
    @Operation(summary = "根据状态查询报告", description = "根据报告状态获取报告列表")
    @GetMapping("/status/{reportStatus}")
    public Result<List<ExperimentReport>> getExperimentReportsByStatus(@PathVariable Integer reportStatus) {
        log.info("根据报告状态查询报告请求，状态: {}", reportStatus);
        try {
            List<ExperimentReport> reports = experimentReportService.findByReportStatus(reportStatus);
            return Result.success("查询成功", reports);
        } catch (Exception e) {
            log.error("查询试验报告失败: {}", e.getMessage(), e);
            return Result.error("查询试验报告失败: " + e.getMessage());
        }
    }

    /**
     * 根据生成人ID查询报告
     * 
     * @param generatorId 生成人ID
     * @return 试验报告列表
     */
    @Operation(summary = "根据生成人查询报告", description = "根据生成人ID获取报告列表")
    @GetMapping("/generator/{generatorId}")
    public Result<List<ExperimentReport>> getExperimentReportsByGeneratorId(@PathVariable Long generatorId) {
        log.info("根据生成人ID查询报告请求，生成人ID: {}", generatorId);
        try {
            List<ExperimentReport> reports = experimentReportService.findByGeneratorId(generatorId);
            return Result.success("查询成功", reports);
        } catch (Exception e) {
            log.error("查询试验报告失败: {}", e.getMessage(), e);
            return Result.error("查询试验报告失败: " + e.getMessage());
        }
    }

    /**
     * 根据审核人ID查询报告
     * 
     * @param reviewerId 审核人ID
     * @return 试验报告列表
     */
    @Operation(summary = "根据审核人查询报告", description = "根据审核人ID获取报告列表")
    @GetMapping("/reviewer/{reviewerId}")
    public Result<List<ExperimentReport>> getExperimentReportsByReviewerId(@PathVariable Long reviewerId) {
        log.info("根据审核人ID查询报告请求，审核人ID: {}", reviewerId);
        try {
            List<ExperimentReport> reports = experimentReportService.findByReviewerId(reviewerId);
            return Result.success("查询成功", reports);
        } catch (Exception e) {
            log.error("查询试验报告失败: {}", e.getMessage(), e);
            return Result.error("查询试验报告失败: " + e.getMessage());
        }
    }

    /**
     * 根据文件类型查询报告
     * 
     * @param fileType 文件类型
     * @return 试验报告列表
     */
    @Operation(summary = "根据文件类型查询报告", description = "根据文件类型获取报告列表")
    @GetMapping("/file-type/{fileType}")
    public Result<List<ExperimentReport>> getExperimentReportsByFileType(@PathVariable String fileType) {
        log.info("根据文件类型查询报告请求，类型: {}", fileType);
        try {
            List<ExperimentReport> reports = experimentReportService.findByFileType(fileType);
            return Result.success("查询成功", reports);
        } catch (Exception e) {
            log.error("查询试验报告失败: {}", e.getMessage(), e);
            return Result.error("查询试验报告失败: " + e.getMessage());
        }
    }

    /**
     * 根据关键词查询报告
     * 
     * @param keywords 关键词
     * @return 试验报告列表
     */
    @Operation(summary = "根据关键词查询报告", description = "根据关键词模糊查询报告")
    @GetMapping("/search")
    public Result<List<ExperimentReport>> searchExperimentReports(@RequestParam String keywords) {
        log.info("根据关键词查询报告请求，关键词: {}", keywords);
        try {
            List<ExperimentReport> reports = experimentReportService.findByKeywordsContaining(keywords);
            return Result.success("查询成功", reports);
        } catch (Exception e) {
            log.error("查询试验报告失败: {}", e.getMessage(), e);
            return Result.error("查询试验报告失败: " + e.getMessage());
        }
    }

    /**
     * 更新试验报告
     * 
     * @param id 报告ID
     * @param report 试验报告实体
     * @return 更新结果
     */
    @Operation(summary = "更新试验报告", description = "更新试验报告信息")
    @PutMapping("/{id}")
    public Result<ExperimentReport> updateExperimentReport(@PathVariable Long id, 
                                                         @RequestBody ExperimentReport report) {
        log.info("更新试验报告请求，ID: {}", id);
        try {
            report.setId(id);
            ExperimentReport updatedReport = experimentReportService.save(report);
            return Result.success("试验报告更新成功", updatedReport);
        } catch (Exception e) {
            log.error("更新试验报告失败: {}", e.getMessage(), e);
            return Result.error("更新试验报告失败: " + e.getMessage());
        }
    }

    /**
     * 删除试验报告
     * 
     * @param id 报告ID
     * @return 删除结果
     */
    @Operation(summary = "删除试验报告", description = "根据ID删除试验报告")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteExperimentReport(@PathVariable Long id) {
        log.info("删除试验报告请求，ID: {}", id);
        try {
            boolean result = experimentReportService.deleteById(id);
            if (result) {
                return Result.success("试验报告删除成功", true);
            } else {
                return Result.error("试验报告删除失败");
            }
        } catch (Exception e) {
            log.error("删除试验报告失败: {}", e.getMessage(), e);
            return Result.error("删除试验报告失败: " + e.getMessage());
        }
    }

    /**
     * 生成试验报告
     * 
     * @param experimentProjectId 试验项目ID
     * @param generatorId 生成人ID
     * @return 生成的报告
     */
    @Operation(summary = "生成试验报告", description = "生成试验报告")
    @PostMapping("/generate")
    public Result<ExperimentReport> generateReport(@RequestParam Long experimentProjectId,
                                                 @RequestParam Long generatorId) {
        log.info("生成试验报告请求，项目ID: {}, 生成人ID: {}", experimentProjectId, generatorId);
        try {
            ExperimentReport report = experimentReportService.generateReport(experimentProjectId, generatorId);
            return Result.success("试验报告生成成功", report);
        } catch (Exception e) {
            log.error("生成试验报告失败: {}", e.getMessage(), e);
            return Result.error("生成试验报告失败: " + e.getMessage());
        }
    }

    /**
     * 审核试验报告
     * 
     * @param id 报告ID
     * @param reviewerId 审核人ID
     * @param approved 是否批准
     * @param reviewComments 审核意见
     * @return 审核结果
     */
    @Operation(summary = "审核试验报告", description = "审核试验报告")
    @PostMapping("/{id}/review")
    public Result<Boolean> reviewReport(@PathVariable Long id,
                                       @RequestParam Long reviewerId,
                                       @RequestParam boolean approved,
                                       @RequestParam String reviewComments) {
        log.info("审核试验报告请求，ID: {}, 审核人ID: {}, 批准: {}", id, reviewerId, approved);
        try {
            boolean result = experimentReportService.reviewReport(id, reviewerId, approved, reviewComments);
            if (result) {
                String message = approved ? "试验报告审核通过" : "试验报告审核未通过";
                return Result.success(message, true);
            } else {
                return Result.error("试验报告审核失败");
            }
        } catch (Exception e) {
            log.error("审核试验报告失败: {}", e.getMessage(), e);
            return Result.error("审核试验报告失败: " + e.getMessage());
        }
    }

    /**
     * 发布试验报告
     * 
     * @param id 报告ID
     * @return 发布结果
     */
    @Operation(summary = "发布试验报告", description = "发布试验报告")
    @PostMapping("/{id}/publish")
    public Result<Boolean> publishReport(@PathVariable Long id) {
        log.info("发布试验报告请求，ID: {}", id);
        try {
            boolean result = experimentReportService.publishReport(id);
            if (result) {
                return Result.success("试验报告发布成功", true);
            } else {
                return Result.error("试验报告发布失败");
            }
        } catch (Exception e) {
            log.error("发布试验报告失败: {}", e.getMessage(), e);
            return Result.error("发布试验报告失败: " + e.getMessage());
        }
    }

    /**
     * 归档试验报告
     * 
     * @param id 报告ID
     * @return 归档结果
     */
    @Operation(summary = "归档试验报告", description = "归档试验报告")
    @PostMapping("/{id}/archive")
    public Result<Boolean> archiveReport(@PathVariable Long id) {
        log.info("归档试验报告请求，ID: {}", id);
        try {
            boolean result = experimentReportService.archiveReport(id);
            if (result) {
                return Result.success("试验报告归档成功", true);
            } else {
                return Result.error("试验报告归档失败");
            }
        } catch (Exception e) {
            log.error("归档试验报告失败: {}", e.getMessage(), e);
            return Result.error("归档试验报告失败: " + e.getMessage());
        }
    }

}