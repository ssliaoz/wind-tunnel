package com.windtunnel.service;

import com.windtunnel.job.DataCleanupJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

/**
 * 定时任务调度服务
 * 
 * 负责初始化和启动系统预定义的定时任务
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Service
public class ScheduledTaskService {

    @Autowired
    private QuartzJobService quartzJobService;

    /**
     * 初始化定时任务
     * 
     * 应用启动后自动初始化预定义的定时任务
     */
    @PostConstruct
    public void initScheduledTasks() {
        log.info("开始初始化定时任务");
        
        try {
            // 添加数据清理任务 - 每天凌晨2点执行
            addDataCleanupJob();
            
            // 启动调度器
            quartzJobService.startScheduler();
            
            log.info("定时任务初始化完成");
        } catch (Exception e) {
            log.error("初始化定时任务失败", e);
        }
    }

    /**
     * 添加数据清理任务
     * 
     * @throws SchedulerException 调度器异常
     */
    private void addDataCleanupJob() throws SchedulerException {
        // 创建任务数据
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("description", "数据清理任务");
        
        // 每天凌晨2点执行: 0 0 2 * * ?
        quartzJobService.addJob("dataCleanupJob", "default", "0 0 2 * * ?", 
                               DataCleanupJob.class, jobDataMap);
    }

    /**
     * 添加其他定时任务的方法可以在这里定义
     * 
     * 例如：
     * - 系统监控任务
     * - 数据备份任务
     * - 报告生成任务
     * 等等
     */

}