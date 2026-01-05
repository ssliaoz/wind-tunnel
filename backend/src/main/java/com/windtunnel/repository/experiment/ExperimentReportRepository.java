package com.windtunnel.repository.experiment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.windtunnel.entity.experiment.ExperimentReport;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 试验报告数据访问层
 * 
 * 提供试验报告相关的数据库操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Mapper
public interface ExperimentReportRepository extends BaseMapper<ExperimentReport> {

    /**
     * 根据试验项目ID查询报告列表
     * 
     * @param experimentProjectId 试验项目ID
     * @return 报告列表
     */
    List<ExperimentReport> findByExperimentProjectId(Long experimentProjectId);

    /**
     * 根据报告状态查询报告列表
     * 
     * @param reportStatus 报告状态
     * @return 报告列表
     */
    List<ExperimentReport> findByReportStatus(Integer reportStatus);

    /**
     * 根据生成时间范围查询报告列表
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 报告列表
     */
    List<ExperimentReport> findByGenerationTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据生成人ID查询报告列表
     * 
     * @param generatorId 生成人ID
     * @return 报告列表
     */
    List<ExperimentReport> findByGeneratorId(Long generatorId);

    /**
     * 根据审核人ID查询报告列表
     * 
     * @param reviewerId 审核人ID
     * @return 报告列表
     */
    List<ExperimentReport> findByReviewerId(Long reviewerId);

    /**
     * 根据文件类型查询报告列表
     * 
     * @param fileType 文件类型
     * @return 报告列表
     */
    List<ExperimentReport> findByFileType(String fileType);

    /**
     * 根据关键词查询报告列表（模糊查询）
     * 
     * @param keywords 关键词
     * @return 报告列表
     */
    List<ExperimentReport> findByKeywordsContaining(String keywords);

}