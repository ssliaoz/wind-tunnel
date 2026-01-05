package com.windtunnel.service.impl.experiment;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.windtunnel.entity.experiment.ExperimentReport;
import com.windtunnel.repository.experiment.ExperimentReportRepository;
import com.windtunnel.service.experiment.ExperimentReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 试验报告服务实现类
 * 
 * 实现试验报告相关的业务操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Service
public class ExperimentReportServiceImpl implements ExperimentReportService {

    @Autowired
    private ExperimentReportRepository experimentReportRepository;

    /**
     * 保存试验报告
     * 
     * @param report 试验报告实体
     * @return 保存后的试验报告实体
     */
    @Override
    public ExperimentReport save(ExperimentReport report) {
        log.debug("保存试验报告: {}", report.getReportTitle());
        if (report.getId() == null || report.getId() <= 0) {
            experimentReportRepository.insert(report);
        } else {
            experimentReportRepository.updateById(report);
        }
        return report;
    }

    /**
     * 根据ID查询试验报告
     * 
     * @param id 试验报告ID
     * @return 试验报告实体
     */
    @Override
    public ExperimentReport findById(Long id) {
        log.debug("根据ID查询试验报告: {}", id);
        return experimentReportRepository.selectById(id);
    }

    /**
     * 查询所有试验报告
     * 
     * @return 试验报告列表
     */
    @Override
    public List<ExperimentReport> findAll() {
        log.debug("查询所有试验报告");
        return experimentReportRepository.selectList(null);
    }

    /**
     * 分页查询试验报告
     * 
     * @param pageable 分页参数
     * @return 分页结果
     */
    @Override
    public org.springframework.data.domain.Page<ExperimentReport> findAll(Pageable pageable) {
        log.debug("分页查询试验报告");
        Page<ExperimentReport> mpPage = new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        Page<ExperimentReport> resultPage = experimentReportRepository.selectPage(mpPage, null);
        
        // 转换为Spring Data分页对象
        return new PageImpl<>(
            resultPage.getRecords(),
            pageable,
            resultPage.getTotal()
        );
    }

    /**
     * 根据试验项目ID查询报告
     * 
     * @param experimentProjectId 试验项目ID
     * @return 试验报告列表
     */
    @Override
    public List<ExperimentReport> findByExperimentProjectId(Long experimentProjectId) {
        log.debug("根据试验项目ID查询报告: {}", experimentProjectId);
        QueryWrapper<ExperimentReport> wrapper = new QueryWrapper<>();
        wrapper.eq("experiment_project_id", experimentProjectId);
        return experimentReportRepository.selectList(wrapper);
    }

    /**
     * 根据报告状态查询报告
     * 
     * @param reportStatus 报告状态
     * @return 试验报告列表
     */
    @Override
    public List<ExperimentReport> findByReportStatus(Integer reportStatus) {
        log.debug("根据报告状态查询报告: {}", reportStatus);
        QueryWrapper<ExperimentReport> wrapper = new QueryWrapper<>();
        wrapper.eq("report_status", reportStatus);
        return experimentReportRepository.selectList(wrapper);
    }

