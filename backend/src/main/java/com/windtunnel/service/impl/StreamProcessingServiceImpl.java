package com.windtunnel.service.impl;

import com.windtunnel.common.Result;
import com.windtunnel.entity.RealTimeData;
import com.windtunnel.repository.RealTimeDataRepository;
import com.windtunnel.service.StreamProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 流处理服务实现类
 * 
 * 实现Apache Flink流处理引擎相关功能，包括窗口计算、数据聚合、复杂事件处理等
 * 使用Spring WebFlux和响应式编程模型模拟Flink的流处理能力
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Service
public class StreamProcessingServiceImpl implements StreamProcessingService {

    @Autowired
    private RealTimeDataRepository realTimeDataRepository;
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Override
    public Result<Map<String, Object>> aggregateByTimeWindow(@org.springframework.lang.NonNull String source, int windowSize, int slideSize) {
        log.info("按时间窗口聚合数据，数据源: {}, 窗口大小: {}秒, 滑动步长: {}秒", source, windowSize, slideSize);
        
        try {
            // 计算时间范围
            LocalDateTime endTime = LocalDateTime.now();
            LocalDateTime startTime = endTime.minusSeconds(windowSize);
            
            // 查询指定时间范围内的数据
            Query query = new Query();
            query.addCriteria(Criteria.where("source").is(source)
                    .and("dataTime").gte(startTime).lte(endTime));
            List<RealTimeData> dataList = mongoTemplate.find(query, RealTimeData.class);
            
            // 执行聚合计算
            Map<String, Object> aggregationResult = performAggregation(dataList);
            
            return Result.success("时间窗口聚合完成", aggregationResult);
        } catch (Exception e) {
            log.error("时间窗口聚合失败: {}", e.getMessage(), e);
            return Result.error("时间窗口聚合失败: " + e.getMessage());
        }
    }

    @Override
    public Result<Map<String, Object>> calculateAverage(@org.springframework.lang.NonNull String source, @org.springframework.lang.NonNull LocalDateTime startTime, @org.springframework.lang.NonNull LocalDateTime endTime) {
        log.info("计算平均值，数据源: {}, 时间范围: {} - {}", source, startTime, endTime);
        
        try {
            List<RealTimeData> dataList = realTimeDataRepository.findBySourceAndDataTimeBetween(
                    source, startTime, endTime);
            
            Map<String, Object> avgResult = calculateAverageValues(dataList);
            
            return Result.success("平均值计算完成", avgResult);
        } catch (Exception e) {
            log.error("平均值计算失败: {}", e.getMessage(), e);
            return Result.error("平均值计算失败: " + e.getMessage());
        }
    }

    @Override
    public Result<Map<String, Object>> calculateMax(@org.springframework.lang.NonNull String source, @org.springframework.lang.NonNull LocalDateTime startTime, @org.springframework.lang.NonNull LocalDateTime endTime) {
        log.info("计算最大值，数据源: {}, 时间范围: {} - {}", source, startTime, endTime);
        
        try {
            List<RealTimeData> dataList = realTimeDataRepository.findBySourceAndDataTimeBetween(
                    source, startTime, endTime);
            
            Map<String, Object> maxResult = calculateMaxValues(dataList);
            
            return Result.success("最大值计算完成", maxResult);
        } catch (Exception e) {
            log.error("最大值计算失败: {}", e.getMessage(), e);
            return Result.error("最大值计算失败: " + e.getMessage());
        }
    }

    @Override
    public Result<Map<String, Object>> calculateMin(@org.springframework.lang.NonNull String source, @org.springframework.lang.NonNull LocalDateTime startTime, @org.springframework.lang.NonNull LocalDateTime endTime) {
        log.info("计算最小值，数据源: {}, 时间范围: {} - {}", source, startTime, endTime);
        
        try {
            List<RealTimeData> dataList = realTimeDataRepository.findBySourceAndDataTimeBetween(
                    source, startTime, endTime);
            
            Map<String, Object> minResult = calculateMinValues(dataList);
            
            return Result.success("最小值计算完成", minResult);
        } catch (Exception e) {
            log.error("最小值计算失败: {}", e.getMessage(), e);
            return Result.error("最小值计算失败: " + e.getMessage());
        }
    }

