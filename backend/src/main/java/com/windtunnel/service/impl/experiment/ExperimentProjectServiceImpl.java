package com.windtunnel.service.impl.experiment;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.windtunnel.entity.experiment.ExperimentProject;
import com.windtunnel.repository.experiment.ExperimentProjectRepository;
import com.windtunnel.service.experiment.ExperimentProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 试验项目服务实现类
 * 
 * 实现试验项目相关的业务操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Service
public class ExperimentProjectServiceImpl implements ExperimentProjectService {

    @Autowired
    private ExperimentProjectRepository experimentProjectRepository;

    /**
     * 保存试验项目
     * 
     * @param experimentProject 试验项目实体
     * @return 保存后的试验项目实体
     */
    @Override
    public ExperimentProject save(ExperimentProject experimentProject) {
        log.debug("保存试验项目: {}", experimentProject.getProjectName());
        if (experimentProject.getId() == null || experimentProject.getId() <= 0) {
            experimentProjectRepository.insert(experimentProject);
        } else {
            experimentProjectRepository.updateById(experimentProject);
        }
        return experimentProject;
    }

    /**
     * 根据ID查询试验项目
     * 
     * @param id 试验项目ID
     * @return 试验项目实体
     */
    @Override
    public ExperimentProject findById(Long id) {
        log.debug("根据ID查询试验项目: {}", id);
        return experimentProjectRepository.selectById(id);
    }

    /**
     * 查询所有试验项目
     * 
     * @return 试验项目列表
     */
    @Override
    @SuppressWarnings("unchecked")
    public @NonNull List<ExperimentProject> findAll() {
        log.debug("查询所有试验项目");
        List<ExperimentProject> result = experimentProjectRepository.selectList(null);
        // 显式处理返回值以满足@NonNull注解要求
        return result != null ? result.stream().filter(item -> item != null).collect(Collectors.toList()) : Collections.emptyList();
    }

    /**
     * 分页查询试验项目
     * 
     * @param pageable 分页参数
     * @return 分页结果
     */
    @Override
    @SuppressWarnings("unchecked")
    public @NonNull org.springframework.data.domain.Page<ExperimentProject> findAll(Pageable pageable) {
        log.debug("分页查询试验项目");
        Page<ExperimentProject> mpPage = new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        Page<ExperimentProject> resultPage = experimentProjectRepository.selectPage(mpPage, null);
        
        // 转换为Spring Data分页对象
        List<ExperimentProject> records = resultPage != null ? resultPage.getRecords() : Collections.emptyList();
        long total = resultPage != null ? resultPage.getTotal() : 0;
        return new PageImpl<>(
            records != null ? records : Collections.emptyList(),
            pageable,
            total
        );
    }

    /**
     * 根据实验室ID查询试验项目
     * 
     * @param laboratoryId 实验室ID
     * @return 试验项目列表
     */
    @Override
    @SuppressWarnings("unchecked")
    public @NonNull List<ExperimentProject> findByLaboratoryId(Long laboratoryId) {
        log.debug("根据实验室ID查询试验项目: {}", laboratoryId);
        QueryWrapper<ExperimentProject> wrapper = new QueryWrapper<>();
        wrapper.eq("laboratory_id", laboratoryId);
        List<ExperimentProject> result = experimentProjectRepository.selectList(wrapper);
        // 显式处理返回值以满足@NonNull注解要求
        return result != null ? result.stream().filter(item -> item != null).collect(Collectors.toList()) : Collections.emptyList();
    }

    /**
     * 根据试验负责人ID查询试验项目
     * 
     * @param projectLeaderId 试验负责人ID
     * @return 试验项目列表
     */
    @Override
    @SuppressWarnings("unchecked")
    public @NonNull List<ExperimentProject> findByProjectLeaderId(Long projectLeaderId) {
        log.debug("根据试验负责人ID查询试验项目: {}", projectLeaderId);
        QueryWrapper<ExperimentProject> wrapper = new QueryWrapper<>();
        wrapper.eq("project_leader_id", projectLeaderId);
        List<ExperimentProject> result = experimentProjectRepository.selectList(wrapper);
        // 显式处理返回值以满足@NonNull注解要求
        return result != null ? result.stream().filter(item -> item != null).collect(Collectors.toList()) : Collections.emptyList();
    }

    /**
     * 根据试验状态查询试验项目
     * 
     * @param status 试验状态
     * @return 试验项目列表
     */
    @Override
    @SuppressWarnings("unchecked")
    public @NonNull List<ExperimentProject> findByStatus(Integer status) {
        log.debug("根据试验状态查询试验项目: {}", status);
        QueryWrapper<ExperimentProject> wrapper = new QueryWrapper<>();
        wrapper.eq("status", status);
        List<ExperimentProject> result = experimentProjectRepository.selectList(wrapper);
        // 显式处理返回值以满足@NonNull注解要求
        return result != null ? result.stream().filter(item -> item != null).collect(Collectors.toList()) : Collections.emptyList();
    }

