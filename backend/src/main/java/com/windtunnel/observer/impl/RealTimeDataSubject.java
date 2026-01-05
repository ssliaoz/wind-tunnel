package com.windtunnel.observer.impl;

import com.windtunnel.entity.RealTimeData;
import com.windtunnel.observer.AbstractSubject;

/**
 * 实时数据主题
 * 当实时数据状态发生变化时通知观察者
 */
public class RealTimeDataSubject extends AbstractSubject {
    
    private RealTimeData realTimeData;
    
    public RealTimeDataSubject(RealTimeData realTimeData) {
        this.realTimeData = realTimeData;
    }
    
    public void updateRealTimeData(RealTimeData realTimeData) {
        this.realTimeData = realTimeData;
        // 通知所有观察者数据已更新
        notifyObservers(realTimeData);
    }
    
    public RealTimeData getRealTimeData() {
        return realTimeData;
    }
}