    @Override
    public Result<List<RealTimeData>> detectComplexEvents(@org.springframework.lang.NonNull List<RealTimeData> events) {
        log.info("检测复杂事件，事件数量: {}", events != null ? events.size() : 0);
        
        try {
            List<RealTimeData> detectedEvents = new ArrayList<>();
            
            if (events != null && events.size() > 1) {
                // 示例：检测连续异常事件
                for (int i = 0; i < events.size() - 1; i++) {
                    RealTimeData current = events.get(i);
                    RealTimeData next = events.get(i + 1);
                    
                    // 检测连续异常（如温度持续升高）
                    if (current.getTemperature() != null && next.getTemperature() != null &&
                        current.getTemperature().compareTo(next.getTemperature()) < 0 &&
                        current.getStatus() != 0 && next.getStatus() != 0) {
                        detectedEvents.add(current);
                        detectedEvents.add(next);
                    }
                }
            }
            
            return Result.success("复杂事件检测完成", detectedEvents);
        } catch (Exception e) {
            log.error("复杂事件检测失败: {}", e.getMessage(), e);
            return Result.error("复杂事件检测失败: " + e.getMessage());
        }
    }

    @Override
    public Result<Map<String, Object>> monitorDataQuality(@org.springframework.lang.NonNull RealTimeData realTimeData) {
        log.info("实时数据质量监控，数据ID: {}", realTimeData.getId());
        
        try {
            Map<String, Object> qualityMetrics = new HashMap<>();
            
            // 检查数据完整性
            qualityMetrics.put("completeness", checkDataCompleteness(realTimeData));
            
            // 检查数据一致性
            qualityMetrics.put("consistency", checkDataConsistency(realTimeData));
            
            // 检查数据准确性
            qualityMetrics.put("accuracy", checkDataAccuracy(realTimeData));
            
            // 检查数据时效性
            qualityMetrics.put("timeliness", checkDataTimeliness(realTimeData));
            
            return Result.success("数据质量监控完成", qualityMetrics);
        } catch (Exception e) {
            log.error("数据质量监控失败: {}", e.getMessage(), e);
            return Result.error("数据质量监控失败: " + e.getMessage());
        }
    }

    @Override
    public Result<Boolean> detectAnomalyByRules(@org.springframework.lang.NonNull RealTimeData realTimeData) {
        log.info("基于规则的异常检测，数据ID: {}", realTimeData.getId());
        
        try {
            boolean isAnomaly = false;
            StringBuilder anomalyReason = new StringBuilder();
            
            // 规则1: 风速异常检测
            if (realTimeData.getWindSpeed() != null) {
                if (realTimeData.getWindSpeed().compareTo(new BigDecimal("150")) > 0) {
                    isAnomaly = true;
                    anomalyReason.append("风速过高;");
                } else if (realTimeData.getWindSpeed().compareTo(new BigDecimal("0")) < 0) {
                    isAnomaly = true;
                    anomalyReason.append("风速为负值;");
                }
            }
            
            // 规则2: 温度异常检测
            if (realTimeData.getTemperature() != null) {
                if (realTimeData.getTemperature().compareTo(new BigDecimal("150")) > 0 ||
                    realTimeData.getTemperature().compareTo(new BigDecimal("-50")) < 0) {
                    isAnomaly = true;
                    anomalyReason.append("温度异常;");
                }
            }
            
            // 规则3: 压力异常检测
            if (realTimeData.getPressure() != null) {
                if (realTimeData.getPressure().compareTo(new BigDecimal("200")) > 0 ||
                    realTimeData.getPressure().compareTo(new BigDecimal("0")) < 0) {
                    isAnomaly = true;
                    anomalyReason.append("压力异常;");
                }
            }
            
            // 规则4: 电压异常检测
            if (realTimeData.getVoltage() != null) {
                if (realTimeData.getVoltage().compareTo(new BigDecimal("500")) > 0 ||
                    realTimeData.getVoltage().compareTo(new BigDecimal("0")) < 0) {
                    isAnomaly = true;
                    anomalyReason.append("电压异常;");
                }
            }
            
            // 规则5: 电流异常检测
            if (realTimeData.getCurrent() != null) {
                if (realTimeData.getCurrent().compareTo(new BigDecimal("100")) > 0 ||
                    realTimeData.getCurrent().compareTo(new BigDecimal("0")) < 0) {
                    isAnomaly = true;
                    anomalyReason.append("电流异常;");
                }
            }
            
            if (isAnomaly) {
                log.warn("检测到异常数据: {}, 原因: {}", realTimeData.getId(), anomalyReason.toString());
            }
            
            return Result.success("异常检测完成", isAnomaly);
        } catch (Exception e) {
            log.error("异常检测失败: {}", e.getMessage(), e);
            return Result.error("异常检测失败: " + e.getMessage());
        }
    }

