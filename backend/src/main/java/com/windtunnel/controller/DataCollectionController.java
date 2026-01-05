package com.windtunnel.controller;

import com.windtunnel.common.Result;
import com.windtunnel.entity.RealTimeData;
import com.windtunnel.service.DataCollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 数据采集控制器
 * 
 * 提供实时数据采集相关的REST API接口
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/api/data-collection")
public class DataCollectionController {

    @Autowired
    private DataCollectionService dataCollectionService;

    /**
     * 保存实时数据
     * 
     * @param realTimeData 实时数据对象
     * @return 保存结果
     */
    @PostMapping("/save")
    public Result<String> saveRealTimeData(@RequestBody RealTimeData realTimeData) {
        log.info("保存实时数据请求，数据来源: {}", realTimeData.getSource());
        return dataCollectionService.saveRealTimeData(realTimeData);
    }

    /**
     * 根据数据来源查询实时数据列表
     * 
     * @param source 数据来源
     * @return 实时数据列表
     */
    @GetMapping("/source/{source}")
    public Result<List<RealTimeData>> getRealTimeDataBySource(@PathVariable String source) {
        log.info("根据数据来源查询实时数据请求，来源: {}", source);
        return dataCollectionService.findBySource(source);
    }

    /**
     * 根据设备ID查询实时数据列表
     * 
     * @param equipmentId 设备ID
     * @return 实时数据列表
     */
    @GetMapping("/equipment/{equipmentId}")
    public Result<List<RealTimeData>> getRealTimeDataByEquipmentId(@PathVariable Long equipmentId) {
        log.info("根据设备ID查询实时数据请求，设备ID: {}", equipmentId);
        return dataCollectionService.findByEquipmentId(equipmentId);
    }

    /**
     * 根据时间范围查询实时数据列表
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 实时数据列表
     */
    @GetMapping("/time-range")
    public Result<List<RealTimeData>> getRealTimeDataByTimeRange(@RequestParam LocalDateTime startTime, 
                                                              @RequestParam LocalDateTime endTime) {
        log.info("根据时间范围查询实时数据请求，开始时间: {}, 结束时间: {}", startTime, endTime);
        return dataCollectionService.findByTimeRange(startTime, endTime);
    }

    /**
     * 根据数据来源和时间范围查询实时数据列表
     * 
     * @param source 数据来源
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 实时数据列表
     */
    @GetMapping("/source-time-range/{source}")
    public Result<List<RealTimeData>> getRealTimeDataBySourceAndTimeRange(@PathVariable String source,
                                                                        @RequestParam LocalDateTime startTime, 
                                                                        @RequestParam LocalDateTime endTime) {
        log.info("根据数据来源和时间范围查询实时数据请求，来源: {}, 开始时间: {}, 结束时间: {}", 
                source, startTime, endTime);
        return dataCollectionService.findBySourceAndTimeRange(source, startTime, endTime);
    }

    /**
     * 获取最新的实时数据
     * 
     * @param source 数据来源
     * @return 最新实时数据
     */
    @GetMapping("/latest/{source}")
    public Result<RealTimeData> getLatestRealTimeData(@PathVariable String source) {
        log.info("获取最新实时数据请求，来源: {}", source);
        return dataCollectionService.getLatestDataBySource(source);
    }

    /**
     * 根据设备ID获取最新的实时数据
     * 
     * @param equipmentId 设备ID
     * @return 最新实时数据
     */
    @GetMapping("/latest/equipment/{equipmentId}")
    public Result<RealTimeData> getLatestRealTimeDataByEquipmentId(@PathVariable Long equipmentId) {
        log.info("根据设备ID获取最新实时数据请求，设备ID: {}", equipmentId);
        return dataCollectionService.getLatestDataByEquipmentId(equipmentId);
    }

    /**
     * 检查数据异常
     * 
     * @param realTimeData 实时数据对象
     * @return 异常检测结果
     */
    @PostMapping("/check-anomalies")
    public Result<Boolean> checkForAnomalies(@RequestBody RealTimeData realTimeData) {
        log.info("检查数据异常请求，数据ID: {}", realTimeData.getId());
        return dataCollectionService.checkForAnomalies(realTimeData);
    }

}