package com.windtunnel.strategy;

import com.windtunnel.entity.RealTimeData;

/**
 * 数据解析策略接口
 * 定义不同数据源的数据解析策略
 */
public interface DataParsingStrategy {
    
    /**
     * 解析数据
     * @param rawData 原始数据
     * @param clientAddress 客户端地址
     * @return 解析后的实时数据对象
     */
    RealTimeData parseData(String rawData, String clientAddress);
    
    /**
     * 检查该策略是否适用于指定的数据源
     * @param clientAddress 客户端地址
     * @return 是否适用
     */
    boolean isApplicable(String clientAddress);
}