    @Override
    public Result<Map<String, Object>> thresholdMonitoring(@org.springframework.lang.NonNull RealTimeData realTimeData, @org.springframework.lang.NonNull Map<String, Object> thresholds) {
        log.info("阈值监控与告警，数据ID: {}", realTimeData.getId());
        
        try {
            Map<String, Object> monitoringResult = new HashMap<>();
            
            // 检查风速阈值
            if (realTimeData.getWindSpeed() != null && thresholds.containsKey("windSpeedMax")) {
                BigDecimal maxWindSpeed = new BigDecimal(thresholds.get("windSpeedMax").toString());
                if (realTimeData.getWindSpeed().compareTo(maxWindSpeed) > 0) {
                    monitoringResult.put("windSpeedAlert", true);
                    monitoringResult.put("windSpeedMessage", "风速超过阈值: " + maxWindSpeed);
                }
            }
            
            // 检查温度阈值
            if (realTimeData.getTemperature() != null && thresholds.containsKey("temperatureMax")) {
                BigDecimal maxTemp = new BigDecimal(thresholds.get("temperatureMax").toString());
                if (realTimeData.getTemperature().compareTo(maxTemp) > 0) {
                    monitoringResult.put("temperatureAlert", true);
                    monitoringResult.put("temperatureMessage", "温度超过阈值: " + maxTemp);
                }
            }
            
            // 检查压力阈值
            if (realTimeData.getPressure() != null && thresholds.containsKey("pressureMax")) {
                BigDecimal maxPressure = new BigDecimal(thresholds.get("pressureMax").toString());
                if (realTimeData.getPressure().compareTo(maxPressure) > 0) {
                    monitoringResult.put("pressureAlert", true);
                    monitoringResult.put("pressureMessage", "压力超过阈值: " + maxPressure);
                }
            }
            
            // 检查电压阈值
            if (realTimeData.getVoltage() != null && thresholds.containsKey("voltageMax")) {
                BigDecimal maxVoltage = new BigDecimal(thresholds.get("voltageMax").toString());
                if (realTimeData.getVoltage().compareTo(maxVoltage) > 0) {
                    monitoringResult.put("voltageAlert", true);
                    monitoringResult.put("voltageMessage", "电压超过阈值: " + maxVoltage);
                }
            }
            
            // 检查电流阈值
            if (realTimeData.getCurrent() != null && thresholds.containsKey("currentMax")) {
                BigDecimal maxCurrent = new BigDecimal(thresholds.get("currentMax").toString());
                if (realTimeData.getCurrent().compareTo(maxCurrent) > 0) {
                    monitoringResult.put("currentAlert", true);
                    monitoringResult.put("currentMessage", "电流超过阈值: " + maxCurrent);
                }
            }
            
            return Result.success("阈值监控完成", monitoringResult);
        } catch (Exception e) {
            log.error("阈值监控失败: {}", e.getMessage(), e);
            return Result.error("阈值监控失败: " + e.getMessage());
        }
    }

    @Override
    public Result<Map<String, Object>> trendAnalysis(@org.springframework.lang.NonNull String source, int dataPoints) {
        log.info("趋势分析与预测，数据源: {}, 数据点数量: {}", source, dataPoints);
        
        try {
            // 获取最新的数据点
            Query query = new Query();
            query.addCriteria(Criteria.where("source").is(source));
            query.with(org.springframework.data.domain.Sort.by(
                    org.springframework.data.domain.Sort.Direction.DESC, "dataTime"));
            query.limit(dataPoints);
            
            List<RealTimeData> recentData = mongoTemplate.find(query, RealTimeData.class);
            
            // 反转列表以获得时间顺序
            Collections.reverse(recentData);
            
            Map<String, Object> trendResult = performTrendAnalysis(recentData);
            
            return Result.success("趋势分析完成", trendResult);
        } catch (Exception e) {
            log.error("趋势分析失败: {}", e.getMessage(), e);
            return Result.error("趋势分析失败: " + e.getMessage());
        }
    }

