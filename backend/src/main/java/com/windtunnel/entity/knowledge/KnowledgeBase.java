package com.windtunnel.entity.knowledge;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.windtunnel.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 知识库实体类
 * 
 * 存储风洞试验相关的知识文档信息
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("knowledge_base")
public class KnowledgeBase extends BaseEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 知识文档标题
     */
    @TableField("title")
    private String title;

    /**
     * 知识文档编号
     */
    @TableField("document_code")
    private String documentCode;

    /**
     * 知识文档类型（1-技术文档，2-操作手册，3-安全规范，4-试验标准，5-故障处理，6-其他）
     */
    @TableField("document_type")
    private Integer documentType;

    /**
     * 知识文档分类
     */
    @TableField("category")
    private String category;

    /**
     * 知识文档摘要
     */
    @TableField("summary")
    private String summary;

    /**
     * 知识文档内容
     */
    @TableField("content")
    private String content;

    /**
     * 知识文档状态（0-草稿，1-审核中，2-已发布，3-已归档，4-已删除）
     */
    @TableField("status")
    private Integer status;

    /**
     * 作者ID
     */
    @TableField("author_id")
    private Long authorId;

    /**
     * 作者姓名
     */
    @TableField("author_name")
    private String authorName;

    /**
     * 审核人ID
     */
    @TableField("reviewer_id")
    private Long reviewerId;

    /**
     * 审核人姓名
     */
    @TableField("reviewer_name")
    private String reviewerName;

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
     * 文件大小（字节）
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * 文件类型（如pdf, doc, docx, txt, html等）
     */
    @TableField("file_type")
    private String fileType;

    /**
     * 文件原始名称
     */
    @TableField("original_file_name")
    private String originalFileName;

    /**
     * 知识文档版本号
     */
    @TableField("document_version")
    private String documentVersion;

    /**
     * 版本描述
     */
    @TableField("version_description")
    private String versionDescription;

    /**
     * 关键词（用逗号分隔）
     */
    @TableField("keywords")
    private String keywords;

    /**
     * 访问次数
     */
    @TableField("view_count")
    private Integer viewCount;

    /**
     * 下载次数
     */
    @TableField("download_count")
    private Integer downloadCount;

    /**
     * 点赞数
     */
    @TableField("like_count")
    private Integer likeCount;

    /**
     * 收藏数
     */
    @TableField("favorite_count")
    private Integer favoriteCount;

    /**
     * 评论数
     */
    @TableField("comment_count")
    private Integer commentCount;

    /**
     * 试验项目ID（关联相关试验项目）
     */
    @TableField("experiment_project_id")
    private Long experimentProjectId;

    /**
     * 实验室ID（关联相关实验室）
     */
    @TableField("laboratory_id")
    private Long laboratoryId;

    /**
     * 设备ID（关联相关设备）
     */
    @TableField("equipment_id")
    private Long equipmentId;

    /**
     * 是否置顶（0-否，1-是）
     */
    @TableField("is_top")
    private Integer isTop;

    /**
     * 是否推荐（0-否，1-是）
     */
    @TableField("is_recommended")
    private Integer isRecommended;

    /**
     * 置顶时间
     */
    @TableField("top_time")
    private LocalDateTime topTime;

    /**
     * 推荐时间
     */
    @TableField("recommended_time")
    private LocalDateTime recommendedTime;

    /**
     * 标签（用逗号分隔）
     */
    @TableField("tags")
    private String tags;

    /**
     * 权限级别（0-公开，1-实验室内部，2-部门内部，3-个人）
     */
    @TableField("permission_level")
    private Integer permissionLevel;

    /**
     * 知识文档来源
     */
    @TableField("source")
    private String source;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}