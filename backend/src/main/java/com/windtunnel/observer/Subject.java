package com.windtunnel.observer;

import java.util.List;
import java.util.ArrayList;

/**
 * 被观察者接口
 * 定义被观察者需要实现的方法
 */
public interface Subject {
    
    /**
     * 添加观察者
     * @param observer 观察者
     */
    void addObserver(Observer observer);
    
    /**
     * 移除观察者
     * @param observer 观察者
     */
    void removeObserver(Observer observer);
    
    /**
     * 通知所有观察者
     */
    void notifyObservers();
    
    /**
     * 通知所有观察者（带数据）
     * @param data 传递的数据
     */
    void notifyObservers(Object data);
}