package com.windtunnel.strategy.impl;

import com.windtunnel.entity.RealTimeData;
import com.windtunnel.strategy.AnomalyDetectionStrategy;

/**
 * 风速异常检测策略
 */
public class WindSpeedAnomalyDetectionStrategy implements AnomalyDetectionStrategy {

    @Override
    public boolean detectAnomaly(RealTimeData realTimeData) {
        if (realTimeData.getWindSpeed() != null) {
            // 检查风速是否超过阈值（例如150）
            return realTimeData.getWindSpeed().compareTo(new java.math.BigDecimal("150")) > 0;
        }
        return false;
    }

    @Override
    public String getAnomalyDescription(RealTimeData realTimeData) {
        if (realTimeData.getWindSpeed() != null) {
            return "风速异常: " + realTimeData.getWindSpeed();
        }
        return "风速异常: 未知值";
    }

    @Override
    public String getStrategyName() {
        return "WindSpeedAnomalyDetection";
    }
}