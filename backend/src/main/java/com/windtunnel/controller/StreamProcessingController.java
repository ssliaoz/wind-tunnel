package com.windtunnel.controller;

import com.windtunnel.common.Result;
import com.windtunnel.entity.RealTimeData;
import com.windtunnel.service.StreamProcessingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 流处理控制器
 * 
 * 提供流处理引擎相关的REST API接口，实现Apache Flink的功能
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/api/stream-processing")
@Tag(name = "流处理服务", description = "提供实时流处理、窗口计算、数据聚合等功能")
public class StreamProcessingController {

    @Autowired
    private StreamProcessingService streamProcessingService;

    /**
     * 按时间窗口聚合数据
     */
    @GetMapping("/aggregate/window")
    @Operation(summary = "时间窗口聚合", description = "按指定时间窗口对数据进行聚合计算")
    public Result<Map<String, Object>> aggregateByTimeWindow(
            @RequestParam String source,
            @RequestParam(defaultValue = "60") int windowSize,
            @RequestParam(defaultValue = "30") int slideSize) {
        log.info("时间窗口聚合请求，数据源: {}, 窗口大小: {}, 滑动步长: {}", source, windowSize, slideSize);
        return streamProcessingService.aggregateByTimeWindow(source, windowSize, slideSize);
    }

    /**
     * 计算平均值
     */
    @GetMapping("/calculate/average")
    @Operation(summary = "计算平均值", description = "计算指定时间范围内的数据平均值")
    public Result<Map<String, Object>> calculateAverage(
            @RequestParam String source,
            @RequestParam String startTime,
            @RequestParam String endTime) {
        log.info("计算平均值请求，数据源: {}, 时间范围: {} - {}", source, startTime, endTime);
        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);
        return streamProcessingService.calculateAverage(source, start, end);
    }

    /**
     * 计算最大值
     */
    @GetMapping("/calculate/max")
    @Operation(summary = "计算最大值", description = "计算指定时间范围内的数据最大值")
    public Result<Map<String, Object>> calculateMax(
            @RequestParam String source,
            @RequestParam String startTime,
            @RequestParam String endTime) {
        log.info("计算最大值请求，数据源: {}, 时间范围: {} - {}", source, startTime, endTime);
        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);
        return streamProcessingService.calculateMax(source, start, end);
    }

    /**
     * 计算最小值
     */
    @GetMapping("/calculate/min")
    @Operation(summary = "计算最小值", description = "计算指定时间范围内的数据最小值")
    public Result<Map<String, Object>> calculateMin(
            @RequestParam String source,
            @RequestParam String startTime,
            @RequestParam String endTime) {
        log.info("计算最小值请求，数据源: {}, 时间范围: {} - {}", source, startTime, endTime);
        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);
        return streamProcessingService.calculateMin(source, start, end);
    }

    /**
     * 检测复杂事件
     */
    @PostMapping("/detect/complex-events")
    @Operation(summary = "检测复杂事件", description = "检测复杂事件序列（CEP）")
    public Result<List<RealTimeData>> detectComplexEvents(@RequestBody List<RealTimeData> events) {
        log.info("复杂事件检测请求，事件数量: {}", events != null ? events.size() : 0);
        return streamProcessingService.detectComplexEvents(events);
    }

    /**
     * 实时数据质量监控
     */
    @PostMapping("/monitor/data-quality")
    @Operation(summary = "数据质量监控", description = "对实时数据进行质量监控")
    public Result<Map<String, Object>> monitorDataQuality(@RequestBody RealTimeData realTimeData) {
        log.info("数据质量监控请求，数据ID: {}", realTimeData.getId());
        return streamProcessingService.monitorDataQuality(realTimeData);
    }

    /**
     * 基于规则的异常检测
     */
    @PostMapping("/detect/anomaly-by-rules")
    @Operation(summary = "规则异常检测", description = "基于预定义规则进行异常检测")
    public Result<Boolean> detectAnomalyByRules(@RequestBody RealTimeData realTimeData) {
        log.info("规则异常检测请求，数据ID: {}", realTimeData.getId());
        return streamProcessingService.detectAnomalyByRules(realTimeData);
    }

    /**
     * 阈值监控与告警
     */
    @PostMapping("/monitor/threshold")
    @Operation(summary = "阈值监控", description = "基于阈值配置进行监控和告警")
    public Result<Map<String, Object>> thresholdMonitoring(
            @RequestBody RealTimeData realTimeData,
            @RequestBody(required = false) Map<String, Object> thresholds) {
        log.info("阈值监控请求，数据ID: {}", realTimeData.getId());
        if (thresholds == null) {
            thresholds = Map.of();
        }
        return streamProcessingService.thresholdMonitoring(realTimeData, thresholds);
    }

    /**
     * 趋势分析与预测
     */
    @GetMapping("/analysis/trend")
    @Operation(summary = "趋势分析", description = "对指定数据源进行趋势分析")
    public Result<Map<String, Object>> trendAnalysis(
            @RequestParam String source,
            @RequestParam(defaultValue = "10") int dataPoints) {
        log.info("趋势分析请求，数据源: {}, 数据点数量: {}", source, dataPoints);
        return streamProcessingService.trendAnalysis(source, dataPoints);
    }

    /**
     * 处理实时数据流
     */
    @PostMapping("/process/realtime")
    @Operation(summary = "处理实时数据", description = "处理单条实时数据流")
    public Result<RealTimeData> processRealTimeDataStream(@RequestBody RealTimeData realTimeData) {
        log.info("处理实时数据流请求，数据ID: {}", realTimeData.getId());
        return streamProcessingService.processRealTimeDataStream(realTimeData);
    }
}