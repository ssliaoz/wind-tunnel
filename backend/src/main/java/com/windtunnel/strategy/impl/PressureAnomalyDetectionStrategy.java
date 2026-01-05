package com.windtunnel.strategy.impl;

import com.windtunnel.entity.RealTimeData;
import com.windtunnel.strategy.AnomalyDetectionStrategy;

/**
 * 压力异常检测策略
 */
public class PressureAnomalyDetectionStrategy implements AnomalyDetectionStrategy {

    @Override
    public boolean detectAnomaly(RealTimeData realTimeData) {
        if (realTimeData.getPressure() != null) {
            // 检查压力是否超出正常范围（例如50到200）
            return realTimeData.getPressure().compareTo(new java.math.BigDecimal("50")) < 0 ||
                   realTimeData.getPressure().compareTo(new java.math.BigDecimal("200")) > 0;
        }
        return false;
    }

    @Override
    public String getAnomalyDescription(RealTimeData realTimeData) {
        if (realTimeData.getPressure() != null) {
            return "压力异常: " + realTimeData.getPressure();
        }
        return "压力异常: 未知值";
    }

    @Override
    public String getStrategyName() {
        return "PressureAnomalyDetection";
    }
}