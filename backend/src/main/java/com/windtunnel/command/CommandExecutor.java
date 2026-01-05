package com.windtunnel.command;

import java.util.Stack;

/**
 * 命令执行器
 * 负责执行命令和管理命令历史
 */
public class CommandExecutor {
    
    private final Stack<Command> commandHistory = new Stack<>();
    
    /**
     * 执行命令
     * @param command 要执行的命令
     */
    public void executeCommand(Command command) {
        command.execute();
        commandHistory.push(command);
    }
    
    /**
     * 撤销上一个命令
     */
    public void undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.pop();
            lastCommand.undo();
        }
    }
    
    /**
     * 清空命令历史
     */
    public void clearHistory() {
        commandHistory.clear();
    }
    
    /**
     * 获取命令历史大小
     * @return 命令历史大小
     */
    public int getHistorySize() {
        return commandHistory.size();
    }
}