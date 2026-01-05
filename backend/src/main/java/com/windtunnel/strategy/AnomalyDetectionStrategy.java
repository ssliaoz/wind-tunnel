package com.windtunnel.strategy;

import com.windtunnel.entity.RealTimeData;

/**
 * 异常检测策略接口
 * 定义不同类型的异常检测策略
 */
public interface AnomalyDetectionStrategy {
    
    /**
     * 检测异常
     * @param realTimeData 实时数据
     * @return 是否检测到异常
     */
    boolean detectAnomaly(RealTimeData realTimeData);
    
    /**
     * 获取异常描述
     * @param realTimeData 实时数据
     * @return 异常描述
     */
    String getAnomalyDescription(RealTimeData realTimeData);
    
    /**
     * 获取策略名称
     * @return 策略名称
     */
    String getStrategyName();
}