package com.windtunnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.windtunnel.common.Constants;
import com.windtunnel.common.Result;
import com.windtunnel.entity.ExperimentProject;
import com.windtunnel.repository.ExperimentProjectRepository;
import com.windtunnel.service.ExperimentProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 试验项目服务实现类
 * 
 * 实现试验项目相关的业务逻辑处理方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Service
public class ExperimentProjectServiceImpl extends ServiceImpl<ExperimentProjectRepository, ExperimentProject> implements ExperimentProjectService {

    @Autowired
    private ExperimentProjectRepository experimentProjectRepository;

    @Override
    public ExperimentProject findByProjectNo(String projectNo) {
        if (!StringUtils.hasText(projectNo)) {
            return null;
        }
        QueryWrapper<ExperimentProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_no", projectNo);
        queryWrapper.eq("deleted", 0); // 未删除的项目
        return experimentProjectRepository.selectOne(queryWrapper);
    }

    @Override
    public Result<ExperimentProject> createExperimentProject(ExperimentProject experimentProject) {
        log.info("创建试验项目，项目名称: {}", experimentProject.getName());
        
        if (!StringUtils.hasText(experimentProject.getName())) {
            return Result.error("试验项目名称不能为空");
        }

        // 生成项目编号
        String projectNo = generateProjectNo();
        experimentProject.setProjectNo(projectNo);

        // 设置默认状态
        if (experimentProject.getStatus() == null) {
            experimentProject.setStatus(Constants.ExperimentStatus.DRAFT);
        }

        // 设置默认创建时间
        experimentProject.setCreateTime(LocalDateTime.now());

        int result = experimentProjectRepository.insert(experimentProject);
        if (result > 0) {
            log.info("试验项目创建成功，项目ID: {}, 项目编号: {}", experimentProject.getId(), experimentProject.getProjectNo());
            return Result.success("创建成功", experimentProject);
        } else {
            log.error("试验项目创建失败，项目名称: {}", experimentProject.getName());
            return Result.error("创建失败");
        }
    }

    @Override
    public Result<ExperimentProject> updateExperimentProject(ExperimentProject experimentProject) {
        log.info("更新试验项目信息，项目ID: {}", experimentProject.getId());
        
        if (experimentProject.getId() == null) {
            return Result.error("项目ID不能为空");
        }

        // 查询原项目信息
        ExperimentProject existingProject = experimentProjectRepository.selectById(experimentProject.getId());
        if (existingProject == null) {
            return Result.error("试验项目不存在");
        }

        // 更新项目信息
        existingProject.setName(experimentProject.getName());
        existingProject.setExperimentType(experimentProject.getExperimentType());
        existingProject.setPurpose(experimentProject.getPurpose());
        existingProject.setPlanStartTime(experimentProject.getPlanStartTime());
        existingProject.setPlanEndTime(experimentProject.getPlanEndTime());
        existingProject.setManagerId(experimentProject.getManagerId());
        existingProject.setTeamMembers(experimentProject.getTeamMembers());
        existingProject.setOperatingCondition(experimentProject.getOperatingCondition());
        existingProject.setLaboratoryId(experimentProject.getLaboratoryId());
        existingProject.setBudgetAmount(experimentProject.getBudgetAmount());
        existingProject.setRemark(experimentProject.getRemark());

        int result = experimentProjectRepository.updateById(existingProject);
        if (result > 0) {
            log.info("试验项目信息更新成功，项目ID: {}", existingProject.getId());
            return Result.success("更新成功", existingProject);
        } else {
            log.error("试验项目信息更新失败，项目ID: {}", existingProject.getId());
            return Result.error("更新失败");
        }
    }

    @Override
    public Result<Boolean> updateProjectStatus(Long projectId, Integer status) {
        log.info("更新试验项目状态，项目ID: {}, 状态: {}", projectId, status);
        
        if (projectId == null || status == null) {
            return Result.error("项目ID和状态不能为空");
        }

        // 检查状态值是否合法
        if (status < 0 || status > 5) {
            return Result.error("状态值不合法");
        }

        ExperimentProject project = new ExperimentProject();
        project.setId(projectId);
        project.setStatus(status);

        int result = experimentProjectRepository.updateById(project);
        if (result > 0) {
            log.info("试验项目状态更新成功，项目ID: {}", projectId);
            return Result.success("状态更新成功", true);
        } else {
            log.error("试验项目状态更新失败，项目ID: {}", projectId);
            return Result.error("状态更新失败");
        }
    }

