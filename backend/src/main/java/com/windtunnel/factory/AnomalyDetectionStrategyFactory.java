package com.windtunnel.factory;

import com.windtunnel.strategy.AnomalyDetectionStrategy;
import com.windtunnel.strategy.impl.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 异常检测策略工厂
 * 负责创建和管理不同的异常检测策略
 */
public class AnomalyDetectionStrategyFactory {
    
    private static final List<AnomalyDetectionStrategy> strategies = new ArrayList<>();
    
    static {
        // 注册所有异常检测策略
        strategies.add(new WindSpeedAnomalyDetectionStrategy());
        strategies.add(new TemperatureAnomalyDetectionStrategy());
        strategies.add(new PressureAnomalyDetectionStrategy());
    }
    
    /**
     * 获取所有异常检测策略
     * @return 所有策略列表
     */
    public static List<AnomalyDetectionStrategy> getAllStrategies() {
        return new ArrayList<>(strategies);
    }
    
    /**
     * 获取特定类型的异常检测策略
     * @param strategyName 策略名称
     * @return 对应的异常检测策略，如果找不到则返回null
     */
    public static AnomalyDetectionStrategy getStrategyByName(String strategyName) {
        for (AnomalyDetectionStrategy strategy : strategies) {
            if (strategy.getStrategyName().equals(strategyName)) {
                return strategy;
            }
        }
        return null;
    }
}