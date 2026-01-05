package com.windtunnel.command.impl;

import com.windtunnel.command.Command;
import com.windtunnel.entity.RealTimeData;
import com.windtunnel.repository.RealTimeDataRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * 保存实时数据命令
 */
@Slf4j
public class SaveRealTimeDataCommand implements Command {
    
    private final RealTimeDataRepository repository;
    private final RealTimeData realTimeData;
    private RealTimeData savedData;
    
    public SaveRealTimeDataCommand(RealTimeDataRepository repository, RealTimeData realTimeData) {
        this.repository = repository;
        this.realTimeData = realTimeData;
    }
    
    @Override
    public void execute() {
        log.info("执行保存实时数据命令，数据ID: {}", realTimeData.getId());
        savedData = repository.save(realTimeData);
        log.info("实时数据保存成功，数据ID: {}", savedData.getId());
    }
    
    @Override
    public void undo() {
        if (savedData != null) {
            log.info("撤销保存实时数据命令，删除数据ID: {}", savedData.getId());
            repository.deleteById(savedData.getId());
            log.info("实时数据已删除，数据ID: {}", savedData.getId());
        }
    }
}