package com.windtunnel.factory;

import com.windtunnel.strategy.DataParsingStrategy;
import com.windtunnel.strategy.impl.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据解析策略工厂
 * 负责创建和管理不同的数据解析策略
 */
public class DataParsingStrategyFactory {
    
    private static final List<DataParsingStrategy> strategies = new ArrayList<>();
    
    static {
        // 注册所有数据解析策略
        strategies.add(new Cwt1PcDataParsingStrategy());
        strategies.add(new Cwt2PcDataParsingStrategy());
        strategies.add(new Cwt3PcDataParsingStrategy());
        strategies.add(new AawtPcDataParsingStrategy());
        strategies.add(new PublicPowerSystemPcDataParsingStrategy());
    }
    
    /**
     * 根据客户端地址获取适用的数据解析策略
     * @param clientAddress 客户端地址
     * @return 适用的数据解析策略，如果找不到则返回null
     */
    public static DataParsingStrategy getStrategy(String clientAddress) {
        for (DataParsingStrategy strategy : strategies) {
            if (strategy.isApplicable(clientAddress)) {
                return strategy;
            }
        }
        return null;
    }
    
    /**
     * 获取所有策略
     * @return 所有策略列表
     */
    public static List<DataParsingStrategy> getAllStrategies() {
        return new ArrayList<>(strategies);
    }
}