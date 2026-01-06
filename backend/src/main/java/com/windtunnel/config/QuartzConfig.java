package com.windtunnel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Quartz定时任务配置类
 * 
 * 配置Quartz调度器相关参数
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Configuration
public class QuartzConfig {

    /**
     * 创建调度器工厂Bean
     * 
     * @param dataSource 数据源
     * @return 调度器工厂Bean
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@NonNull DataSource dataSource) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);
        
        // 设置Quartz属性
        Properties quartzProperties = new Properties();
        quartzProperties.setProperty("org.quartz.scheduler.instanceName", "WindTunnelScheduler");
        quartzProperties.setProperty("org.quartz.scheduler.instanceId", "AUTO");
        quartzProperties.setProperty("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        quartzProperties.setProperty("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        quartzProperties.setProperty("org.quartz.jobStore.useProperties", "false");
        quartzProperties.setProperty("org.quartz.jobStore.tablePrefix", "QRTZ_");
        quartzProperties.setProperty("org.quartz.jobStore.isClustered", "true");
        quartzProperties.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        quartzProperties.setProperty("org.quartz.threadPool.threadCount", "5");
        quartzProperties.setProperty("org.quartz.threadPool.threadPriority", "5");
        quartzProperties.setProperty("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread", "true");
        
        factory.setQuartzProperties(quartzProperties);
        factory.setWaitForJobsToCompleteOnShutdown(true);
        factory.setOverwriteExistingJobs(true);
        
        return factory;
    }

}