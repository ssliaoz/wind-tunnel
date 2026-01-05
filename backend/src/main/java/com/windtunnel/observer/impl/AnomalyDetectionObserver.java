package com.windtunnel.observer.impl;

import com.windtunnel.entity.RealTimeData;
import com.windtunnel.observer.Observer;
import com.windtunnel.observer.Subject;
import lombok.extern.slf4j.Slf4j;

/**
 * 异常检测观察者
 * 当实时数据更新时，检测是否存在异常
 */
@Slf4j
public class AnomalyDetectionObserver implements Observer {
    
    @Override
    public void update(Subject subject, Object data) {
        if (data instanceof RealTimeData) {
            RealTimeData realTimeData = (RealTimeData) data;
            
            // 检测异常
            boolean hasAnomaly = detectAnomalies(realTimeData);
            
            if (hasAnomaly) {
                log.warn("检测到实时数据异常: {}", realTimeData);
                // 触发告警
                triggerAlert(realTimeData);
            }
        }
    }
    
    /**
     * 检测数据中的异常
     * @param realTimeData 实时数据
     * @return 是否存在异常
     */
    private boolean detectAnomalies(RealTimeData realTimeData) {
        boolean hasAnomaly = false;
        
        // 检查风速是否异常
        if (realTimeData.getWindSpeed() != null && 
            realTimeData.getWindSpeed().compareTo(new java.math.BigDecimal("150")) > 0) {
            hasAnomaly = true;
        }
        
        // 检查温度是否异常
        if (realTimeData.getTemperature() != null && 
            (realTimeData.getTemperature().compareTo(new java.math.BigDecimal("-50")) < 0 ||
             realTimeData.getTemperature().compareTo(new java.math.BigDecimal("100")) > 0)) {
            hasAnomaly = true;
        }
        
        // 检查压力是否异常
        if (realTimeData.getPressure() != null && 
            (realTimeData.getPressure().compareTo(new java.math.BigDecimal("50")) < 0 ||
             realTimeData.getPressure().compareTo(new java.math.BigDecimal("200")) > 0)) {
            hasAnomaly = true;
        }
        
        return hasAnomaly;
    }
    
    /**
     * 触发告警
     * @param realTimeData 实时数据
     */
    private void triggerAlert(RealTimeData realTimeData) {
        log.warn("触发告警，设备: {}, 异常: 数据超出正常范围", realTimeData.getSource());
        // 在实际应用中，可以发送邮件、短信或站内信等
    }
}