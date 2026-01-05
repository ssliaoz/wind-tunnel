package com.windtunnel.service.experiment;

import com.windtunnel.entity.experiment.ExperimentReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 试验报告服务接口
 * 
 * 定义试验报告相关的业务操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
public interface ExperimentReportService {

    /**
     * 保存试验报告
     * 
     * @param report 试验报告实体
     * @return 保存后的试验报告实体
     */
    ExperimentReport save(ExperimentReport report);

    /**
     * 根据ID查询试验报告
     * 
     * @param id 试验报告ID
     * @return 试验报告实体
     */
    ExperimentReport findById(Long id);

    /**
     * 查询所有试验报告
     * 
     * @return 试验报告列表
     */
    List<ExperimentReport> findAll();

    /**
     * 分页查询试验报告
     * 
     * @param pageable 分页参数
     * @return 分页结果
     */
    Page<ExperimentReport> findAll(Pageable pageable);

    /**
     * 根据试验项目ID查询报告
     * 
     * @param experimentProjectId 试验项目ID
     * @return 试验报告列表
     */
    List<ExperimentReport> findByExperimentProjectId(Long experimentProjectId);

    /**
     * 根据报告状态查询报告
     * 
     * @param reportStatus 报告状态
     * @return 试验报告列表
     */
    List<ExperimentReport> findByReportStatus(Integer reportStatus);

    /**
     * 根据生成时间范围查询报告
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 试验报告列表
     */
    List<ExperimentReport> findByGenerationTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据生成人ID查询报告
     * 
     * @param generatorId 生成人ID
     * @return 试验报告列表
     */
    List<ExperimentReport> findByGeneratorId(Long generatorId);

    /**
     * 根据审核人ID查询报告
     * 
     * @param reviewerId 审核人ID
     * @return 试验报告列表
     */
    List<ExperimentReport> findByReviewerId(Long reviewerId);

    /**
     * 根据文件类型查询报告
     * 
     * @param fileType 文件类型
     * @return 试验报告列表
     */
    List<ExperimentReport> findByFileType(String fileType);

    /**
     * 根据关键词查询报告（模糊查询）
     * 
     * @param keywords 关键词
     * @return 试验报告列表
     */
    List<ExperimentReport> findByKeywordsContaining(String keywords);

    /**
     * 删除试验报告
     * 
     * @param id 试验报告ID
     * @return 删除结果
     */
    boolean deleteById(Long id);

    /**
     * 生成试验报告
     * 
     * @param experimentProjectId 试验项目ID
     * @param generatorId 生成人ID
     * @return 生成的报告
     */
    ExperimentReport generateReport(Long experimentProjectId, Long generatorId);

    /**
     * 审核试验报告
     * 
     * @param id 报告ID
     * @param reviewerId 审核人ID
     * @param approved 是否批准
     * @param reviewComments 审核意见
     * @return 审核结果
     */
    boolean reviewReport(Long id, Long reviewerId, boolean approved, String reviewComments);

    /**
     * 发布试验报告
     * 
     * @param id 报告ID
     * @return 发布结果
     */
    boolean publishReport(Long id);

    /**
     * 归档试验报告
     * 
     * @param id 报告ID
     * @return 归档结果
     */
    boolean archiveReport(Long id);

    /**
     * 更新报告状态
     * 
     * @param id 报告ID
     * @param reportStatus 新状态
     * @return 更新结果
     */
    boolean updateReportStatus(Long id, Integer reportStatus);

}