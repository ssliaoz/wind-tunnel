package com.windtunnel.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

/**
 * MongoDB基础服务抽象类
 * 使用模板方法模式提供通用的CRUD操作
 *
 * @param <T>  实体类型
 * @param <ID> 实体ID类型
 * @param <R>  Repository类型
 */
public abstract class MongoBaseService<T, ID extends Serializable, R extends MongoRepository<T, ID>> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected abstract R getRepository();

    /**
     * 保存实体
     *
     * @param entity 实体
     * @return 保存后的实体
     */
    @NonNull
    public T save(@NonNull T entity) {
        logOperation("保存", entity);
        T savedEntity = getRepository().save(entity);
        logOperationSuccess("保存", savedEntity);
        return savedEntity;
    }

    /**
     * 根据ID查找实体
     *
     * @param id ID
     * @return 实体
     */
    @Nullable
    public T findById(@NonNull ID id) {
        logOperation("查找", id);
        T entity = getRepository().findById(id).orElse(null);
        logOperationResult("查找", id, entity != null);
        return entity;
    }

    /**
     * 查找所有实体
     *
     * @return 实体列表
     */
    @NonNull
    public List<T> findAll() {
        logOperation("查找所有", null);
        List<T> entities = getRepository().findAll();
        logOperationSuccess("查找所有", entities.size() + " 条记录");
        return entities;
    }

    /**
     * 分页查找所有实体
     *
     * @param pageable 分页参数
     * @return 分页结果
     */
    @NonNull
    public Page<T> findAll(@NonNull Pageable pageable) {
        logOperation("分页查找", pageable);
        Page<T> entities = getRepository().findAll(pageable);
        logOperationSuccess("分页查找", entities.getTotalElements() + " 条记录");
        return entities;
    }

    /**
     * 根据ID删除实体
     *
     * @param id ID
     */
    public void deleteById(@NonNull ID id) {
        logOperation("删除", id);
        getRepository().deleteById(id);
        logOperationSuccess("删除", id);
    }

    /**
     * 删除实体
     *
     * @param entity 实体
     */
    public void delete(@NonNull T entity) {
        logOperation("删除", entity);
        getRepository().delete(entity);
        logOperationSuccess("删除", entity);
    }

    /**
     * 批量删除
     *
     * @param entities 实体列表
     */
    public void deleteAll(@NonNull List<T> entities) {
        logOperation("批量删除", entities.size() + " 条记录");
        getRepository().deleteAll(entities);
        logOperationSuccess("批量删除", entities.size() + " 条记录");
    }

    /**
     * 判断实体是否存在
     *
     * @param id ID
     * @return 是否存在
     */
    public boolean existsById(@NonNull ID id) {
        return getRepository().existsById(id);
    }

    /**
     * 获取实体总数
     *
     * @return 总数
     */
    public long count() {
        return getRepository().count();
    }

    /**
     * 记录操作日志
     */
    protected void logOperation(String operation, Object param) {
        logger.debug("{} 操作 - 参数: {}", operation, param);
    }

    /**
     * 记录操作成功日志
     */
    protected void logOperationSuccess(String operation, Object result) {
        logger.debug("{} 操作成功 - 结果: {}", operation, result);
    }

    /**
     * 记录操作结果日志
     */
    protected void logOperationResult(String operation, Object param, boolean success) {
        logger.debug("{} 操作 - 参数: {}, 结果: {}", operation, param, success ? "成功" : "失败");
    }
}