    /**
     * 根据生成时间范围查询报告
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 试验报告列表
     */
    @Override
    public List<ExperimentReport> findByGenerationTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        log.debug("根据生成时间范围查询报告: {} - {}", startTime, endTime);
        QueryWrapper<ExperimentReport> wrapper = new QueryWrapper<>();
        wrapper.between("generation_time", startTime, endTime);
        return experimentReportRepository.selectList(wrapper);
    }

    /**
     * 根据生成人ID查询报告
     * 
     * @param generatorId 生成人ID
     * @return 试验报告列表
     */
    @Override
    public List<ExperimentReport> findByGeneratorId(Long generatorId) {
        log.debug("根据生成人ID查询报告: {}", generatorId);
        QueryWrapper<ExperimentReport> wrapper = new QueryWrapper<>();
        wrapper.eq("generator_id", generatorId);
        return experimentReportRepository.selectList(wrapper);
    }

    /**
     * 根据审核人ID查询报告
     * 
     * @param reviewerId 审核人ID
     * @return 试验报告列表
     */
    @Override
    public List<ExperimentReport> findByReviewerId(Long reviewerId) {
        log.debug("根据审核人ID查询报告: {}", reviewerId);
        QueryWrapper<ExperimentReport> wrapper = new QueryWrapper<>();
        wrapper.eq("reviewer_id", reviewerId);
        return experimentReportRepository.selectList(wrapper);
    }

    /**
     * 根据文件类型查询报告
     * 
     * @param fileType 文件类型
     * @return 试验报告列表
     */
    @Override
    public List<ExperimentReport> findByFileType(String fileType) {
        log.debug("根据文件类型查询报告: {}", fileType);
        QueryWrapper<ExperimentReport> wrapper = new QueryWrapper<>();
        wrapper.eq("file_type", fileType);
        return experimentReportRepository.selectList(wrapper);
    }

    /**
     * 根据关键词查询报告（模糊查询）
     * 
     * @param keywords 关键词
     * @return 试验报告列表
     */
    @Override
    public List<ExperimentReport> findByKeywordsContaining(String keywords) {
        log.debug("根据关键词查询报告: {}", keywords);
        QueryWrapper<ExperimentReport> wrapper = new QueryWrapper<>();
        wrapper.like("keywords", keywords);
        return experimentReportRepository.selectList(wrapper);
    }

    /**
     * 删除试验报告
     * 
     * @param id 试验报告ID
     * @return 删除结果
     */
    @Override
    public boolean deleteById(Long id) {
        log.debug("删除试验报告: {}", id);
        return experimentReportRepository.deleteById(id) > 0;
    }

    /**
     * 生成试验报告
     * 
     * @param experimentProjectId 试验项目ID
     * @param generatorId 生成人ID
     * @return 生成的报告
     */
    @Override
    public ExperimentReport generateReport(Long experimentProjectId, Long generatorId) {
        log.info("生成试验报告，试验项目ID: {}, 生成人ID: {}", experimentProjectId, generatorId);
        ExperimentReport report = new ExperimentReport();
        report.setExperimentProjectId(experimentProjectId);
        report.setGeneratorId(generatorId);
        report.setReportStatus(0); // 0-草稿
        report.setGenerationTime(LocalDateTime.now());
        return save(report);
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
    @Override
    public boolean reviewReport(Long id, Long reviewerId, boolean approved, String reviewComments) {
        log.info("审核试验报告: {}, 审核人: {}, 批准: {}", id, reviewerId, approved);
        ExperimentReport report = findById(id);
        if (report != null) {
            report.setReviewerId(reviewerId);
            report.setReviewComments(reviewComments);
            report.setReviewTime(LocalDateTime.now());
            report.setReportStatus(approved ? 2 : 1); // 2-已发布，1-审核中（这里改为已拒绝）
            return experimentReportRepository.updateById(report) > 0;
        }
        return false;
    }

    /**
     * 发布试验报告
     * 
     * @param id 报告ID
     * @return 发布结果
     */
    @Override
    public boolean publishReport(Long id) {
        log.info("发布试验报告: {}", id);
        ExperimentReport report = findById(id);
        if (report != null) {
            report.setReportStatus(2); // 2-已发布
            report.setPublishTime(LocalDateTime.now());
            return experimentReportRepository.updateById(report) > 0;
        }
        return false;
    }

    /**
     * 归档试验报告
     * 
     * @param id 报告ID
     * @return 归档结果
     */
    @Override
    public boolean archiveReport(Long id) {
        log.info("归档试验报告: {}", id);
        ExperimentReport report = findById(id);
        if (report != null) {
            report.setReportStatus(3); // 3-已归档
            report.setArchiveTime(LocalDateTime.now());
            return experimentReportRepository.updateById(report) > 0;
        }
        return false;
    }

    /**
     * 更新报告状态
     * 
     * @param id 报告ID
     * @param reportStatus 新状态
     * @return 更新结果
     */
    @Override
    public boolean updateReportStatus(Long id, Integer reportStatus) {
        log.info("更新报告状态: {}, 状态: {}", id, reportStatus);
        ExperimentReport report = findById(id);
        if (report != null) {
            report.setReportStatus(reportStatus);
            report.setUpdateTime(LocalDateTime.now());
            return experimentReportRepository.updateById(report) > 0;
        }
        return false;
    }

}