package com.windtunnel.service.experiment;

import com.windtunnel.entity.experiment.ExperimentProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 试验项目服务接口
 * 
 * 定义试验项目相关的业务操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
public interface ExperimentProjectService {

    /**
     * 保存试验项目
     * 
     * @param experimentProject 试验项目实体
     * @return 保存后的试验项目实体
     */
    ExperimentProject save(ExperimentProject experimentProject);

    /**
     * 根据ID查询试验项目
     * 
     * @param id 试验项目ID
     * @return 试验项目实体
     */
    ExperimentProject findById(Long id);

    /**
     * 查询所有试验项目
     * 
     * @return 试验项目列表
     */
    List<ExperimentProject> findAll();

    /**
     * 分页查询试验项目
     * 
     * @param pageable 分页参数
     * @return 分页结果
     */
    Page<ExperimentProject> findAll(Pageable pageable);

    /**
     * 根据实验室ID查询试验项目
     * 
     * @param laboratoryId 实验室ID
     * @return 试验项目列表
     */
    List<ExperimentProject> findByLaboratoryId(Long laboratoryId);

    /**
     * 根据试验负责人ID查询试验项目
     * 
     * @param projectLeaderId 试验负责人ID
     * @return 试验项目列表
     */
    List<ExperimentProject> findByProjectLeaderId(Long projectLeaderId);

    /**
     * 根据试验状态查询试验项目
     * 
     * @param status 试验状态
     * @return 试验项目列表
     */
    List<ExperimentProject> findByStatus(Integer status);

    /**
     * 根据时间范围查询试验项目
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 试验项目列表
     */
    List<ExperimentProject> findByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据项目类型查询试验项目
     * 
     * @param projectType 项目类型
     * @return 试验项目列表
     */
    List<ExperimentProject> findByProjectType(String projectType);

    /**
     * 根据成本管控状态查询试验项目
     * 
     * @param costControlStatus 成本管控状态
     * @return 试验项目列表
     */
    List<ExperimentProject> findByCostControlStatus(Integer costControlStatus);

    /**
     * 删除试验项目
     * 
     * @param id 试验项目ID
     * @return 删除结果
     */
    boolean deleteById(Long id);

    /**
     * 提交试验项目审批
     * 
     * @param id 试验项目ID
     * @return 审批提交结果
     */
    boolean submitForApproval(Long id);

    /**
     * 审批试验项目
     * 
     * @param id 试验项目ID
     * @param approved 是否批准
     * @param approvalComments 审批意见
     * @param approverId 审批人ID
     * @return 审批结果
     */
    boolean approve(Long id, boolean approved, String approvalComments, Long approverId);

    /**
     * 更新试验项目状态
     * 
     * @param id 试验项目ID
     * @param status 新状态
     * @return 更新结果
     */
    boolean updateStatus(Long id, Integer status);

    /**
     * 更新成本信息
     * 
     * @param id 试验项目ID
     * @param actualCost 实际花费
     * @return 更新结果
     */
    boolean updateCost(Long id, java.math.BigDecimal actualCost);

}