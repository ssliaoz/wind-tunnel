package com.windtunnel.service;

import com.windtunnel.entity.RealTimeData;
import com.windtunnel.common.Result;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 数据采集服务接口
 * 
 * 提供实时数据采集、处理和存储相关的业务逻辑处理方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
public interface DataCollectionService {

    /**
     * 保存实时数据
     * 
     * @param realTimeData 实时数据对象
     * @return 保存结果
     */
    Result<String> saveRealTimeData(RealTimeData realTimeData);

    /**
     * 根据数据来源查询实时数据列表
     * 
     * @param source 数据来源
     * @return 实时数据列表
     */
    Result<List<RealTimeData>> findBySource(String source);

    /**
     * 根据设备ID查询实时数据列表
     * 
     * @param equipmentId 设备ID
     * @return 实时数据列表
     */
    Result<List<RealTimeData>> findByEquipmentId(Long equipmentId);

    /**
     * 根据时间范围查询实时数据列表
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 实时数据列表
     */
    Result<List<RealTimeData>> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据数据来源和时间范围查询实时数据列表
     * 
     * @param source 数据来源
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 实时数据列表
     */
    Result<List<RealTimeData>> findBySourceAndTimeRange(String source, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取最新的实时数据
     * 
     * @param source 数据来源
     * @return 最新实时数据
     */
    Result<RealTimeData> getLatestDataBySource(String source);

    /**
     * 根据设备ID获取最新的实时数据
     * 
     * @param equipmentId 设备ID
     * @return 最新实时数据
     */
    Result<RealTimeData> getLatestDataByEquipmentId(Long equipmentId);

    /**
     * 根据时间范围删除实时数据
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 删除结果
     */
    Result<Boolean> deleteByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 检查数据异常
     * 
     * @param realTimeData 实时数据
     * @return 异常检测结果
     */
    Result<Boolean> checkForAnomalies(RealTimeData realTimeData);

    /**
     * 触发告警
     * 
     * @param realTimeData 实时数据
     * @return 告警触发结果
     */
    Result<Boolean> triggerAlert(RealTimeData realTimeData);

}