package com.windtunnel.controller;

import com.windtunnel.common.Result;
import com.windtunnel.entity.ExperimentProject;
import com.windtunnel.service.ExperimentProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
@RequestMapping("/api/experiments")
public class ExperimentProjectController {

    @Autowired
    private ExperimentProjectService experimentProjectService;

    /**
     * 创建试验项目
     * 
     * @param experimentProject 试验项目实体
     * @return 创建结果
     */
    @PostMapping
    public Result<ExperimentProject> createExperimentProject(@RequestBody ExperimentProject experimentProject) {
        log.info("创建试验项目请求，项目名称: {}", experimentProject.getName());
        return experimentProjectService.createExperimentProject(experimentProject);
    }

    /**
     * 根据ID获取试验项目信息
     * 
     * @param id 项目ID
     * @return 试验项目信息
     */
    @GetMapping("/{id}")
    public Result<ExperimentProject> getExperimentProjectById(@PathVariable Long id) {
        log.info("获取试验项目信息请求，项目ID: {}", id);
        ExperimentProject project = experimentProjectService.getById(id);
        if (project != null && project.getDeleted() == 0) {
            return Result.success("查询成功", project);
        } else {
            return Result.error("试验项目不存在");
        }
    }

    /**
     * 更新试验项目信息
     * 
     * @param experimentProject 试验项目实体
     * @return 更新结果
     */
    @PutMapping
    public Result<ExperimentProject> updateExperimentProject(@RequestBody ExperimentProject experimentProject) {
        log.info("更新试验项目信息请求，项目ID: {}", experimentProject.getId());
        return experimentProjectService.updateExperimentProject(experimentProject);
    }

    /**
     * 更新试验项目状态
     * 
     * @param projectId 项目ID
     * @param status 状态
     * @return 更新结果
     */
    @PutMapping("/status")
    public Result<Boolean> updateProjectStatus(@RequestParam Long projectId, @RequestParam Integer status) {
        log.info("更新试验项目状态请求，项目ID: {}, 状态: {}", projectId, status);
        return experimentProjectService.updateProjectStatus(projectId, status);
    }

    /**
     * 根据实验室ID查询试验项目列表
     * 
     * @param laboratoryId 实验室ID
     * @return 试验项目列表
     */
    @GetMapping("/laboratory/{laboratoryId}")
    public Result<List<ExperimentProject>> getProjectsByLaboratoryId(@PathVariable Long laboratoryId) {
        log.info("根据实验室ID查询试验项目列表请求，实验室ID: {}", laboratoryId);
        return experimentProjectService.findByLaboratoryId(laboratoryId);
    }

    /**
     * 根据负责人ID查询试验项目列表
     * 
     * @param managerId 负责人ID
     * @return 试验项目列表
     */
    @GetMapping("/manager/{managerId}")
    public Result<List<ExperimentProject>> getProjectsByManagerId(@PathVariable Long managerId) {
        log.info("根据负责人ID查询试验项目列表请求，负责人ID: {}", managerId);
        return experimentProjectService.findByManagerId(managerId);
    }

    /**
     * 根据项目状态查询试验项目列表
     * 
     * @param status 项目状态
     * @return 试验项目列表
     */
    @GetMapping("/status/{status}")
    public Result<List<ExperimentProject>> getProjectsByStatus(@PathVariable Integer status) {
        log.info("根据项目状态查询试验项目列表请求，状态: {}", status);
        return experimentProjectService.findByStatus(status);
    }

    /**
     * 根据时间范围查询试验项目列表
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 试验项目列表
     */
    @GetMapping("/time-range")
    public Result<List<ExperimentProject>> getProjectsByTimeRange(@RequestParam LocalDateTime startTime, 
                                                                @RequestParam LocalDateTime endTime) {
        log.info("根据时间范围查询试验项目列表请求，开始时间: {}, 结束时间: {}", startTime, endTime);
        return experimentProjectService.findByTimeRange(startTime, endTime);
    }

    /**
     * 根据试验类型查询试验项目列表
     * 
     * @param experimentType 试验类型
     * @return 试验项目列表
     */
    @GetMapping("/type/{experimentType}")
    public Result<List<ExperimentProject>> getProjectsByExperimentType(@PathVariable String experimentType) {
        log.info("根据试验类型查询试验项目列表请求，试验类型: {}", experimentType);
        return experimentProjectService.findByExperimentType(experimentType);
    }

    /**
     * 分页查询试验项目列表
     * 
     * @param page 页码
     * @param size 页面大小
     * @return 试验项目列表
     */
    @GetMapping
    public Result<List<ExperimentProject>> getExperimentProjects(@RequestParam(defaultValue = "1") int page, 
                                                               @RequestParam(defaultValue = "10") int size) {
        log.info("分页查询试验项目列表请求，页码: {}, 页面大小: {}", page, size);
        return experimentProjectService.findExperimentProjects(page, size);
    }

    /**
     * 删除试验项目
     * 
     * @param projectId 项目ID
     * @return 删除结果
     */
    @DeleteMapping("/{projectId}")
    public Result<Boolean> deleteExperimentProject(@PathVariable Long projectId) {
        log.info("删除试验项目请求，项目ID: {}", projectId);
        return experimentProjectService.deleteExperimentProject(projectId);
    }

    /**
     * 更新试验项目的实际花费
     * 
     * @param projectId 项目ID
     * @param actualCost 实际花费
     * @return 更新结果
     */
    @PutMapping("/actual-cost")
    public Result<Boolean> updateActualCost(@RequestParam Long projectId, @RequestParam java.math.BigDecimal actualCost) {
        log.info("更新试验项目实际花费请求，项目ID: {}, 实际花费: {}", projectId, actualCost);
        return experimentProjectService.updateActualCost(projectId, actualCost);
    }

}