    @Override
    public Result<RealTimeData> processRealTimeDataStream(@org.springframework.lang.NonNull RealTimeData realTimeData) {
        log.info("处理实时数据流，数据ID: {}", realTimeData.getId());
        
        try {
            // 1. 数据验证
            Result<Boolean> validation = detectAnomalyByRules(realTimeData);
            if (validation.getData()) {
                log.warn("检测到异常数据: {}", realTimeData.getId());
                realTimeData.setStatus(1); // 设置为异常状态
            } else {
                realTimeData.setStatus(0); // 设置为正常状态
            }
            
            // 2. 数据质量监控
            Result<Map<String, Object>> qualityResult = monitorDataQuality(realTimeData);
            
            // 3. 阈值监控
            Map<String, Object> defaultThresholds = new HashMap<>();
            defaultThresholds.put("windSpeedMax", "150");
            defaultThresholds.put("temperatureMax", "100");
            defaultThresholds.put("pressureMax", "150");
            defaultThresholds.put("voltageMax", "400");
            defaultThresholds.put("currentMax", "80");
            
            Result<Map<String, Object>> thresholdResult = thresholdMonitoring(realTimeData, defaultThresholds);
            
            // 4. 更新风险等级
            updateRiskLevel(realTimeData, qualityResult.getData(), thresholdResult.getData());
            
            return Result.success("实时数据流处理完成", realTimeData);
        } catch (Exception e) {
            log.error("实时数据流处理失败: {}", e.getMessage(), e);
            return Result.error("实时数据流处理失败: " + e.getMessage());
        }
    }
    
    /**
     * 执行聚合计算
     */
    private Map<String, Object> performAggregation(List<RealTimeData> dataList) {
        Map<String, Object> result = new HashMap<>();
        
        if (dataList.isEmpty()) {
            return result;
        }
        
        // 计算平均值
        Map<String, Object> avgValues = calculateAverageValues(dataList);
        result.put("average", avgValues);
        
        // 计算最大值
        Map<String, Object> maxValues = calculateMaxValues(dataList);
        result.put("max", maxValues);
        
        // 计算最小值
        Map<String, Object> minValues = calculateMinValues(dataList);
        result.put("min", minValues);
        
        // 计算总数
        result.put("count", dataList.size());
        
        // 计算标准差
        Map<String, Object> stdDevValues = calculateStandardDeviation(dataList, avgValues);
        result.put("stdDev", stdDevValues);
        
        return result;
    }
    
