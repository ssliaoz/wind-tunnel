package com.windtunnel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.windtunnel.entity.Laboratory;
import com.windtunnel.common.Result;

import java.util.List;

/**
 * 实验室服务接口
 * 
 * 提供实验室相关的业务逻辑处理方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
public interface LaboratoryService extends IService<Laboratory> {

    /**
     * 根据实验室名称查询实验室
     * 
     * @param name 实验室名称
     * @return 实验室实体
     */
    Laboratory findByName(String name);

    /**
     * 创建实验室
     * 
     * @param laboratory 实验室实体
     * @return 创建结果
     */
    Result<Laboratory> createLaboratory(Laboratory laboratory);

    /**
     * 更新实验室信息
     * 
     * @param laboratory 实验室实体
     * @return 更新结果
     */
    Result<Laboratory> updateLaboratory(Laboratory laboratory);

    /**
     * 更新实验室状态
     * 
     * @param laboratoryId 实验室ID
     * @param status 状态
     * @return 更新结果
     */
    Result<Boolean> updateLaboratoryStatus(Long laboratoryId, Integer status);

    /**
     * 查询所有启用的实验室
     * 
     * @return 实验室列表
     */
    Result<List<Laboratory>> findEnabledLaboratories();

    /**
     * 根据负责人ID查询实验室列表
     * 
     * @param managerId 负责人ID
     * @return 实验室列表
     */
    Result<List<Laboratory>> findByManagerId(Long managerId);

    /**
     * 分页查询实验室列表
     * 
     * @param page 页码
     * @param size 页面大小
     * @return 实验室列表
     */
    Result<List<Laboratory>> findLaboratories(int page, int size);

    /**
     * 删除实验室
     * 
     * @param laboratoryId 实验室ID
     * @return 删除结果
     */
    Result<Boolean> deleteLaboratory(Long laboratoryId);

}