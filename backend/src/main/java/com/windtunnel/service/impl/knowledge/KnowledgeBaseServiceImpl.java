package com.windtunnel.service.impl.knowledge;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.windtunnel.entity.knowledge.KnowledgeBase;
import com.windtunnel.repository.knowledge.KnowledgeBaseRepository;
import com.windtunnel.service.knowledge.KnowledgeBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 知识库服务实现类
 * 
 * 实现知识库相关的业务操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Service
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {

    @Autowired
    private KnowledgeBaseRepository knowledgeBaseRepository;

    /**
     * 保存知识文档
     * 
     * @param knowledgeBase 知识文档实体
     * @return 保存后的知识文档实体
     */
    @Override
    public KnowledgeBase save(KnowledgeBase knowledgeBase) {
        log.debug("保存知识文档: {}", knowledgeBase.getTitle());
        if (knowledgeBase.getId() == null || knowledgeBase.getId() <= 0) {
            knowledgeBaseRepository.insert(knowledgeBase);
        } else {
            knowledgeBaseRepository.updateById(knowledgeBase);
        }
        return knowledgeBase;
    }

    /**
     * 根据ID查询知识文档
     * 
     * @param id 知识文档ID
     * @return 知识文档实体
     */
    @Override
    public KnowledgeBase findById(Long id) {
        log.debug("根据ID查询知识文档: {}", id);
        return knowledgeBaseRepository.selectById(id);
    }

    /**
     * 查询所有知识文档
     * 
     * @return 知识文档列表
     */
    @Override
    public List<KnowledgeBase> findAll() {
        log.debug("查询所有知识文档");
        return knowledgeBaseRepository.selectList(null);
    }

    /**
     * 分页查询知识文档
     * 
     * @param pageable 分页参数
     * @return 分页结果
     */
    @Override
    public org.springframework.data.domain.Page<KnowledgeBase> findAll(Pageable pageable) {
        log.debug("分页查询知识文档");
        Page<KnowledgeBase> mpPage = new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        Page<KnowledgeBase> resultPage = knowledgeBaseRepository.selectPage(mpPage, null);
        
        // 转换为Spring Data分页对象
        return new PageImpl<>(
            resultPage.getRecords(),
            pageable,
            resultPage.getTotal()
        );
    }

    /**
     * 根据知识文档类型查询知识文档
     * 
     * @param documentType 知识文档类型
     * @return 知识文档列表
     */
    @Override
    public List<KnowledgeBase> findByDocumentType(Integer documentType) {
        log.debug("根据知识文档类型查询知识文档: {}", documentType);
        QueryWrapper<KnowledgeBase> wrapper = new QueryWrapper<>();
        wrapper.eq("document_type", documentType);
        return knowledgeBaseRepository.selectList(wrapper);
    }

    /**
     * 根据知识文档状态查询知识文档
     * 
     * @param status 知识文档状态
     * @return 知识文档列表
     */
    @Override
    public List<KnowledgeBase> findByStatus(Integer status) {
        log.debug("根据知识文档状态查询知识文档: {}", status);
        QueryWrapper<KnowledgeBase> wrapper = new QueryWrapper<>();
        wrapper.eq("status", status);
        return knowledgeBaseRepository.selectList(wrapper);
    }

    /**
     * 根据分类查询知识文档
     * 
     * @param category 知识文档分类
     * @return 知识文档列表
     */
    @Override
    public List<KnowledgeBase> findByCategory(String category) {
        log.debug("根据分类查询知识文档: {}", category);
        QueryWrapper<KnowledgeBase> wrapper = new QueryWrapper<>();
        wrapper.eq("category", category);
        return knowledgeBaseRepository.selectList(wrapper);
    }

    /**
     * 根据作者ID查询知识文档
     * 
     * @param authorId 作者ID
     * @return 知识文档列表
     */
    @Override
    public List<KnowledgeBase> findByAuthorId(Long authorId) {
        log.debug("根据作者ID查询知识文档: {}", authorId);
        QueryWrapper<KnowledgeBase> wrapper = new QueryWrapper<>();
        wrapper.eq("author_id", authorId);
        return knowledgeBaseRepository.selectList(wrapper);
    }

    /**
     * 根据实验室ID查询知识文档
     * 
     * @param laboratoryId 实验室ID
     * @return 知识文档列表
     */
    @Override
    public List<KnowledgeBase> findByLaboratoryId(Long laboratoryId) {
        log.debug("根据实验室ID查询知识文档: {}", laboratoryId);
        QueryWrapper<KnowledgeBase> wrapper = new QueryWrapper<>();
        wrapper.eq("laboratory_id", laboratoryId);
        return knowledgeBaseRepository.selectList(wrapper);
    }

    /**
     * 根据试验项目ID查询知识文档
     * 
     * @param experimentProjectId 试验项目ID
     * @return 知识文档列表
     */
    @Override
    public List<KnowledgeBase> findByExperimentProjectId(Long experimentProjectId) {
        log.debug("根据试验项目ID查询知识文档: {}", experimentProjectId);
        QueryWrapper<KnowledgeBase> wrapper = new QueryWrapper<>();
        wrapper.eq("experiment_project_id", experimentProjectId);
        return knowledgeBaseRepository.selectList(wrapper);
    }

    /**
     * 根据设备ID查询知识文档
     * 
     * @param equipmentId 设备ID
     * @return 知识文档列表
     */
    @Override
    public List<KnowledgeBase> findByEquipmentId(Long equipmentId) {
        log.debug("根据设备ID查询知识文档: {}", equipmentId);
        QueryWrapper<KnowledgeBase> wrapper = new QueryWrapper<>();
        wrapper.eq("equipment_id", equipmentId);
        return knowledgeBaseRepository.selectList(wrapper);
    }

    /**
     * 根据关键词查询知识文档（模糊查询）
     * 
     * @param keywords 关键词
     * @return 知识文档列表
     */
    @Override
    public List<KnowledgeBase> findByKeywordsContaining(String keywords) {
        log.debug("根据关键词查询知识文档: {}", keywords);
        QueryWrapper<KnowledgeBase> wrapper = new QueryWrapper<>();
        wrapper.like("keywords", keywords);
        return knowledgeBaseRepository.selectList(wrapper);
    }

    /**
     * 根据标签查询知识文档（模糊查询）
     * 
     * @param tags 标签
     * @return 知识文档列表
     */
    @Override
    public List<KnowledgeBase> findByTagsContaining(String tags) {
        log.debug("根据标签查询知识文档: {}", tags);
        QueryWrapper<KnowledgeBase> wrapper = new QueryWrapper<>();
        wrapper.like("tags", tags);
        return knowledgeBaseRepository.selectList(wrapper);
    }

    /**
     * 根据时间范围查询知识文档
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 知识文档列表
     */
    @Override
    public List<KnowledgeBase> findByCreateTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        log.debug("根据时间范围查询知识文档: {} - {}", startTime, endTime);
        QueryWrapper<KnowledgeBase> wrapper = new QueryWrapper<>();
        wrapper.between("create_time", startTime, endTime);
        return knowledgeBaseRepository.selectList(wrapper);
    }

    /**
     * 根据是否置顶查询知识文档
     * 
     * @param isTop 是否置顶
     * @return 知识文档列表
     */
    @Override
    public List<KnowledgeBase> findByIsTop(Integer isTop) {
        log.debug("根据是否置顶查询知识文档: {}", isTop);
        QueryWrapper<KnowledgeBase> wrapper = new QueryWrapper<>();
        wrapper.eq("is_top", isTop);
        return knowledgeBaseRepository.selectList(wrapper);
    }

    /**
     * 根据是否推荐查询知识文档
     * 
     * @param isRecommended 是否推荐
     * @return 知识文档列表
     */
    @Override
    public List<KnowledgeBase> findByIsRecommended(Integer isRecommended) {
        log.debug("根据是否推荐查询知识文档: {}", isRecommended);
        QueryWrapper<KnowledgeBase> wrapper = new QueryWrapper<>();
        wrapper.eq("is_recommended", isRecommended);
        return knowledgeBaseRepository.selectList(wrapper);
    }

    /**
     * 根据权限级别查询知识文档
     * 
     * @param permissionLevel 权限级别
     * @return 知识文档列表
     */
    @Override
    public List<KnowledgeBase> findByPermissionLevel(Integer permissionLevel) {
        log.debug("根据权限级别查询知识文档: {}", permissionLevel);
        QueryWrapper<KnowledgeBase> wrapper = new QueryWrapper<>();
        wrapper.eq("permission_level", permissionLevel);
        return knowledgeBaseRepository.selectList(wrapper);
    }

    /**
     * 根据标题查询知识文档（模糊查询）
     * 
     * @param title 标题
     * @return 知识文档列表
     */
    @Override
    public List<KnowledgeBase> findByTitleContaining(String title) {
        log.debug("根据标题查询知识文档: {}", title);
        QueryWrapper<KnowledgeBase> wrapper = new QueryWrapper<>();
        wrapper.like("title", title);
        return knowledgeBaseRepository.selectList(wrapper);
    }

    /**
     * 删除知识文档
     * 
     * @param id 知识文档ID
     * @return 删除结果
     */
    @Override
    public boolean deleteById(Long id) {
        log.debug("删除知识文档: {}", id);
        return knowledgeBaseRepository.deleteById(id) > 0;
    }

    /**
     * 更新知识文档状态
     * 
     * @param id 知识文档ID
     * @param status 新状态
     * @return 更新结果
     */
    @Override
    public boolean updateStatus(Long id, Integer status) {
        log.info("更新知识文档状态: {}, 状态: {}", id, status);
        KnowledgeBase knowledge = findById(id);
        if (knowledge != null) {
            knowledge.setStatus(status);
            knowledge.setUpdateTime(LocalDateTime.now());
            return knowledgeBaseRepository.updateById(knowledge) > 0;
        }
        return false;
    }

    /**
     * 更新访问次数
     * 
     * @param id 知识文档ID
     * @return 更新结果
     */
    @Override
    public boolean updateViewCount(Long id) {
        log.info("更新知识文档访问次数: {}", id);
        KnowledgeBase knowledge = findById(id);
        if (knowledge != null) {
            knowledge.setViewCount((knowledge.getViewCount() == null ? 0 : knowledge.getViewCount()) + 1);
            knowledge.setUpdateTime(LocalDateTime.now());
            return knowledgeBaseRepository.updateById(knowledge) > 0;
        }
        return false;
    }

    /**
     * 更新下载次数
     * 
     * @param id 知识文档ID
     * @return 更新结果
     */
    @Override
    public boolean updateDownloadCount(Long id) {
        log.info("更新知识文档下载次数: {}", id);
        KnowledgeBase knowledge = findById(id);
        if (knowledge != null) {
            knowledge.setDownloadCount((knowledge.getDownloadCount() == null ? 0 : knowledge.getDownloadCount()) + 1);
            knowledge.setUpdateTime(LocalDateTime.now());
            return knowledgeBaseRepository.updateById(knowledge) > 0;
        }
        return false;
    }

    /**
     * 提交审核
     * 
     * @param id 知识文档ID
     * @return 提交审核结果
     */
    @Override
    public boolean submitForReview(Long id) {
        log.info("提交知识文档审核: {}", id);
        KnowledgeBase knowledge = findById(id);
        if (knowledge != null) {
            knowledge.setStatus(1); // 1-审核中
            knowledge.setUpdateTime(LocalDateTime.now());
            return knowledgeBaseRepository.updateById(knowledge) > 0;
        }
        return false;
    }

    /**
     * 审核知识文档
     * 
     * @param id 知识文档ID
     * @param reviewerId 审核人ID
     * @param approved 是否批准
     * @param reviewComments 审核意见
     * @return 审核结果
     */
    @Override
    public boolean review(Long id, Long reviewerId, boolean approved, String reviewComments) {
        log.info("审核知识文档: {}, 批准: {}", id, approved);
        KnowledgeBase knowledge = findById(id);
        if (knowledge != null) {
            knowledge.setReviewerId(reviewerId);
            knowledge.setReviewComments(reviewComments);
            knowledge.setReviewTime(LocalDateTime.now());
            knowledge.setStatus(approved ? 2 : 0); // 2-已发布，0-草稿
            knowledge.setUpdateTime(LocalDateTime.now());
            return knowledgeBaseRepository.updateById(knowledge) > 0;
        }
        return false;
    }

    /**
     * 发布知识文档
     * 
     * @param id 知识文档ID
     * @return 发布结果
     */
    @Override
    public boolean publish(Long id) {
        log.info("发布知识文档: {}", id);
        KnowledgeBase knowledge = findById(id);
        if (knowledge != null) {
            knowledge.setStatus(2); // 2-已发布
            knowledge.setPublishTime(LocalDateTime.now());
            knowledge.setUpdateTime(LocalDateTime.now());
            return knowledgeBaseRepository.updateById(knowledge) > 0;
        }
        return false;
    }

    /**
     * 归档知识文档
     * 
     * @param id 知识文档ID
     * @return 归档结果
     */
    @Override
    public boolean archive(Long id) {
        log.info("归档知识文档: {}", id);
        KnowledgeBase knowledge = findById(id);
        if (knowledge != null) {
            knowledge.setStatus(3); // 3-已归档
            knowledge.setArchiveTime(LocalDateTime.now());
            knowledge.setUpdateTime(LocalDateTime.now());
            return knowledgeBaseRepository.updateById(knowledge) > 0;
        }
        return false;
    }

    /**
     * 设置置顶
     * 
     * @param id 知识文档ID
     * @param isTop 是否置顶
     * @return 设置结果
     */
    @Override
    public boolean setTop(Long id, Integer isTop) {
        log.info("设置知识文档置顶: {}, 置顶: {}", id, isTop);
        KnowledgeBase knowledge = findById(id);
        if (knowledge != null) {
            knowledge.setIsTop(isTop);
            knowledge.setTopTime(isTop == 1 ? LocalDateTime.now() : null);
            knowledge.setUpdateTime(LocalDateTime.now());
            return knowledgeBaseRepository.updateById(knowledge) > 0;
        }
        return false;
    }

    /**
     * 设置推荐
     * 
     * @param id 知识文档ID
     * @param isRecommended 是否推荐
     * @return 设置结果
     */
    @Override
    public boolean setRecommended(Long id, Integer isRecommended) {
        log.info("设置知识文档推荐: {}, 推荐: {}", id, isRecommended);
        KnowledgeBase knowledge = findById(id);
        if (knowledge != null) {
            knowledge.setIsRecommended(isRecommended);
            knowledge.setRecommendedTime(isRecommended == 1 ? LocalDateTime.now() : null);
            knowledge.setUpdateTime(LocalDateTime.now());
            return knowledgeBaseRepository.updateById(knowledge) > 0;
        }
        return false;
    }

    /**
     * 搜索知识文档
     * 
     * @param query 搜索关键词
     * @param documentType 文档类型
     * @param category 分类
     * @param pageable 分页参数
     * @return 搜索结果
     */
    @Override
    public org.springframework.data.domain.Page<KnowledgeBase> search(String query, Integer documentType, String category, Pageable pageable) {
        log.info("搜索知识文档: {}, 类型: {}, 分类: {}", query, documentType, category);
        
        QueryWrapper<KnowledgeBase> wrapper = new QueryWrapper<>();
        
        if (query != null && !query.isEmpty()) {
            wrapper.and(w -> w.like("title", query)
                           .or()
                           .like("content", query)
                           .or()
                           .like("keywords", query));
        }
        
        if (documentType != null) {
            wrapper.eq("document_type", documentType);
        }
        
        if (category != null) {
            wrapper.eq("category", category);
        }
        
        Page<KnowledgeBase> mpPage = new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        Page<KnowledgeBase> resultPage = knowledgeBaseRepository.selectPage(mpPage, wrapper);
        
        // 转换为Spring Data分页对象
        return new PageImpl<>(
            resultPage.getRecords(),
            pageable,
            resultPage.getTotal()
        );
    }

}