    /**
     * 计算平均值
     */
    private Map<String, Object> calculateAverageValues(List<RealTimeData> dataList) {
        Map<String, Object> averages = new HashMap<>();
        
        if (dataList.isEmpty()) {
            return averages;
        }
        
        BigDecimal windSpeedSum = BigDecimal.ZERO;
        BigDecimal temperatureSum = BigDecimal.ZERO;
        BigDecimal pressureSum = BigDecimal.ZERO;
        BigDecimal flowSum = BigDecimal.ZERO;
        BigDecimal powerSum = BigDecimal.ZERO;
        BigDecimal vibrationSum = BigDecimal.ZERO;
        BigDecimal voltageSum = BigDecimal.ZERO;
        BigDecimal currentSum = BigDecimal.ZERO;
        
        int validWindSpeedCount = 0;
        int validTemperatureCount = 0;
        int validPressureCount = 0;
        int validFlowCount = 0;
        int validPowerCount = 0;
        int validVibrationCount = 0;
        int validVoltageCount = 0;
        int validCurrentCount = 0;
        
        for (RealTimeData data : dataList) {
            if (data.getWindSpeed() != null) {
                windSpeedSum = windSpeedSum.add(data.getWindSpeed());
                validWindSpeedCount++;
            }
            if (data.getTemperature() != null) {
                temperatureSum = temperatureSum.add(data.getTemperature());
                validTemperatureCount++;
            }
            if (data.getPressure() != null) {
                pressureSum = pressureSum.add(data.getPressure());
                validPressureCount++;
            }
            if (data.getFlow() != null) {
                flowSum = flowSum.add(data.getFlow());
                validFlowCount++;
            }
            if (data.getPower() != null) {
                powerSum = powerSum.add(data.getPower());
                validPowerCount++;
            }
            if (data.getVibration() != null) {
                vibrationSum = vibrationSum.add(data.getVibration());
                validVibrationCount++;
            }
            if (data.getVoltage() != null) {
                voltageSum = voltageSum.add(data.getVoltage());
                validVoltageCount++;
            }
            if (data.getCurrent() != null) {
                currentSum = currentSum.add(data.getCurrent());
                validCurrentCount++;
            }
        }
        
        if (validWindSpeedCount > 0) {
            averages.put("windSpeed", windSpeedSum.divide(BigDecimal.valueOf(validWindSpeedCount), 4, RoundingMode.HALF_UP));
        }
        if (validTemperatureCount > 0) {
            averages.put("temperature", temperatureSum.divide(BigDecimal.valueOf(validTemperatureCount), 4, RoundingMode.HALF_UP));
        }
        if (validPressureCount > 0) {
            averages.put("pressure", pressureSum.divide(BigDecimal.valueOf(validPressureCount), 4, RoundingMode.HALF_UP));
        }
        if (validFlowCount > 0) {
            averages.put("flow", flowSum.divide(BigDecimal.valueOf(validFlowCount), 4, RoundingMode.HALF_UP));
        }
        if (validPowerCount > 0) {
            averages.put("power", powerSum.divide(BigDecimal.valueOf(validPowerCount), 4, RoundingMode.HALF_UP));
        }
        if (validVibrationCount > 0) {
            averages.put("vibration", vibrationSum.divide(BigDecimal.valueOf(validVibrationCount), 4, RoundingMode.HALF_UP));
        }
        if (validVoltageCount > 0) {
            averages.put("voltage", voltageSum.divide(BigDecimal.valueOf(validVoltageCount), 4, RoundingMode.HALF_UP));
        }
        if (validCurrentCount > 0) {
            averages.put("current", currentSum.divide(BigDecimal.valueOf(validCurrentCount), 4, RoundingMode.HALF_UP));
        }
        
        return averages;
    }
    
    /**
     * 计算最大值
     */
    private Map<String, Object> calculateMaxValues(List<RealTimeData> dataList) {
        Map<String, Object> maxValues = new HashMap<>();
        
        BigDecimal maxWindSpeed = null;
        BigDecimal maxTemperature = null;
        BigDecimal maxPressure = null;
        BigDecimal maxFlow = null;
        BigDecimal maxPower = null;
        BigDecimal maxVibration = null;
        BigDecimal maxVoltage = null;
        BigDecimal maxCurrent = null;
        
        for (RealTimeData data : dataList) {
            if (data.getWindSpeed() != null && (maxWindSpeed == null || data.getWindSpeed().compareTo(maxWindSpeed) > 0)) {
                maxWindSpeed = data.getWindSpeed();
            }
            if (data.getTemperature() != null && (maxTemperature == null || data.getTemperature().compareTo(maxTemperature) > 0)) {
                maxTemperature = data.getTemperature();
            }
            if (data.getPressure() != null && (maxPressure == null || data.getPressure().compareTo(maxPressure) > 0)) {
                maxPressure = data.getPressure();
            }
            if (data.getFlow() != null && (maxFlow == null || data.getFlow().compareTo(maxFlow) > 0)) {
                maxFlow = data.getFlow();
            }
            if (data.getPower() != null && (maxPower == null || data.getPower().compareTo(maxPower) > 0)) {
                maxPower = data.getPower();
            }
            if (data.getVibration() != null && (maxVibration == null || data.getVibration().compareTo(maxVibration) > 0)) {
                maxVibration = data.getVibration();
            }
            if (data.getVoltage() != null && (maxVoltage == null || data.getVoltage().compareTo(maxVoltage) > 0)) {
                maxVoltage = data.getVoltage();
            }
            if (data.getCurrent() != null && (maxCurrent == null || data.getCurrent().compareTo(maxCurrent) > 0)) {
                maxCurrent = data.getCurrent();
            }
        }
        
        if (maxWindSpeed != null) maxValues.put("windSpeed", maxWindSpeed);
        if (maxTemperature != null) maxValues.put("temperature", maxTemperature);
        if (maxPressure != null) maxValues.put("pressure", maxPressure);
        if (maxFlow != null) maxValues.put("flow", maxFlow);
        if (maxPower != null) maxValues.put("power", maxPower);
        if (maxVibration != null) maxValues.put("vibration", maxVibration);
        if (maxVoltage != null) maxValues.put("voltage", maxVoltage);
        if (maxCurrent != null) maxValues.put("current", maxCurrent);
        
        return maxValues;
    }
    
