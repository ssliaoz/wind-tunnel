package com.windtunnel.service.knowledge;

import com.windtunnel.entity.knowledge.KnowledgeBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 知识库服务接口
 * 
 * 定义知识库相关的业务操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
public interface KnowledgeBaseService {

    /**
     * 保存知识文档
     * 
     * @param knowledgeBase 知识文档实体
     * @return 保存后的知识文档实体
     */
    KnowledgeBase save(KnowledgeBase knowledgeBase);

    /**
     * 根据ID查询知识文档
     * 
     * @param id 知识文档ID
     * @return 知识文档实体
     */
    KnowledgeBase findById(Long id);

    /**
     * 查询所有知识文档
     * 
     * @return 知识文档列表
     */
    List<KnowledgeBase> findAll();

    /**
     * 分页查询知识文档
     * 
     * @param pageable 分页参数
     * @return 分页结果
     */
    Page<KnowledgeBase> findAll(Pageable pageable);

    /**
     * 根据知识文档类型查询知识文档
     * 
     * @param documentType 知识文档类型
     * @return 知识文档列表
     */
    List<KnowledgeBase> findByDocumentType(Integer documentType);

    /**
     * 根据知识文档状态查询知识文档
     * 
     * @param status 知识文档状态
     * @return 知识文档列表
     */
    List<KnowledgeBase> findByStatus(Integer status);

    /**
     * 根据分类查询知识文档
     * 
     * @param category 知识文档分类
     * @return 知识文档列表
     */
    List<KnowledgeBase> findByCategory(String category);

    /**
     * 根据作者ID查询知识文档
     * 
     * @param authorId 作者ID
     * @return 知识文档列表
     */
    List<KnowledgeBase> findByAuthorId(Long authorId);

    /**
     * 根据实验室ID查询知识文档
     * 
     * @param laboratoryId 实验室ID
     * @return 知识文档列表
     */
    List<KnowledgeBase> findByLaboratoryId(Long laboratoryId);

    /**
     * 根据试验项目ID查询知识文档
     * 
     * @param experimentProjectId 试验项目ID
     * @return 知识文档列表
     */
    List<KnowledgeBase> findByExperimentProjectId(Long experimentProjectId);

    /**
     * 根据设备ID查询知识文档
     * 
     * @param equipmentId 设备ID
     * @return 知识文档列表
     */
    List<KnowledgeBase> findByEquipmentId(Long equipmentId);

    /**
     * 根据关键词查询知识文档（模糊查询）
     * 
     * @param keywords 关键词
     * @return 知识文档列表
     */
    List<KnowledgeBase> findByKeywordsContaining(String keywords);

    /**
     * 根据标签查询知识文档（模糊查询）
     * 
     * @param tags 标签
     * @return 知识文档列表
     */
    List<KnowledgeBase> findByTagsContaining(String tags);

    /**
     * 根据时间范围查询知识文档
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 知识文档列表
     */
    List<KnowledgeBase> findByCreateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据是否置顶查询知识文档
     * 
     * @param isTop 是否置顶
     * @return 知识文档列表
     */
    List<KnowledgeBase> findByIsTop(Integer isTop);

    /**
     * 根据是否推荐查询知识文档
     * 
     * @param isRecommended 是否推荐
     * @return 知识文档列表
     */
    List<KnowledgeBase> findByIsRecommended(Integer isRecommended);

    /**
     * 根据权限级别查询知识文档
     * 
     * @param permissionLevel 权限级别
     * @return 知识文档列表
     */
    List<KnowledgeBase> findByPermissionLevel(Integer permissionLevel);

    /**
     * 根据标题查询知识文档（模糊查询）
     * 
     * @param title 标题
     * @return 知识文档列表
     */
    List<KnowledgeBase> findByTitleContaining(String title);

    /**
     * 删除知识文档
     * 
     * @param id 知识文档ID
     * @return 删除结果
     */
    boolean deleteById(Long id);

    /**
     * 更新知识文档状态
     * 
     * @param id 知识文档ID
     * @param status 新状态
     * @return 更新结果
     */
    boolean updateStatus(Long id, Integer status);

    /**
     * 更新访问次数
     * 
     * @param id 知识文档ID
     * @return 更新结果
     */
    boolean updateViewCount(Long id);

    /**
     * 更新下载次数
     * 
     * @param id 知识文档ID
     * @return 更新结果
     */
    boolean updateDownloadCount(Long id);

    /**
     * 提交审核
     * 
     * @param id 知识文档ID
     * @return 提交审核结果
     */
    boolean submitForReview(Long id);

    /**
     * 审核知识文档
     * 
     * @param id 知识文档ID
     * @param reviewerId 审核人ID
     * @param approved 是否批准
     * @param reviewComments 审核意见
     * @return 审核结果
     */
    boolean review(Long id, Long reviewerId, boolean approved, String reviewComments);

    /**
     * 发布知识文档
     * 
     * @param id 知识文档ID
     * @return 发布结果
     */
    boolean publish(Long id);

    /**
     * 归档知识文档
     * 
     * @param id 知识文档ID
     * @return 归档结果
     */
    boolean archive(Long id);

    /**
     * 设置置顶
     * 
     * @param id 知识文档ID
     * @param isTop 是否置顶
     * @return 设置结果
     */
    boolean setTop(Long id, Integer isTop);

    /**
     * 设置推荐
     * 
     * @param id 知识文档ID
     * @param isRecommended 是否推荐
     * @return 设置结果
     */
    boolean setRecommended(Long id, Integer isRecommended);

    /**
     * 搜索知识文档
     * 
     * @param query 搜索关键词
     * @param documentType 文档类型
     * @param category 分类
     * @param pageable 分页参数
     * @return 搜索结果
     */
    Page<KnowledgeBase> search(String query, Integer documentType, String category, Pageable pageable);

}