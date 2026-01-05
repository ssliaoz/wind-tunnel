package com.windtunnel.command;

/**
 * 命令接口
 * 定义命令需要实现的方法
 */
public interface Command {
    
    /**
     * 执行命令
     */
    void execute();
    
    /**
     * 撤销命令
     */
    void undo();
}