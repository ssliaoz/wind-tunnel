package com.windtunnel.service.impl.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.windtunnel.entity.system.OperationLog;
import com.windtunnel.repository.system.OperationLogRepository;
import com.windtunnel.service.system.OperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 操作日志服务实现类
 * 
 * 实现操作日志相关的业务操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogRepository operationLogRepository;

    /**
     * 保存操作日志
     * 
     * @param operationLog 操作日志实体
     * @return 保存后的操作日志实体
     */
    @Override
    public OperationLog save(OperationLog operationLog) {
        log.debug("保存操作日志: {}", operationLog.getDescription());
        if (operationLog.getId() == null || operationLog.getId() <= 0) {
            operationLogRepository.insert(operationLog);
        } else {
            operationLogRepository.updateById(operationLog);
        }
        return operationLog;
    }

    /**
     * 根据ID查询操作日志
     * 
     * @param id 操作日志ID
     * @return 操作日志实体
     */
    @Override
    public OperationLog findById(Long id) {
        log.debug("根据ID查询操作日志: {}", id);
        return operationLogRepository.selectById(id);
    }

    /**
     * 查询所有操作日志
     * 
     * @return 操作日志列表
     */
    @Override
    public List<OperationLog> findAll() {
        log.debug("查询所有操作日志");
        return operationLogRepository.selectList(null);
    }

    /**
     * 分页查询操作日志
     * 
     * @param pageable 分页参数
     * @return 分页结果
     */
    @Override
    public org.springframework.data.domain.Page<OperationLog> findAll(Pageable pageable) {
        log.debug("分页查询操作日志");
        Page<OperationLog> mpPage = new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        Page<OperationLog> resultPage = operationLogRepository.selectPage(mpPage, null);
        
        // 转换为Spring Data分页对象
        return new PageImpl<>(
            resultPage.getRecords(),
            pageable,
            resultPage.getTotal()
        );
    }

    /**
     * 根据用户ID查询操作日志
     * 
     * @param userId 用户ID
     * @return 操作日志列表
     */
    @Override
    public List<OperationLog> findByUserId(Long userId) {
        log.debug("根据用户ID查询操作日志: {}", userId);
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return operationLogRepository.selectList(wrapper);
    }

    /**
     * 根据操作类型查询操作日志
     * 
     * @param operationType 操作类型
     * @return 操作日志列表
     */
    @Override
    public List<OperationLog> findByOperationType(Integer operationType) {
        log.debug("根据操作类型查询操作日志: {}", operationType);
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        wrapper.eq("operation_type", operationType);
        return operationLogRepository.selectList(wrapper);
    }

    /**
     * 根据操作模块查询操作日志
     * 
     * @param module 操作模块
     * @return 操作日志列表
     */
    @Override
    public List<OperationLog> findByModule(String module) {
        log.debug("根据操作模块查询操作日志: {}", module);
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        wrapper.eq("module", module);
        return operationLogRepository.selectList(wrapper);
    }

    /**
     * 根据操作状态查询操作日志
     * 
     * @param status 操作状态
     * @return 操作日志列表
     */
    @Override
    public List<OperationLog> findByStatus(Integer status) {
        log.debug("根据操作状态查询操作日志: {}", status);
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        wrapper.eq("status", status);
        return operationLogRepository.selectList(wrapper);
    }

    /**
     * 根据时间范围查询操作日志
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 操作日志列表
     */
    @Override
    public List<OperationLog> findByOperationTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        log.debug("根据时间范围查询操作日志: {} - {}", startTime, endTime);
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        wrapper.between("operation_time", startTime, endTime);
        return operationLogRepository.selectList(wrapper);
    }

    /**
     * 根据实验室ID查询操作日志
     * 
     * @param laboratoryId 实验室ID
     * @return 操作日志列表
     */
    @Override
    public List<OperationLog> findByLaboratoryId(Long laboratoryId) {
        log.debug("根据实验室ID查询操作日志: {}", laboratoryId);
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        wrapper.eq("laboratory_id", laboratoryId);
        return operationLogRepository.selectList(wrapper);
    }

    /**
     * 根据IP地址查询操作日志
     * 
     * @param ipAddress IP地址
     * @return 操作日志列表
     */
    @Override
    public List<OperationLog> findByIpAddress(String ipAddress) {
        log.debug("根据IP地址查询操作日志: {}", ipAddress);
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        wrapper.eq("ip_address", ipAddress);
        return operationLogRepository.selectList(wrapper);
    }

    /**
     * 根据请求URL查询操作日志
     * 
     * @param requestUrl 请求URL
     * @return 操作日志列表
     */
    @Override
    public List<OperationLog> findByRequestUrl(String requestUrl) {
        log.debug("根据请求URL查询操作日志: {}", requestUrl);
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        wrapper.eq("request_url", requestUrl);
        return operationLogRepository.selectList(wrapper);
    }

    /**
     * 删除操作日志
     * 
     * @param id 操作日志ID
     * @return 删除结果
     */
    @Override
    public boolean deleteById(Long id) {
        log.debug("删除操作日志: {}", id);
        return operationLogRepository.deleteById(id) > 0;
    }

    /**
     * 批量删除操作日志
     * 
     * @param ids 操作日志ID列表
     * @return 删除结果
     */
    @Override
    public boolean deleteByIds(List<Long> ids) {
        log.debug("批量删除操作日志: {}", ids);
        return operationLogRepository.deleteBatchIds(ids) > 0;
    }

    /**
     * 清空指定时间之前的日志
     * 
     * @param beforeTime 指定时间
     * @return 删除数量
     */
    @Override
    public int clearLogsBeforeTime(LocalDateTime beforeTime) {
        log.info("清空指定时间之前的操作日志: {}", beforeTime);
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        wrapper.lt("operation_time", beforeTime);
        return operationLogRepository.delete(wrapper);
    }

    /**
     * 统计操作日志数量
     * 
     * @param operationType 操作类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计数量
     */
    @Override
    public long countByTypeAndTime(Integer operationType, LocalDateTime startTime, LocalDateTime endTime) {
        log.debug("统计操作日志数量，类型: {}, 时间: {} - {}", operationType, startTime, endTime);
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        if (operationType != null) {
            wrapper.eq("operation_type", operationType);
        }
        if (startTime != null && endTime != null) {
            wrapper.between("operation_time", startTime, endTime);
        }
        return operationLogRepository.selectCount(wrapper);
    }

    /**
     * 生成操作日志统计报告
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计报告
     */
    @Override
    public String generateOperationLogReport(LocalDateTime startTime, LocalDateTime endTime) {
        log.info("生成操作日志统计报告，时间范围: {} - {}", startTime, endTime);
        
        // 查询相关操作日志数据
        List<OperationLog> logs;
        if (startTime != null && endTime != null) {
            logs = findByOperationTimeBetween(startTime, endTime);
        } else {
            logs = findAll();
        }
        
        // 生成报告内容
        StringBuilder report = new StringBuilder();
        report.append("操作日志统计报告\n");
        report.append("生成时间: ").append(LocalDateTime.now()).append("\n");
        report.append("时间范围: ").append(startTime).append(" - ").append(endTime).append("\n\n");
        
        report.append("日志统计:\n");
        int totalLogs = logs.size();
        report.append("总日志数: ").append(totalLogs).append("\n");
        
        // 按操作类型统计
        int createCount = 0, updateCount = 0, deleteCount = 0, queryCount = 0, loginCount = 0, logoutCount = 0, otherCount = 0;
        int successCount = 0, failureCount = 0;
        
        for (OperationLog log : logs) {
            if (log.getOperationType() != null) {
                switch (log.getOperationType()) {
                    case 1: createCount++; break;
                    case 2: updateCount++; break;
                    case 3: deleteCount++; break;
                    case 4: queryCount++; break;
                    case 5: loginCount++; break;
                    case 6: logoutCount++; break;
                    default: otherCount++; break;
                }
            }
            if (log.getStatus() != null) {
                if (log.getStatus() == 1) {
                    successCount++;
                } else {
                    failureCount++;
                }
            }
        }
        
        report.append("新增操作: ").append(createCount).append("\n");
        report.append("修改操作: ").append(updateCount).append("\n");
        report.append("删除操作: ").append(deleteCount).append("\n");
        report.append("查询操作: ").append(queryCount).append("\n");
        report.append("登录操作: ").append(loginCount).append("\n");
        report.append("登出操作: ").append(logoutCount).append("\n");
        report.append("其他操作: ").append(otherCount).append("\n\n");
        
        report.append("成功操作: ").append(successCount).append("\n");
        report.append("失败操作: ").append(failureCount).append("\n\n");
        
        report.append("成功率: ").append(String.format("%.2f%%", 
            totalLogs > 0 ? (double) successCount / totalLogs * 100 : 0)).append("\n\n");
        
        report.append("详细日志:\n");
        for (OperationLog log : logs) {
            report.append("用户: ").append(log.getUserName()).append("\n");
            report.append("操作: ").append(log.getDescription()).append("\n");
            report.append("模块: ").append(log.getModule()).append("\n");
            report.append("IP: ").append(log.getIpAddress()).append("\n");
            report.append("时间: ").append(log.getOperationTime()).append("\n");
            report.append("状态: ").append(log.getStatus() == 1 ? "成功" : "失败").append("\n");
            report.append("----------\n");
        }
        
        return report.toString();
    }

}