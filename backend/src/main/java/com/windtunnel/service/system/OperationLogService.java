package com.windtunnel.service.system;

import com.windtunnel.entity.system.OperationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 操作日志服务接口
 * 
 * 定义操作日志相关的业务操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
public interface OperationLogService {

    /**
     * 保存操作日志
     * 
     * @param operationLog 操作日志实体
     * @return 保存后的操作日志实体
     */
    OperationLog save(OperationLog operationLog);

    /**
     * 根据ID查询操作日志
     * 
     * @param id 操作日志ID
     * @return 操作日志实体
     */
    OperationLog findById(Long id);

    /**
     * 查询所有操作日志
     * 
     * @return 操作日志列表
     */
    List<OperationLog> findAll();

    /**
     * 分页查询操作日志
     * 
     * @param pageable 分页参数
     * @return 分页结果
     */
    Page<OperationLog> findAll(Pageable pageable);

    /**
     * 根据用户ID查询操作日志
     * 
     * @param userId 用户ID
     * @return 操作日志列表
     */
    List<OperationLog> findByUserId(Long userId);

    /**
     * 根据操作类型查询操作日志
     * 
     * @param operationType 操作类型
     * @return 操作日志列表
     */
    List<OperationLog> findByOperationType(Integer operationType);

    /**
     * 根据操作模块查询操作日志
     * 
     * @param module 操作模块
     * @return 操作日志列表
     */
    List<OperationLog> findByModule(String module);

    /**
     * 根据操作状态查询操作日志
     * 
     * @param status 操作状态
     * @return 操作日志列表
     */
    List<OperationLog> findByStatus(Integer status);

    /**
     * 根据时间范围查询操作日志
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 操作日志列表
     */
    List<OperationLog> findByOperationTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据实验室ID查询操作日志
     * 
     * @param laboratoryId 实验室ID
     * @return 操作日志列表
     */
    List<OperationLog> findByLaboratoryId(Long laboratoryId);

    /**
     * 根据IP地址查询操作日志
     * 
     * @param ipAddress IP地址
     * @return 操作日志列表
     */
    List<OperationLog> findByIpAddress(String ipAddress);

    /**
     * 根据请求URL查询操作日志
     * 
     * @param requestUrl 请求URL
     * @return 操作日志列表
     */
    List<OperationLog> findByRequestUrl(String requestUrl);

    /**
     * 删除操作日志
     * 
     * @param id 操作日志ID
     * @return 删除结果
     */
    boolean deleteById(Long id);

    /**
     * 批量删除操作日志
     * 
     * @param ids 操作日志ID列表
     * @return 删除结果
     */
    boolean deleteByIds(List<Long> ids);

    /**
     * 清空指定时间之前的日志
     * 
     * @param beforeTime 指定时间
     * @return 删除数量
     */
    int clearLogsBeforeTime(LocalDateTime beforeTime);

    /**
     * 统计操作日志数量
     * 
     * @param operationType 操作类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计数量
     */
    long countByTypeAndTime(Integer operationType, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 生成操作日志统计报告
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计报告
     */
    String generateOperationLogReport(LocalDateTime startTime, LocalDateTime endTime);

}