package com.windtunnel.controller.knowledge;

import com.windtunnel.common.Result;
import com.windtunnel.entity.knowledge.KnowledgeBase;
import com.windtunnel.service.knowledge.KnowledgeBaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 知识库控制器
 * 
 * 提供知识库相关的REST API接口
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/api/knowledge")
@Tag(name = "知识库管理", description = "知识库相关的API接口")
public class KnowledgeBaseController {

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    /**
     * 创建知识文档
     * 
     * @param knowledgeBase 知识文档实体
     * @return 创建结果
     */
    @Operation(summary = "创建知识文档", description = "创建新的知识文档")
    @PostMapping
    public Result<KnowledgeBase> createKnowledgeDocument(@RequestBody KnowledgeBase knowledgeBase) {
        log.info("创建知识文档请求: {}", knowledgeBase.getTitle());
        try {
            KnowledgeBase savedDocument = knowledgeBaseService.save(knowledgeBase);
            return Result.success("知识文档创建成功", savedDocument);
        } catch (Exception e) {
            log.error("创建知识文档失败: {}", e.getMessage(), e);
            return Result.error("创建知识文档失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取知识文档
     * 
     * @param id 知识文档ID
     * @return 知识文档信息
     */
    @Operation(summary = "根据ID获取知识文档", description = "根据ID获取知识文档的详细信息")
    @GetMapping("/{id}")
    public Result<KnowledgeBase> getKnowledgeDocumentById(@PathVariable Long id) {
        log.info("获取知识文档请求，ID: {}", id);
        try {
            KnowledgeBase document = knowledgeBaseService.findById(id);
            if (document != null) {
                // 更新访问次数
                knowledgeBaseService.updateViewCount(id);
                return Result.success("查询成功", document);
            } else {
                return Result.error("知识文档不存在");
            }
        } catch (Exception e) {
            log.error("获取知识文档失败: {}", e.getMessage(), e);
            return Result.error("获取知识文档失败: " + e.getMessage());
        }
    }

    /**
     * 分页查询知识文档
     * 
     * @param page 页码
     * @param size 页面大小
     * @param sortBy 排序字段
     * @param direction 排序方向
     * @return 知识文档分页结果
     */
    @Operation(summary = "分页查询知识文档", description = "分页获取知识文档列表")
    @GetMapping
    public Result<Page<KnowledgeBase>> getKnowledgeDocuments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createTime") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {
        log.info("分页查询知识文档请求，页码: {}, 大小: {}", page, size);
        try {
            Sort sort = direction.equalsIgnoreCase("ASC") ? 
                Sort.by(Sort.Direction.ASC, sortBy) : 
                Sort.by(Sort.Direction.DESC, sortBy);
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<KnowledgeBase> documentsPage = knowledgeBaseService.findAll(pageable);
            return Result.success("查询成功", documentsPage);
        } catch (Exception e) {
            log.error("分页查询知识文档失败: {}", e.getMessage(), e);
            return Result.error("分页查询知识文档失败: " + e.getMessage());
        }
    }

    /**
     * 根据知识文档类型查询知识文档
     * 
     * @param documentType 知识文档类型
     * @return 知识文档列表
     */
    @Operation(summary = "根据类型查询知识文档", description = "根据知识文档类型获取知识文档列表")
    @GetMapping("/type/{documentType}")
    public Result<List<KnowledgeBase>> getKnowledgeDocumentsByType(@PathVariable Integer documentType) {
        log.info("根据类型查询知识文档请求，类型: {}", documentType);
        try {
            List<KnowledgeBase> documents = knowledgeBaseService.findByDocumentType(documentType);
            return Result.success("查询成功", documents);
        } catch (Exception e) {
            log.error("查询知识文档失败: {}", e.getMessage(), e);
            return Result.error("查询知识文档失败: " + e.getMessage());
        }
    }

    /**
     * 根据知识文档状态查询知识文档
     * 
     * @param status 知识文档状态
     * @return 知识文档列表
     */
    @Operation(summary = "根据状态查询知识文档", description = "根据知识文档状态获取知识文档列表")
    @GetMapping("/status/{status}")
    public Result<List<KnowledgeBase>> getKnowledgeDocumentsByStatus(@PathVariable Integer status) {
        log.info("根据状态查询知识文档请求，状态: {}", status);
        try {
            List<KnowledgeBase> documents = knowledgeBaseService.findByStatus(status);
            return Result.success("查询成功", documents);
        } catch (Exception e) {
            log.error("查询知识文档失败: {}", e.getMessage(), e);
            return Result.error("查询知识文档失败: " + e.getMessage());
        }
    }

    /**
     * 根据分类查询知识文档
     * 
     * @param category 分类
     * @return 知识文档列表
     */
    @Operation(summary = "根据分类查询知识文档", description = "根据知识文档分类获取知识文档列表")
    @GetMapping("/category/{category}")
    public Result<List<KnowledgeBase>> getKnowledgeDocumentsByCategory(@PathVariable String category) {
        log.info("根据分类查询知识文档请求，分类: {}", category);
        try {
            List<KnowledgeBase> documents = knowledgeBaseService.findByCategory(category);
            return Result.success("查询成功", documents);
        } catch (Exception e) {
            log.error("查询知识文档失败: {}", e.getMessage(), e);
            return Result.error("查询知识文档失败: " + e.getMessage());
        }
    }

    /**
     * 根据作者ID查询知识文档
     * 
     * @param authorId 作者ID
     * @return 知识文档列表
     */
    @Operation(summary = "根据作者查询知识文档", description = "根据作者ID获取知识文档列表")
    @GetMapping("/author/{authorId}")
    public Result<List<KnowledgeBase>> getKnowledgeDocumentsByAuthorId(@PathVariable Long authorId) {
        log.info("根据作者ID查询知识文档请求，作者ID: {}", authorId);
        try {
            List<KnowledgeBase> documents = knowledgeBaseService.findByAuthorId(authorId);
            return Result.success("查询成功", documents);
        } catch (Exception e) {
            log.error("查询知识文档失败: {}", e.getMessage(), e);
            return Result.error("查询知识文档失败: " + e.getMessage());
        }
    }

    /**
     * 根据实验室ID查询知识文档
     * 
     * @param laboratoryId 实验室ID
     * @return 知识文档列表
     */
    @Operation(summary = "根据实验室查询知识文档", description = "根据实验室ID获取知识文档列表")
    @GetMapping("/laboratory/{laboratoryId}")
    public Result<List<KnowledgeBase>> getKnowledgeDocumentsByLaboratoryId(@PathVariable Long laboratoryId) {
        log.info("根据实验室ID查询知识文档请求，实验室ID: {}", laboratoryId);
        try {
            List<KnowledgeBase> documents = knowledgeBaseService.findByLaboratoryId(laboratoryId);
            return Result.success("查询成功", documents);
        } catch (Exception e) {
            log.error("查询知识文档失败: {}", e.getMessage(), e);
            return Result.error("查询知识文档失败: " + e.getMessage());
        }
    }

    /**
     * 根据试验项目ID查询知识文档
     * 
     * @param experimentProjectId 试验项目ID
     * @return 知识文档列表
     */
    @Operation(summary = "根据试验项目查询知识文档", description = "根据试验项目ID获取知识文档列表")
    @GetMapping("/project/{experimentProjectId}")
    public Result<List<KnowledgeBase>> getKnowledgeDocumentsByProjectId(@PathVariable Long experimentProjectId) {
        log.info("根据试验项目ID查询知识文档请求，项目ID: {}", experimentProjectId);
        try {
            List<KnowledgeBase> documents = knowledgeBaseService.findByExperimentProjectId(experimentProjectId);
            return Result.success("查询成功", documents);
        } catch (Exception e) {
            log.error("查询知识文档失败: {}", e.getMessage(), e);
            return Result.error("查询知识文档失败: " + e.getMessage());
        }
    }

    /**
     * 根据设备ID查询知识文档
     * 
     * @param equipmentId 设备ID
     * @return 知识文档列表
     */
    @Operation(summary = "根据设备查询知识文档", description = "根据设备ID获取知识文档列表")
    @GetMapping("/equipment/{equipmentId}")
    public Result<List<KnowledgeBase>> getKnowledgeDocumentsByEquipmentId(@PathVariable Long equipmentId) {
        log.info("根据设备ID查询知识文档请求，设备ID: {}", equipmentId);
        try {
            List<KnowledgeBase> documents = knowledgeBaseService.findByEquipmentId(equipmentId);
            return Result.success("查询成功", documents);
        } catch (Exception e) {
            log.error("查询知识文档失败: {}", e.getMessage(), e);
            return Result.error("查询知识文档失败: " + e.getMessage());
        }
    }

    /**
     * 根据关键词查询知识文档
     * 
     * @param keywords 关键词
     * @return 知识文档列表
     */
    @Operation(summary = "根据关键词查询知识文档", description = "根据关键词搜索知识文档")
    @GetMapping("/search/keywords")
    public Result<List<KnowledgeBase>> getKnowledgeDocumentsByKeywords(@RequestParam String keywords) {
        log.info("根据关键词查询知识文档请求，关键词: {}", keywords);
        try {
            List<KnowledgeBase> documents = knowledgeBaseService.findByKeywordsContaining(keywords);
            return Result.success("查询成功", documents);
        } catch (Exception e) {
            log.error("查询知识文档失败: {}", e.getMessage(), e);
            return Result.error("查询知识文档失败: " + e.getMessage());
        }
    }

    /**
     * 根据标签查询知识文档
     * 
     * @param tags 标签
     * @return 知识文档列表
     */
    @Operation(summary = "根据标签查询知识文档", description = "根据标签搜索知识文档")
    @GetMapping("/search/tags")
    public Result<List<KnowledgeBase>> getKnowledgeDocumentsByTags(@RequestParam String tags) {
        log.info("根据标签查询知识文档请求，标签: {}", tags);
        try {
            List<KnowledgeBase> documents = knowledgeBaseService.findByTagsContaining(tags);
            return Result.success("查询成功", documents);
        } catch (Exception e) {
            log.error("查询知识文档失败: {}", e.getMessage(), e);
            return Result.error("查询知识文档失败: " + e.getMessage());
        }
    }

    /**
     * 根据标题查询知识文档
     * 
     * @param title 标题
     * @return 知识文档列表
     */
    @Operation(summary = "根据标题查询知识文档", description = "根据标题搜索知识文档")
    @GetMapping("/search/title")
    public Result<List<KnowledgeBase>> getKnowledgeDocumentsByTitle(@RequestParam String title) {
        log.info("根据标题查询知识文档请求，标题: {}", title);
        try {
            List<KnowledgeBase> documents = knowledgeBaseService.findByTitleContaining(title);
            return Result.success("查询成功", documents);
        } catch (Exception e) {
            log.error("查询知识文档失败: {}", e.getMessage(), e);
            return Result.error("查询知识文档失败: " + e.getMessage());
        }
    }

    /**
     * 更新知识文档
     * 
     * @param id 知识文档ID
     * @param knowledgeBase 知识文档实体
     * @return 更新结果
     */
    @Operation(summary = "更新知识文档", description = "更新知识文档信息")
    @PutMapping("/{id}")
    public Result<KnowledgeBase> updateKnowledgeDocument(@PathVariable Long id, 
                                                       @RequestBody KnowledgeBase knowledgeBase) {
        log.info("更新知识文档请求，ID: {}", id);
        try {
            knowledgeBase.setId(id);
            KnowledgeBase updatedDocument = knowledgeBaseService.save(knowledgeBase);
            return Result.success("知识文档更新成功", updatedDocument);
        } catch (Exception e) {
            log.error("更新知识文档失败: {}", e.getMessage(), e);
            return Result.error("更新知识文档失败: " + e.getMessage());
        }
    }

    /**
     * 删除知识文档
     * 
     * @param id 知识文档ID
     * @return 删除结果
     */
    @Operation(summary = "删除知识文档", description = "根据ID删除知识文档")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteKnowledgeDocument(@PathVariable Long id) {
        log.info("删除知识文档请求，ID: {}", id);
        try {
            boolean result = knowledgeBaseService.deleteById(id);
            if (result) {
                return Result.success("知识文档删除成功", true);
            } else {
                return Result.error("知识文档删除失败");
            }
        } catch (Exception e) {
            log.error("删除知识文档失败: {}", e.getMessage(), e);
            return Result.error("删除知识文档失败: " + e.getMessage());
        }
    }

    /**
     * 提交知识文档审核
     * 
     * @param id 知识文档ID
     * @return 提交审核结果
     */
    @Operation(summary = "提交审核", description = "提交知识文档进行审核")
    @PostMapping("/{id}/submit-review")
    public Result<Boolean> submitForReview(@PathVariable Long id) {
        log.info("提交知识文档审核请求，ID: {}", id);
        try {
            boolean result = knowledgeBaseService.submitForReview(id);
            if (result) {
                return Result.success("提交审核成功", true);
            } else {
                return Result.error("提交审核失败");
            }
        } catch (Exception e) {
            log.error("提交审核失败: {}", e.getMessage(), e);
            return Result.error("提交审核失败: " + e.getMessage());
        }
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
    @Operation(summary = "审核知识文档", description = "审核知识文档")
    @PostMapping("/{id}/review")
    public Result<Boolean> reviewKnowledgeDocument(@PathVariable Long id,
                                                 @RequestParam Long reviewerId,
                                                 @RequestParam boolean approved,
                                                 @RequestParam String reviewComments) {
        log.info("审核知识文档请求，ID: {}, 批准: {}", id, approved);
        try {
            boolean result = knowledgeBaseService.review(id, reviewerId, approved, reviewComments);
            if (result) {
                String message = approved ? "知识文档审核通过" : "知识文档审核未通过";
                return Result.success(message, true);
            } else {
                return Result.error("审核失败");
            }
        } catch (Exception e) {
            log.error("审核知识文档失败: {}", e.getMessage(), e);
            return Result.error("审核知识文档失败: " + e.getMessage());
        }
    }

    /**
     * 发布知识文档
     * 
     * @param id 知识文档ID
     * @return 发布结果
     */
    @Operation(summary = "发布知识文档", description = "发布知识文档")
    @PostMapping("/{id}/publish")
    public Result<Boolean> publishKnowledgeDocument(@PathVariable Long id) {
        log.info("发布知识文档请求，ID: {}", id);
        try {
            boolean result = knowledgeBaseService.publish(id);
            if (result) {
                return Result.success("知识文档发布成功", true);
            } else {
                return Result.error("知识文档发布失败");
            }
        } catch (Exception e) {
            log.error("发布知识文档失败: {}", e.getMessage(), e);
            return Result.error("发布知识文档失败: " + e.getMessage());
        }
    }

    /**
     * 归档知识文档
     * 
     * @param id 知识文档ID
     * @return 归档结果
     */
    @Operation(summary = "归档知识文档", description = "归档知识文档")
    @PostMapping("/{id}/archive")
    public Result<Boolean> archiveKnowledgeDocument(@PathVariable Long id) {
        log.info("归档知识文档请求，ID: {}", id);
        try {
            boolean result = knowledgeBaseService.archive(id);
            if (result) {
                return Result.success("知识文档归档成功", true);
            } else {
                return Result.error("知识文档归档失败");
            }
        } catch (Exception e) {
            log.error("归档知识文档失败: {}", e.getMessage(), e);
            return Result.error("归档知识文档失败: " + e.getMessage());
        }
    }

    /**
     * 设置知识文档置顶
     * 
     * @param id 知识文档ID
     * @param isTop 是否置顶
     * @return 设置结果
     */
    @Operation(summary = "设置置顶", description = "设置知识文档是否置顶")
    @PutMapping("/{id}/top")
    public Result<Boolean> setTop(@PathVariable Long id,
                                @RequestParam Integer isTop) {
        log.info("设置知识文档置顶请求，ID: {}, 置顶: {}", id, isTop);
        try {
            boolean result = knowledgeBaseService.setTop(id, isTop);
            if (result) {
                String message = isTop == 1 ? "知识文档置顶设置成功" : "知识文档取消置顶成功";
                return Result.success(message, true);
            } else {
                return Result.error("设置置顶失败");
            }
        } catch (Exception e) {
            log.error("设置置顶失败: {}", e.getMessage(), e);
            return Result.error("设置置顶失败: " + e.getMessage());
        }
    }

    /**
     * 设置知识文档推荐
     * 
     * @param id 知识文档ID
     * @param isRecommended 是否推荐
     * @return 设置结果
     */
    @Operation(summary = "设置推荐", description = "设置知识文档是否推荐")
    @PutMapping("/{id}/recommended")
    public Result<Boolean> setRecommended(@PathVariable Long id,
                                       @RequestParam Integer isRecommended) {
        log.info("设置知识文档推荐请求，ID: {}, 推荐: {}", id, isRecommended);
        try {
            boolean result = knowledgeBaseService.setRecommended(id, isRecommended);
            if (result) {
                String message = isRecommended == 1 ? "知识文档推荐设置成功" : "知识文档取消推荐成功";
                return Result.success(message, true);
            } else {
                return Result.error("设置推荐失败");
            }
        } catch (Exception e) {
            log.error("设置推荐失败: {}", e.getMessage(), e);
            return Result.error("设置推荐失败: " + e.getMessage());
        }
    }

    /**
     * 搜索知识文档
     * 
     * @param query 搜索关键词
     * @param documentType 文档类型
     * @param category 分类
     * @param page 页码
     * @param size 页面大小
     * @param sortBy 排序字段
     * @param direction 排序方向
     * @return 搜索结果
     */
    @Operation(summary = "搜索知识文档", description = "根据关键词、类型、分类等条件搜索知识文档")
    @GetMapping("/search")
    public Result<Page<KnowledgeBase>> searchKnowledgeDocuments(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Integer documentType,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createTime") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {
        log.info("搜索知识文档请求，关键词: {}, 类型: {}, 分类: {}", query, documentType, category);
        try {
            Sort sort = direction.equalsIgnoreCase("ASC") ? 
                Sort.by(Sort.Direction.ASC, sortBy) : 
                Sort.by(Sort.Direction.DESC, sortBy);
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<KnowledgeBase> searchResult = knowledgeBaseService.search(query, documentType, category, pageable);
            return Result.success("搜索成功", searchResult);
        } catch (Exception e) {
            log.error("搜索知识文档失败: {}", e.getMessage(), e);
            return Result.error("搜索知识文档失败: " + e.getMessage());
        }
    }

}