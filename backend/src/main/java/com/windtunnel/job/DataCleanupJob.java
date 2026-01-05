package com.windtunnel.job;

import com.windtunnel.service.RealTimeDataService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 数据清理定时任务
 * 
 * 定期清理过期的实时数据，释放数据库空间
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Component
public class DataCleanupJob implements Job {

    @Autowired
    private RealTimeDataService realTimeDataService;

    /**
     * 执行数据清理任务
     * 
     * @param context 任务执行上下文
     * @throws JobExecutionException 任务执行异常
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("开始执行数据清理任务");
        
        try {
            // 计算过期时间（例如：30天前的数据）
            LocalDateTime expiredTime = LocalDateTime.now().minusDays(30);
            
            // 调用服务层清理过期数据
            int deletedCount = realTimeDataService.deleteExpiredData(expiredTime);
            
            log.info("数据清理任务完成，共删除 {} 条过期数据，截止时间: {}", deletedCount, expiredTime);
        } catch (Exception e) {
            log.error("执行数据清理任务失败", e);
            throw new JobExecutionException("数据清理任务执行失败", e);
        }
    }

}