    /**
     * 计算最小值
     */
    private Map<String, Object> calculateMinValues(List<RealTimeData> dataList) {
        Map<String, Object> minValues = new HashMap<>();
        
        BigDecimal minWindSpeed = null;
        BigDecimal minTemperature = null;
        BigDecimal minPressure = null;
        BigDecimal minFlow = null;
        BigDecimal minPower = null;
        BigDecimal minVibration = null;
        BigDecimal minVoltage = null;
        BigDecimal minCurrent = null;
        
        for (RealTimeData data : dataList) {
            if (data.getWindSpeed() != null && (minWindSpeed == null || data.getWindSpeed().compareTo(minWindSpeed) < 0)) {
                minWindSpeed = data.getWindSpeed();
            }
            if (data.getTemperature() != null && (minTemperature == null || data.getTemperature().compareTo(minTemperature) < 0)) {
                minTemperature = data.getTemperature();
            }
            if (data.getPressure() != null && (minPressure == null || data.getPressure().compareTo(minPressure) < 0)) {
                minPressure = data.getPressure();
            }
            if (data.getFlow() != null && (minFlow == null || data.getFlow().compareTo(minFlow) < 0)) {
                minFlow = data.getFlow();
            }
            if (data.getPower() != null && (minPower == null || data.getPower().compareTo(minPower) < 0)) {
                minPower = data.getPower();
            }
            if (data.getVibration() != null && (minVibration == null || data.getVibration().compareTo(minVibration) < 0)) {
                minVibration = data.getVibration();
            }
            if (data.getVoltage() != null && (minVoltage == null || data.getVoltage().compareTo(minVoltage) < 0)) {
                minVoltage = data.getVoltage();
            }
            if (data.getCurrent() != null && (minCurrent == null || data.getCurrent().compareTo(minCurrent) < 0)) {
                minCurrent = data.getCurrent();
            }
        }
        
        if (minWindSpeed != null) minValues.put("windSpeed", minWindSpeed);
        if (minTemperature != null) minValues.put("temperature", minTemperature);
        if (minPressure != null) minValues.put("pressure", minPressure);
        if (minFlow != null) minValues.put("flow", minFlow);
        if (minPower != null) minValues.put("power", minPower);
        if (minVibration != null) minValues.put("vibration", minVibration);
        if (minVoltage != null) minValues.put("voltage", minVoltage);
        if (minCurrent != null) minValues.put("current", minCurrent);
        
        return minValues;
    }
    
