package com.windtunnel.service.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.lang.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

/**
 * 基于MyBatis-Plus的基础服务抽象类
 * 使用模板方法模式提供通用的CRUD操作
 *
 * @param <M>  Mapper类型
 * @param <T>  实体类型
 * @param <ID> 实体ID类型
 */
public abstract class MyBatisBaseService<M extends BaseMapper<T>, T, ID extends Serializable> extends ServiceImpl<M, T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 保存实体
     *
     * @param entity 实体
     * @return 保存是否成功
     */
    public boolean saveEntity(T entity) {
        logOperation("保存", entity);
        boolean result = super.save(entity);
        logOperationSuccess("保存", result);
        return result;
    }

    /**
     * 更新实体
     *
     * @param entity 实体
     * @return 更新是否成功
     */
    public boolean updateEntity(T entity) {
        logOperation("更新", entity);
        boolean result = super.updateById(entity);
        logOperationSuccess("更新", result);
        return result;
    }

    /**
     * 根据ID查找实体
     *
     * @param id ID
     * @return 实体
     */
    public T findById(@NonNull ID id) {
        logOperation("查找", id);
        T entity = super.getById(id);
        logOperationResult("查找", id, entity != null);
        return entity;
    }

    /**
     * 查找所有实体
     *
     * @return 实体列表
     */
    public List<T> findAll() {
        logOperation("查找所有", null);
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        List<T> entities = super.list(queryWrapper);
        logOperationSuccess("查找所有", entities.size() + " 条记录");
        return entities;
    }

    /**
     * 根据ID删除实体（逻辑删除）
     *
     * @param id ID
     * @return 删除是否成功
     */
    public boolean deleteById(@NonNull ID id) {
        logOperation("删除", id);
        boolean result = super.removeById(id);
        logOperationSuccess("删除", result);
        return result;
    }

    /**
     * 批量删除实体
     *
     * @param ids ID列表
     * @return 删除是否成功
     */
    public boolean deleteBatch(List<ID> ids) {
        logOperation("批量删除", ids.size() + " 条记录");
        boolean result = super.removeByIds(ids);
        logOperationSuccess("批量删除", result);
        return result;
    }

    /**
     * 判断实体是否存在
     *
     * @param id ID
     * @return 是否存在
     */
    public boolean existsById(@NonNull ID id) {
        return super.getById(id) != null;
    }

    /**
     * 获取实体总数
     *
     * @return 总数
     */
    public long count() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        return super.count(queryWrapper);
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