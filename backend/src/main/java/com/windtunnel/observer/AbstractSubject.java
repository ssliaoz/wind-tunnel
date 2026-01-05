package com.windtunnel.observer;

import java.util.List;
import java.util.ArrayList;

/**
 * 被观察者抽象实现类
 * 提供被观察者的基本实现
 */
public abstract class AbstractSubject implements Subject {
    
    protected List<Observer> observers = new ArrayList<>();
    
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyObservers() {
        notifyObservers(null);
    }
    
    @Override
    public void notifyObservers(Object data) {
        for (Observer observer : observers) {
            observer.update(this, data);
        }
    }
}