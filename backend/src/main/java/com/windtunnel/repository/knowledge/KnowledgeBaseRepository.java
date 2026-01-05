package com.windtunnel.repository.knowledge;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.windtunnel.entity.knowledge.KnowledgeBase;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 知识库数据访问层
 * 
 * 提供知识库相关的数据库操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Mapper
public interface KnowledgeBaseRepository extends BaseMapper<KnowledgeBase> {

    /**
     * 根据知识文档类型查询知识库列表
     * 
     * @param documentType 知识文档类型
     * @return 知识库列表
     */
    List<KnowledgeBase> findByDocumentType(Integer documentType);

    /**
     * 根据知识文档状态查询知识库列表
     * 
     * @param status 知识文档状态
     * @return 知识库列表
     */
    List<KnowledgeBase> findByStatus(Integer status);

    /**
     * 根据分类查询知识库列表
     * 
     * @param category 知识文档分类
     * @return 知识库列表
     */
    List<KnowledgeBase> findByCategory(String category);

    /**
     * 根据作者ID查询知识库列表
     * 
     * @param authorId 作者ID
     * @return 知识库列表
     */
    List<KnowledgeBase> findByAuthorId(Long authorId);

    /**
     * 根据实验室ID查询知识库列表
     * 
     * @param laboratoryId 实验室ID
     * @return 知识库列表
     */
    List<KnowledgeBase> findByLaboratoryId(Long laboratoryId);

    /**
     * 根据试验项目ID查询知识库列表
     * 
     * @param experimentProjectId 试验项目ID
     * @return 知识库列表
     */
    List<KnowledgeBase> findByExperimentProjectId(Long experimentProjectId);

    /**
     * 根据设备ID查询知识库列表
     * 
     * @param equipmentId 设备ID
     * @return 知识库列表
     */
    List<KnowledgeBase> findByEquipmentId(Long equipmentId);

    /**
     * 根据关键词查询知识库列表（模糊查询）
     * 
     * @param keywords 关键词
     * @return 知识库列表
     */
    List<KnowledgeBase> findByKeywordsContaining(String keywords);

    /**
     * 根据标签查询知识库列表（模糊查询）
     * 
     * @param tags 标签
     * @return 知识库列表
     */
    List<KnowledgeBase> findByTagsContaining(String tags);

    /**
     * 根据时间范围查询知识库列表
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 知识库列表
     */
    List<KnowledgeBase> findByCreateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据是否置顶查询知识库列表
     * 
     * @param isTop 是否置顶
     * @return 知识库列表
     */
    List<KnowledgeBase> findByIsTop(Integer isTop);

    /**
     * 根据是否推荐查询知识库列表
     * 
     * @param isRecommended 是否推荐
     * @return 知识库列表
     */
    List<KnowledgeBase> findByIsRecommended(Integer isRecommended);

    /**
     * 根据权限级别查询知识库列表
     * 
     * @param permissionLevel 权限级别
     * @return 知识库列表
     */
    List<KnowledgeBase> findByPermissionLevel(Integer permissionLevel);

    /**
     * 根据标题查询知识库列表（模糊查询）
     * 
     * @param title 标题
     * @return 知识库列表
     */
    List<KnowledgeBase> findByTitleContaining(String title);

}