    /**
     * 根据时间范围查询试验项目
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 试验项目列表
     */
    @Override
    @SuppressWarnings("unchecked")
    public @NonNull List<ExperimentProject> findByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        log.debug("根据时间范围查询试验项目: {} - {}", startTime, endTime);
        QueryWrapper<ExperimentProject> wrapper = new QueryWrapper<>();
        wrapper.between("start_time", startTime, endTime);
        List<ExperimentProject> result = experimentProjectRepository.selectList(wrapper);
        // 显式处理返回值以满足@NonNull注解要求
        return result != null ? result.stream().filter(item -> item != null).collect(Collectors.toList()) : Collections.emptyList();
    }

    /**
     * 根据项目类型查询试验项目
     * 
     * @param projectType 项目类型
     * @return 试验项目列表
     */
    @Override
    @SuppressWarnings("unchecked")
    public @NonNull List<ExperimentProject> findByProjectType(String projectType) {
        log.debug("根据项目类型查询试验项目: {}", projectType);
        QueryWrapper<ExperimentProject> wrapper = new QueryWrapper<>();
        wrapper.eq("project_type", projectType);
        List<ExperimentProject> result = experimentProjectRepository.selectList(wrapper);
        // 显式处理返回值以满足@NonNull注解要求
        return result != null ? result.stream().filter(item -> item != null).collect(Collectors.toList()) : Collections.emptyList();
    }

    /**
     * 根据成本管控状态查询试验项目
     * 
     * @param costControlStatus 成本管控状态
     * @return 试验项目列表
     */
    @Override
    @SuppressWarnings("unchecked")
    public @NonNull List<ExperimentProject> findByCostControlStatus(Integer costControlStatus) {
        log.debug("根据成本管控状态查询试验项目: {}", costControlStatus);
        QueryWrapper<ExperimentProject> wrapper = new QueryWrapper<>();
        wrapper.eq("cost_control_status", costControlStatus);
        List<ExperimentProject> result = experimentProjectRepository.selectList(wrapper);
        // 显式处理返回值以满足@NonNull注解要求
        return result != null ? result.stream().filter(item -> item != null).collect(Collectors.toList()) : Collections.emptyList();
    }
    /**
     * 删除试验项目
     * 
     * @param id 试验项目ID
     * @return 删除结果
     */
    @Override
    public boolean deleteById(Long id) {
        log.debug("删除试验项目: {}", id);
        return experimentProjectRepository.deleteById(id) > 0;
    }

    /**
     * 提交试验项目审批
     * 
     * @param id 试验项目ID
     * @return 审批提交结果
     */
    @Override
    public boolean submitForApproval(Long id) {
        log.info("提交试验项目审批: {}", id);
        ExperimentProject project = findById(id);
        if (project != null) {
            project.setStatus(0); // 设置为待审批状态
            project.setApplicationTime(LocalDateTime.now());
            return experimentProjectRepository.updateById(project) > 0;
        }
        return false;
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
    @Override
    public boolean approve(Long id, boolean approved, String approvalComments, Long approverId) {
        log.info("审批试验项目: {}, 批准: {}", id, approved);
        ExperimentProject project = findById(id);
        if (project != null) {
            project.setStatus(approved ? 1 : 0); // 1-已批准，0-未批准
            project.setApprovalTime(LocalDateTime.now());
            project.setApproverId(approverId);
            project.setApprovalComments(approvalComments);
            return experimentProjectRepository.updateById(project) > 0;
        }
        return false;
    }

    /**
     * 更新试验项目状态
     * 
     * @param id 试验项目ID
     * @param status 新状态
     * @return 更新结果
     */
    @Override
    public boolean updateStatus(Long id, Integer status) {
        log.info("更新试验项目状态: {}, 状态: {}", id, status);
        ExperimentProject project = findById(id);
        if (project != null) {
            project.setStatus(status);
            project.setUpdateTime(LocalDateTime.now());
            return experimentProjectRepository.updateById(project) > 0;
        }
        return false;
    }

    /**
     * 更新成本信息
     * 
     * @param id 试验项目ID
     * @param actualCost 实际花费
     * @return 更新结果
     */
    @Override
    public boolean updateCost(Long id, java.math.BigDecimal actualCost) {
        log.info("更新试验项目成本: {}, 成本: {}", id, actualCost);
        ExperimentProject project = findById(id);
        if (project != null) {
            project.setActualCost(actualCost);
            project.setUpdateTime(LocalDateTime.now());
            
            // 更新成本管控状态
            if (project.getBudgetAmount() != null && actualCost.compareTo(project.getBudgetAmount()) > 0) {
                project.setCostControlStatus(2); // 超标
            } else if (project.getBudgetAmount() != null && 
                      actualCost.compareTo(project.getBudgetAmount().multiply(new java.math.BigDecimal("0.9"))) > 0) {
                project.setCostControlStatus(1); // 预警
            } else {
                project.setCostControlStatus(0); // 正常
            }
            
            return experimentProjectRepository.updateById(project) > 0;
        }
        return false;
    }

}