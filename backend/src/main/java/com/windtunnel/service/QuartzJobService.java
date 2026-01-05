package com.windtunnel.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Quartz任务服务类
 * 
 * 用于管理定时任务的创建、执行、暂停、恢复和删除
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Service
public class QuartzJobService {

    @Autowired
    private Scheduler scheduler;

    /**
     * 添加定时任务
     * 
     * @param jobName 任务名称
     * @param jobGroup 任务组名
     * @param cronExpression cron表达式
     * @param jobClass 任务类
     * @param jobDataMap 任务数据
     * @throws SchedulerException 调度器异常
     */
    public void addJob(String jobName, String jobGroup, String cronExpression, 
                      Class<? extends Job> jobClass, JobDataMap jobDataMap) throws SchedulerException {
        // 构建任务详情
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName, jobGroup)
                .withDescription("定时任务")
                .setJobData(jobDataMap)
                .build();

        // 构建触发器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, jobGroup)
                .withSchedule(scheduleBuilder)
                .build();

        // 添加任务到调度器
        scheduler.scheduleJob(jobDetail, cronTrigger);
        log.info("定时任务添加成功: {}-{}", jobName, jobGroup);
    }

    /**
     * 暂停任务
     * 
     * @param jobName 任务名称
     * @param jobGroup 任务组名
     * @throws SchedulerException 调度器异常
     */
    public void pauseJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        scheduler.pauseJob(jobKey);
        log.info("定时任务暂停: {}-{}", jobName, jobGroup);
    }

    /**
     * 恢复任务
     * 
     * @param jobName 任务名称
     * @param jobGroup 任务组名
     * @throws SchedulerException 调度器异常
     */
    public void resumeJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        scheduler.resumeJob(jobKey);
        log.info("定时任务恢复: {}-{}", jobName, jobGroup);
    }

    /**
     * 删除任务
     * 
     * @param jobName 任务名称
     * @param jobGroup 任务组名
     * @throws SchedulerException 调度器异常
     */
    public void deleteJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        scheduler.deleteJob(jobKey);
        log.info("定时任务删除: {}-{}", jobName, jobGroup);
    }

    /**
     * 立即执行任务
     * 
     * @param jobName 任务名称
     * @param jobGroup 任务组名
     * @throws SchedulerException 调度器异常
     */
    public void executeJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        scheduler.triggerJob(jobKey);
        log.info("定时任务立即执行: {}-{}", jobName, jobGroup);
    }

    /**
     * 更新任务Cron表达式
     * 
     * @param jobName 任务名称
     * @param jobGroup 任务组名
     * @param cronExpression 新的Cron表达式
     * @throws SchedulerException 调度器异常
     */
    public void updateJobCron(String jobName, String jobGroup, String cronExpression) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        
        if (cronTrigger != null) {
            // 构建新的触发器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            CronTrigger newCronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder)
                    .build();
            
            // 重新调度任务
            scheduler.rescheduleJob(triggerKey, newCronTrigger);
            log.info("定时任务Cron表达式更新: {}-{}", jobName, jobGroup);
        }
    }

    /**
     * 开始调度器
     * 
     * @throws SchedulerException 调度器异常
     */
    public void startScheduler() throws SchedulerException {
        if (!scheduler.isStarted()) {
            scheduler.start();
            log.info("调度器已启动");
        }
    }

    /**
     * 关闭调度器
     * 
     * @throws SchedulerException 调度器异常
     */
    public void shutdownScheduler() throws SchedulerException {
        if (!scheduler.isShutdown()) {
            scheduler.shutdown();
            log.info("调度器已关闭");
        }
    }

}
