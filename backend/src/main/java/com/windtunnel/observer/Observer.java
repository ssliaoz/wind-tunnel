package com.windtunnel.observer;

/**
 * 观察者接口
 * 定义观察者需要实现的方法
 */
public interface Observer {
    
    /**
     * 更新方法，当被观察对象状态发生变化时调用
     * @param subject 被观察对象
     * @param data 传递的数据
     */
    void update(Subject subject, Object data);
}