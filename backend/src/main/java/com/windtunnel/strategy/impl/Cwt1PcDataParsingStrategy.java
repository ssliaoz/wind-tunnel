package com.windtunnel.strategy.impl;

import com.windtunnel.entity.RealTimeData;
import com.windtunnel.strategy.DataParsingStrategy;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * CWT1 PC数据解析策略
 */
public class Cwt1PcDataParsingStrategy implements DataParsingStrategy {

    @Override
    public RealTimeData parseData(String rawData, String clientAddress) {
        RealTimeData realTimeData = new RealTimeData();
        realTimeData.setSource("CWT1_PC");

        // 解析数据内容
        Map<String, Object> dataContent = new HashMap<>();
        
        // 假设数据格式为: "WIND_SPEED:10.5,TEMP:25.3,PRESSURE:101.3"
        String[] pairs = rawData.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                
                // 根据键名设置对应的值
                switch (key.toUpperCase()) {
                    case "WIND_SPEED":
                        realTimeData.setWindSpeed(new java.math.BigDecimal(value));
                        break;
                    case "TEMP":
                        realTimeData.setTemperature(new java.math.BigDecimal(value));
                        break;
                    case "PRESSURE":
                        realTimeData.setPressure(new java.math.BigDecimal(value));
                        break;
                    case "FLOW":
                        realTimeData.setFlow(new java.math.BigDecimal(value));
                        break;
                    case "POWER":
                        realTimeData.setPower(new java.math.BigDecimal(value));
                        break;
                    case "VIBRATION":
                        realTimeData.setVibration(new java.math.BigDecimal(value));
                        break;
                    case "VOLTAGE":
                        realTimeData.setVoltage(new java.math.BigDecimal(value));
                        break;
                    case "CURRENT":
                        realTimeData.setCurrent(new java.math.BigDecimal(value));
                        break;
                    default:
                        // 其他参数存储在dataContent中
                        dataContent.put(key, value);
                        break;
                }
            }
        }
        
        realTimeData.setDataContent(dataContent);
        realTimeData.setDataTime(LocalDateTime.now());
        realTimeData.setStatus(0); // 设置默认状态为正常
        
        return realTimeData;
    }

    @Override
    public boolean isApplicable(String clientAddress) {
        return clientAddress.contains("101") || clientAddress.contains("CWT1");
    }
}