    @Override
    public Result<List<ExperimentProject>> findByLaboratoryId(Long laboratoryId) {
        log.info("根据实验室ID查询试验项目列表，实验室ID: {}", laboratoryId);
        
        if (laboratoryId == null) {
            return Result.error("实验室ID不能为空");
        }

        List<ExperimentProject> projects = experimentProjectRepository.findByLaboratoryId(laboratoryId);
        return Result.success("查询成功", projects);
    }

    @Override
    public Result<List<ExperimentProject>> findByManagerId(Long managerId) {
        log.info("根据负责人ID查询试验项目列表，负责人ID: {}", managerId);
        
        if (managerId == null) {
            return Result.error("负责人ID不能为空");
        }

        List<ExperimentProject> projects = experimentProjectRepository.findByManagerId(managerId);
        return Result.success("查询成功", projects);
    }

    @Override
    public Result<List<ExperimentProject>> findByStatus(Integer status) {
        log.info("根据项目状态查询试验项目列表，状态: {}", status);
        
        if (status == null) {
            return Result.error("状态不能为空");
        }

        List<ExperimentProject> projects = experimentProjectRepository.findByStatus(status);
        return Result.success("查询成功", projects);
    }

    @Override
    public Result<List<ExperimentProject>> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        log.info("根据时间范围查询试验项目列表，开始时间: {}, 结束时间: {}", startTime, endTime);
        
        if (startTime == null || endTime == null) {
            return Result.error("开始时间和结束时间不能为空");
        }

        List<ExperimentProject> projects = experimentProjectRepository.findByTimeRange(startTime, endTime);
        return Result.success("查询成功", projects);
    }

    @Override
    public Result<List<ExperimentProject>> findByExperimentType(String experimentType) {
        log.info("根据试验类型查询试验项目列表，试验类型: {}", experimentType);
        
        if (!StringUtils.hasText(experimentType)) {
            return Result.error("试验类型不能为空");
        }

        List<ExperimentProject> projects = experimentProjectRepository.findByExperimentType(experimentType);
        return Result.success("查询成功", projects);
    }

    @Override
    public Result<List<ExperimentProject>> findExperimentProjects(int page, int size) {
        log.info("分页查询试验项目列表，页码: {}, 页面大小: {}", page, size);
        
        if (page < 1) {
            page = 1;
        }
        if (size < 1) {
            size = Constants.DefaultValues.DEFAULT_PAGE_SIZE;
        }

        // 这里简化实现，实际项目中应使用分页插件
        QueryWrapper<ExperimentProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0); // 未删除的项目
        queryWrapper.orderByDesc("create_time");

        List<ExperimentProject> projects = experimentProjectRepository.selectList(queryWrapper);
        return Result.success("查询成功", projects);
    }

    @Override
    public Result<Boolean> deleteExperimentProject(Long projectId) {
        log.info("删除试验项目，项目ID: {}", projectId);
        
        if (projectId == null) {
            return Result.error("项目ID不能为空");
        }

        ExperimentProject project = new ExperimentProject();
        project.setId(projectId);
        project.setDeleted(1); // 逻辑删除

        int result = experimentProjectRepository.updateById(project);
        if (result > 0) {
            log.info("试验项目删除成功，项目ID: {}", projectId);
            return Result.success("删除成功", true);
        } else {
            log.error("试验项目删除失败，项目ID: {}", projectId);
            return Result.error("删除失败");
        }
    }

    @Override
    public Result<Boolean> updateActualCost(Long projectId, java.math.BigDecimal actualCost) {
        log.info("更新试验项目实际花费，项目ID: {}, 实际花费: {}", projectId, actualCost);
        
        if (projectId == null || actualCost == null) {
            return Result.error("项目ID和实际花费不能为空");
        }

        ExperimentProject project = new ExperimentProject();
        project.setId(projectId);
        project.setActualCost(actualCost);

        int result = experimentProjectRepository.updateActualCost(projectId, actualCost);
        if (result > 0) {
            log.info("试验项目实际花费更新成功，项目ID: {}", projectId);
            return Result.success("实际花费更新成功", true);
        } else {
            log.error("试验项目实际花费更新失败，项目ID: {}", projectId);
            return Result.error("实际花费更新失败");
        }
    }

    /**
     * 生成项目编号
     * 
     * @return 项目编号
     */
    private String generateProjectNo() {
        // 生成格式为：EXP-YYYYMMDD-XXXX 的项目编号
        String dateStr = LocalDateTime.now().toString().substring(0, 10).replace("-", "");
        String randomStr = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return "EXP-" + dateStr + "-" + randomStr;
    }

}