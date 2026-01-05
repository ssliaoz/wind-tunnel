package com.windtunnel.repository.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.windtunnel.entity.system.OperationLog;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 操作日志数据访问层
 * 
 * 提供操作日志相关的数据库操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Mapper
public interface OperationLogRepository extends BaseMapper<OperationLog> {

    /**
     * 根据用户ID查询操作日志列表
     * 
     * @param userId 用户ID
     * @return 操作日志列表
     */
    List<OperationLog> findByUserId(Long userId);

    /**
     * 根据操作类型查询操作日志列表
     * 
     * @param operationType 操作类型
     * @return 操作日志列表
     */
    List<OperationLog> findByOperationType(Integer operationType);

    /**
     * 根据操作模块查询操作日志列表
     * 
     * @param module 操作模块
     * @return 操作日志列表
     */
    List<OperationLog> findByModule(String module);

    /**
     * 根据操作状态查询操作日志列表
     * 
     * @param status 操作状态
     * @return 操作日志列表
     */
    List<OperationLog> findByStatus(Integer status);

    /**
     * 根据时间范围查询操作日志列表
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 操作日志列表
     */
    List<OperationLog> findByOperationTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据实验室ID查询操作日志列表
     * 
     * @param laboratoryId 实验室ID
     * @return 操作日志列表
     */
    List<OperationLog> findByLaboratoryId(Long laboratoryId);

    /**
     * 根据IP地址查询操作日志列表
     * 
     * @param ipAddress IP地址
     * @return 操作日志列表
     */
    List<OperationLog> findByIpAddress(String ipAddress);

    /**
     * 根据请求URL查询操作日志列表
     * 
     * @param requestUrl 请求URL
     * @return 操作日志列表
     */
    List<OperationLog> findByRequestUrl(String requestUrl);

}