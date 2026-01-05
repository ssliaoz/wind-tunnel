package com.windtunnel.entity.experiment;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.windtunnel.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 试验报告实体类
 * 
 * 存储试验报告信息
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("experiment_report")
public class ExperimentReport extends BaseEntity {

    /**
     * 试验项目ID
     */
    @TableField("experiment_project_id")
    private Long experimentProjectId;

    /**
     * 报告标题
     */
    @TableField("report_title")
    private String reportTitle;

    /**
     * 报告内容
     */
    @TableField("report_content")
    private String reportContent;

    /**
     * 报告状态（0-草稿，1-审核中，2-已发布，3-已归档）
     */
    @TableField("report_status")
    private Integer reportStatus;

    /**
     * 生成时间
     */
    @TableField("generation_time")
    private LocalDateTime generationTime;

    /**
     * 生成人ID
     */
    @TableField("generator_id")
    private Long generatorId;

    /**
     * 审核人ID
     */
    @TableField("reviewer_id")
    private Long reviewerId;

    /**
     * 审核时间
     */
    @TableField("review_time")
    private LocalDateTime reviewTime;

    /**
     * 审核意见
     */
    @TableField("review_comments")
    private String reviewComments;

    /**
     * 发布时间
     */
    @TableField("publish_time")
    private LocalDateTime publishTime;

    /**
     * 归档时间
     */
    @TableField("archive_time")
    private LocalDateTime archiveTime;

    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 文件大小
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * 文件类型
     */
    @TableField("file_type")
    private String fileType;

    /**
     * 报告版本号
     */
    @TableField("report_version")
    private String reportVersion;

    /**
     * 摘要
     */
    @TableField("summary")
    private String summary;

    /**
     * 关键词
     */
    @TableField("keywords")
    private String keywords;

    /**
     * 参与人员ID列表（JSON格式）
     */
    @TableField("participant_ids")
    private String participantIds;

    /**
     * 试验数据统计信息（JSON格式）
     */
    @TableField("data_statistics")
    private String dataStatistics;

    /**
     * 结论
     */
    @TableField("conclusion")
    private String conclusion;

    /**
     * 建议
     */
    @TableField("recommendations")
    private String recommendations;

    /**
     * 主键ID
     */
    private Long id;

}