    /**
     * 计算标准差
     */
    private Map<String, Object> calculateStandardDeviation(List<RealTimeData> dataList, Map<String, Object> averages) {
        Map<String, Object> stdDev = new HashMap<>();
        
        if (dataList.isEmpty() || averages.isEmpty()) {
            return stdDev;
        }
        
        BigDecimal avgWindSpeed = (BigDecimal) averages.get("windSpeed");
        BigDecimal avgTemperature = (BigDecimal) averages.get("temperature");
        BigDecimal avgPressure = (BigDecimal) averages.get("pressure");
        BigDecimal avgFlow = (BigDecimal) averages.get("flow");
        BigDecimal avgPower = (BigDecimal) averages.get("power");
        BigDecimal avgVibration = (BigDecimal) averages.get("vibration");
        BigDecimal avgVoltage = (BigDecimal) averages.get("voltage");
        BigDecimal avgCurrent = (BigDecimal) averages.get("current");
        
        BigDecimal windSpeedVariance = BigDecimal.ZERO;
        BigDecimal temperatureVariance = BigDecimal.ZERO;
        BigDecimal pressureVariance = BigDecimal.ZERO;
        BigDecimal flowVariance = BigDecimal.ZERO;
        BigDecimal powerVariance = BigDecimal.ZERO;
        BigDecimal vibrationVariance = BigDecimal.ZERO;
        BigDecimal voltageVariance = BigDecimal.ZERO;
        BigDecimal currentVariance = BigDecimal.ZERO;
        
        int validWindSpeedCount = 0;
        int validTemperatureCount = 0;
        int validPressureCount = 0;
        int validFlowCount = 0;
        int validPowerCount = 0;
        int validVibrationCount = 0;
        int validVoltageCount = 0;
        int validCurrentCount = 0;
        
        for (RealTimeData data : dataList) {
            if (data.getWindSpeed() != null && avgWindSpeed != null) {
                BigDecimal diff = data.getWindSpeed().subtract(avgWindSpeed);
                windSpeedVariance = windSpeedVariance.add(diff.multiply(diff));
                validWindSpeedCount++;
            }
            if (data.getTemperature() != null && avgTemperature != null) {
                BigDecimal diff = data.getTemperature().subtract(avgTemperature);
                temperatureVariance = temperatureVariance.add(diff.multiply(diff));
                validTemperatureCount++;
            }
            if (data.getPressure() != null && avgPressure != null) {
                BigDecimal diff = data.getPressure().subtract(avgPressure);
                pressureVariance = pressureVariance.add(diff.multiply(diff));
                validPressureCount++;
            }
            if (data.getFlow() != null && avgFlow != null) {
                BigDecimal diff = data.getFlow().subtract(avgFlow);
                flowVariance = flowVariance.add(diff.multiply(diff));
                validFlowCount++;
            }
            if (data.getPower() != null && avgPower != null) {
                BigDecimal diff = data.getPower().subtract(avgPower);
                powerVariance = powerVariance.add(diff.multiply(diff));
                validPowerCount++;
            }
            if (data.getVibration() != null && avgVibration != null) {
                BigDecimal diff = data.getVibration().subtract(avgVibration);
                vibrationVariance = vibrationVariance.add(diff.multiply(diff));
                validVibrationCount++;
            }
            if (data.getVoltage() != null && avgVoltage != null) {
                BigDecimal diff = data.getVoltage().subtract(avgVoltage);
                voltageVariance = voltageVariance.add(diff.multiply(diff));
                validVoltageCount++;
            }
            if (data.getCurrent() != null && avgCurrent != null) {
                BigDecimal diff = data.getCurrent().subtract(avgCurrent);
                currentVariance = currentVariance.add(diff.multiply(diff));
                validCurrentCount++;
            }
        }
        
        if (validWindSpeedCount > 1) {
            stdDev.put("windSpeed", sqrt(windSpeedVariance.divide(BigDecimal.valueOf(validWindSpeedCount - 1), 4, RoundingMode.HALF_UP)));
        }
        if (validTemperatureCount > 1) {
            stdDev.put("temperature", sqrt(temperatureVariance.divide(BigDecimal.valueOf(validTemperatureCount - 1), 4, RoundingMode.HALF_UP)));
        }
        if (validPressureCount > 1) {
            stdDev.put("pressure", sqrt(pressureVariance.divide(BigDecimal.valueOf(validPressureCount - 1), 4, RoundingMode.HALF_UP)));
        }
        if (validFlowCount > 1) {
            stdDev.put("flow", sqrt(flowVariance.divide(BigDecimal.valueOf(validFlowCount - 1), 4, RoundingMode.HALF_UP)));
        }
        if (validPowerCount > 1) {
            stdDev.put("power", sqrt(powerVariance.divide(BigDecimal.valueOf(validPowerCount - 1), 4, RoundingMode.HALF_UP)));
        }
        if (validVibrationCount > 1) {
            stdDev.put("vibration", sqrt(vibrationVariance.divide(BigDecimal.valueOf(validVibrationCount - 1), 4, RoundingMode.HALF_UP)));
        }
        if (validVoltageCount > 1) {
            stdDev.put("voltage", sqrt(voltageVariance.divide(BigDecimal.valueOf(validVoltageCount - 1), 4, RoundingMode.HALF_UP)));
        }
        if (validCurrentCount > 1) {
            stdDev.put("current", sqrt(currentVariance.divide(BigDecimal.valueOf(validCurrentCount - 1), 4, RoundingMode.HALF_UP)));
        }
        
        return stdDev;
    }
    
    /**
     * 计算平方根
     */
    private BigDecimal sqrt(BigDecimal value) {
        return new BigDecimal(Math.sqrt(value.doubleValue()));
    }
    
