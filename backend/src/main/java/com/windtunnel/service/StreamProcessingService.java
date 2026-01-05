package com.windtunnel.service;

import com.windtunnel.entity.RealTimeData;
import com.windtunnel.common.Result;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 流处理服务接口
 * 
 * 实现Apache Flink流处理引擎相关功能，包括窗口计算、数据聚合、复杂事件处理等
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
public interface StreamProcessingService {

    /**
     * 按时间窗口聚合数据
     * 
     * @param source 数据源
     * @param windowSize 窗口大小（秒）
     * @param slideSize 滑动步长（秒）
     * @return 聚合结果
     */
    Result<Map<String, Object>> aggregateByTimeWindow(String source, int windowSize, int slideSize);

    /**
     * 计算平均值
     * 
     * @param source 数据源
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 平均值计算结果
     */
    Result<Map<String, Object>> calculateAverage(String source, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 计算最大值
     * 
     * @param source 数据源
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 最大值计算结果
     */
    Result<Map<String, Object>> calculateMax(String source, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 计算最小值
     * 
     * @param source 数据源
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 最小值计算结果
     */
    Result<Map<String, Object>> calculateMin(String source, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 检测复杂事件（CEP）
     * 
     * @param events 事件序列
     * @return 事件检测结果
     */
    Result<List<RealTimeData>> detectComplexEvents(List<RealTimeData> events);

    /**
     * 实时数据质量监控
     * 
     * @param realTimeData 实时数据
     * @return 数据质量评估结果
     */
    Result<Map<String, Object>> monitorDataQuality(RealTimeData realTimeData);

    /**
     * 基于规则的异常检测
     * 
     * @param realTimeData 实时数据
     * @return 异常检测结果
     */
    Result<Boolean> detectAnomalyByRules(RealTimeData realTimeData);

    /**
     * 阈值监控与告警
     * 
     * @param realTimeData 实时数据
     * @param thresholds 阈值配置
     * @return 阈值监控结果
     */
    Result<Map<String, Object>> thresholdMonitoring(RealTimeData realTimeData, Map<String, Object> thresholds);

    /**
     * 趋势分析与预测
     * 
     * @param source 数据源
     * @param dataPoints 数据点数量
     * @return 趋势分析结果
     */
    Result<Map<String, Object>> trendAnalysis(String source, int dataPoints);

    /**
     * 处理实时数据流
     * 
     * @param realTimeData 实时数据
     * @return 处理结果
     */
    Result<RealTimeData> processRealTimeDataStream(RealTimeData realTimeData);
}