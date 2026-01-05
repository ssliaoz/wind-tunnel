package com.windtunnel.controller.experiment;

import com.windtunnel.common.Result;
import com.windtunnel.entity.experiment.ExperimentProject;
import com.windtunnel.service.experiment.ExperimentProjectService;
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
 * 试验项目控制器
 * 
 * 提供试验项目相关的REST API接口
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/api/experiments/projects")
@Tag(name = "试验项目管理", description = "试验项目相关的API接口")
public class ExperimentProjectController {

    @Autowired
    private ExperimentProjectService experimentProjectService;

    /**
     * 创建试验项目
     * 
     * @param experimentProject 试验项目实体
     * @return 创建结果
     */
    @Operation(summary = "创建试验项目", description = "创建新的试验项目")
    @PostMapping
    public Result<ExperimentProject> createExperimentProject(@RequestBody ExperimentProject experimentProject) {
        log.info("创建试验项目请求: {}", experimentProject.getProjectName());
        try {
            ExperimentProject savedProject = experimentProjectService.save(experimentProject);
            return Result.success("试验项目创建成功", savedProject);
        } catch (Exception e) {
            log.error("创建试验项目失败: {}", e.getMessage(), e);
            return Result.error("创建试验项目失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取试验项目
     * 
     * @param id 试验项目ID
     * @return 试验项目信息
     */
    @Operation(summary = "根据ID获取试验项目", description = "根据ID获取试验项目的详细信息")
    @GetMapping("/{id}")
    public Result<ExperimentProject> getExperimentProjectById(@PathVariable Long id) {
        log.info("获取试验项目请求，ID: {}", id);
        try {
            ExperimentProject project = experimentProjectService.findById(id);
            if (project != null) {
                return Result.success("查询成功", project);
            } else {
                return Result.error("试验项目不存在");
            }
        } catch (Exception e) {
            log.error("获取试验项目失败: {}", e.getMessage(), e);
            return Result.error("获取试验项目失败: " + e.getMessage());
        }
    }

    /**
     * 分页查询试验项目
     * 
     * @param page 页码
     * @param size 页面大小
     * @param sortBy 排序字段
     * @param direction 排序方向
     * @return 试验项目分页结果
     */
    @Operation(summary = "分页查询试验项目", description = "分页获取试验项目列表")
    @GetMapping
    public Result<Page<ExperimentProject>> getExperimentProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createTime") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {
        log.info("分页查询试验项目请求，页码: {}, 大小: {}", page, size);
        try {
            Sort sort = direction.equalsIgnoreCase("ASC") ? 
                Sort.by(Sort.Direction.ASC, sortBy) : 
                Sort.by(Sort.Direction.DESC, sortBy);
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<ExperimentProject> projectsPage = experimentProjectService.findAll(pageable);
            return Result.success("查询成功", projectsPage);
        } catch (Exception e) {
            log.error("分页查询试验项目失败: {}", e.getMessage(), e);
            return Result.error("分页查询试验项目失败: " + e.getMessage());
        }
    }

    /**
     * 根据实验室ID查询试验项目
     * 
     * @param laboratoryId 实验室ID
     * @return 试验项目列表
     */
    @Operation(summary = "根据实验室查询试验项目", description = "根据实验室ID获取该实验室下的所有试验项目")
    @GetMapping("/laboratory/{laboratoryId}")
    public Result<List<ExperimentProject>> getExperimentProjectsByLaboratoryId(@PathVariable Long laboratoryId) {
        log.info("根据实验室ID查询试验项目请求，实验室ID: {}", laboratoryId);
        try {
            List<ExperimentProject> projects = experimentProjectService.findByLaboratoryId(laboratoryId);
            return Result.success("查询成功", projects);
        } catch (Exception e) {
            log.error("查询试验项目失败: {}", e.getMessage(), e);
            return Result.error("查询试验项目失败: " + e.getMessage());
        }
    }

    /**
     * 根据试验负责人ID查询试验项目
     * 
     * @param projectLeaderId 试验负责人ID
     * @return 试验项目列表
     */
    @Operation(summary = "根据负责人查询试验项目", description = "根据试验负责人ID获取其负责的所有试验项目")
    @GetMapping("/leader/{projectLeaderId}")
    public Result<List<ExperimentProject>> getExperimentProjectsByLeaderId(@PathVariable Long projectLeaderId) {
        log.info("根据负责人ID查询试验项目请求，负责人ID: {}", projectLeaderId);
        try {
            List<ExperimentProject> projects = experimentProjectService.findByProjectLeaderId(projectLeaderId);
            return Result.success("查询成功", projects);
        } catch (Exception e) {
            log.error("查询试验项目失败: {}", e.getMessage(), e);
            return Result.error("查询试验项目失败: " + e.getMessage());
        }
    }

    /**
     * 根据状态查询试验项目
     * 
     * @param status 试验状态
     * @return 试验项目列表
     */
    @Operation(summary = "根据状态查询试验项目", description = "根据试验状态获取试验项目列表")
    @GetMapping("/status/{status}")
    public Result<List<ExperimentProject>> getExperimentProjectsByStatus(@PathVariable Integer status) {
        log.info("根据状态查询试验项目请求，状态: {}", status);
        try {
            List<ExperimentProject> projects = experimentProjectService.findByStatus(status);
            return Result.success("查询成功", projects);
        } catch (Exception e) {
            log.error("查询试验项目失败: {}", e.getMessage(), e);
            return Result.error("查询试验项目失败: " + e.getMessage());
        }
    }

    /**
     * 更新试验项目
     * 
     * @param id 试验项目ID
     * @param experimentProject 试验项目实体
     * @return 更新结果
     */
    @Operation(summary = "更新试验项目", description = "更新试验项目的基本信息")
    @PutMapping("/{id}")
    public Result<ExperimentProject> updateExperimentProject(@PathVariable Long id, 
                                                            @RequestBody ExperimentProject experimentProject) {
        log.info("更新试验项目请求，ID: {}", id);
        try {
            experimentProject.setId(id);
            ExperimentProject updatedProject = experimentProjectService.save(experimentProject);
            return Result.success("试验项目更新成功", updatedProject);
        } catch (Exception e) {
            log.error("更新试验项目失败: {}", e.getMessage(), e);
            return Result.error("更新试验项目失败: " + e.getMessage());
        }
    }

    /**
     * 删除试验项目
     * 
     * @param id 试验项目ID
     * @return 删除结果
     */
    @Operation(summary = "删除试验项目", description = "根据ID删除试验项目（逻辑删除）")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteExperimentProject(@PathVariable Long id) {
        log.info("删除试验项目请求，ID: {}", id);
        try {
            boolean result = experimentProjectService.deleteById(id);
            if (result) {
                return Result.success("试验项目删除成功", true);
            } else {
                return Result.error("试验项目删除失败");
            }
        } catch (Exception e) {
            log.error("删除试验项目失败: {}", e.getMessage(), e);
            return Result.error("删除试验项目失败: " + e.getMessage());
        }
    }

    /**
     * 提交试验项目审批
     * 
     * @param id 试验项目ID
     * @return 审批提交结果
     */
    @Operation(summary = "提交审批", description = "提交试验项目进行审批")
    @PostMapping("/{id}/submit-approval")
    public Result<Boolean> submitApproval(@PathVariable Long id) {
        log.info("提交试验项目审批请求，ID: {}", id);
        try {
            boolean result = experimentProjectService.submitForApproval(id);
            if (result) {
                return Result.success("审批提交成功", true);
            } else {
                return Result.error("审批提交失败");
            }
        } catch (Exception e) {
            log.error("提交审批失败: {}", e.getMessage(), e);
            return Result.error("提交审批失败: " + e.getMessage());
        }
    }

    /**
     * 审批试验项目
     * 
     * @param id 试验项目ID
     * @param approved 是否批准
     * @param approvalComments 审批意见
     * @param approverId 审批人ID
     * @return 审批结果
     */
    @Operation(summary = "审批试验项目", description = "审批试验项目")
    @PostMapping("/{id}/approve")
    public Result<Boolean> approveExperimentProject(@PathVariable Long id,
                                                   @RequestParam boolean approved,
                                                   @RequestParam String approvalComments,
                                                   @RequestParam Long approverId) {
        log.info("审批试验项目请求，ID: {}, 批准: {}", id, approved);
        try {
            boolean result = experimentProjectService.approve(id, approved, approvalComments, approverId);
            if (result) {
                String message = approved ? "试验项目审批通过" : "试验项目审批未通过";
                return Result.success(message, true);
            } else {
                return Result.error("审批失败");
            }
        } catch (Exception e) {
            log.error("审批试验项目失败: {}", e.getMessage(), e);
            return Result.error("审批试验项目失败: " + e.getMessage());
        }
    }

}