    /**
     * 检查数据完整性
     */
    private boolean checkDataCompleteness(RealTimeData realTimeData) {
        // 检查关键字段是否存在
        return realTimeData.getSource() != null && 
               realTimeData.getDataTime() != null;
    }
    
    /**
     * 检查数据一致性
     */
    private boolean checkDataConsistency(RealTimeData realTimeData) {
        // 检查数据逻辑一致性
        // 例如：压力和温度之间可能存在某种关系
        return true; // 简化实现
    }
    
    /**
     * 检查数据准确性
     */
    private boolean checkDataAccuracy(RealTimeData realTimeData) {
        // 检查数据是否在合理范围内
        return detectAnomalyByRules(realTimeData).getData() == false;
    }
    
    /**
     * 检查数据时效性
     */
    private boolean checkDataTimeliness(RealTimeData realTimeData) {
        // 检查数据时间戳是否在合理范围内
        if (realTimeData.getDataTime() == null) {
            return false;
        }
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dataTime = realTimeData.getDataTime();
        
        // 数据时间不应超过当前时间太多，也不应太早
        return !dataTime.isAfter(now.plusMinutes(1)) && !dataTime.isBefore(now.minusHours(24));
    }
    
    /**
     * 更新风险等级
     */
    private void updateRiskLevel(RealTimeData realTimeData, Map<String, Object> qualityMetrics, 
                                Map<String, Object> thresholdResults) {
        int riskLevel = 1; // 默认一般风险
        
        // 根据质量指标和阈值结果更新风险等级
        boolean hasQualityIssues = !((Boolean) qualityMetrics.getOrDefault("accuracy", true));
        boolean hasThresholdAlerts = thresholdResults.values().stream()
                .anyMatch(value -> value instanceof Boolean && (Boolean) value);
        
        if (hasQualityIssues || hasThresholdAlerts) {
            riskLevel = 2; // 较重风险
        }
        
        // 如果状态是异常，风险等级设为严重
        if (realTimeData.getStatus() != null && realTimeData.getStatus() == 1) {
            riskLevel = 3; // 严重风险
        }
        
        realTimeData.setRiskLevel(riskLevel);
    }
    
    /**
     * 执行趋势分析
     */
    private Map<String, Object> performTrendAnalysis(List<RealTimeData> dataList) {
        Map<String, Object> trendAnalysis = new HashMap<>();
        
        if (dataList.size() < 2) {
            return trendAnalysis;
        }
        
        // 计算各参数的趋势
        Map<String, Object> windSpeedTrend = calculateTrend(
            dataList.stream().map(RealTimeData::getWindSpeed).collect(Collectors.toList()));
        trendAnalysis.put("windSpeedTrend", windSpeedTrend);
        
        Map<String, Object> temperatureTrend = calculateTrend(
            dataList.stream().map(RealTimeData::getTemperature).collect(Collectors.toList()));
        trendAnalysis.put("temperatureTrend", temperatureTrend);
        
        Map<String, Object> pressureTrend = calculateTrend(
            dataList.stream().map(RealTimeData::getPressure).collect(Collectors.toList()));
        trendAnalysis.put("pressureTrend", pressureTrend);
        
        return trendAnalysis;
    }
    
    /**
     * 计算趋势
     */
    private Map<String, Object> calculateTrend(List<BigDecimal> values) {
        Map<String, Object> trend = new HashMap<>();
        
        // 过滤掉空值
        List<BigDecimal> nonNullValues = values.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        
        if (nonNullValues.size() < 2) {
            trend.put("direction", "insufficient_data");
            trend.put("rate", 0);
            return trend;
        }
        
        // 简单线性趋势计算（使用首尾值）
        BigDecimal firstValue = nonNullValues.get(0);
        BigDecimal lastValue = nonNullValues.get(nonNullValues.size() - 1);
        
        BigDecimal change = lastValue.subtract(firstValue);
        double rate = change.doubleValue() / firstValue.doubleValue() * 100;
        
        String direction;
        if (change.compareTo(BigDecimal.ZERO) > 0) {
            direction = "increasing";
        } else if (change.compareTo(BigDecimal.ZERO) < 0) {
            direction = "decreasing";
        } else {
            direction = "stable";
        }
        
        trend.put("direction", direction);
        trend.put("rate", rate);
        trend.put("change", change);
        
        return trend;
    }
}