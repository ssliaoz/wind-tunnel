package com.windtunnel.strategy.impl;

import com.windtunnel.entity.RealTimeData;
import com.windtunnel.strategy.AnomalyDetectionStrategy;

/**
 * 温度异常检测策略
 */
public class TemperatureAnomalyDetectionStrategy implements AnomalyDetectionStrategy {

    @Override
    public boolean detectAnomaly(RealTimeData realTimeData) {
        if (realTimeData.getTemperature() != null) {
            // 检查温度是否超出正常范围（例如-50到100度）
            return realTimeData.getTemperature().compareTo(new java.math.BigDecimal("-50")) < 0 ||
                   realTimeData.getTemperature().compareTo(new java.math.BigDecimal("100")) > 0;
        }
        return false;
    }

    @Override
    public String getAnomalyDescription(RealTimeData realTimeData) {
        if (realTimeData.getTemperature() != null) {
            return "温度异常: " + realTimeData.getTemperature();
        }
        return "温度异常: 未知值";
    }

    @Override
    public String getStrategyName() {
        return "TemperatureAnomalyDetection";
    }
}