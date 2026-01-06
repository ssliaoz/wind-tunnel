package com.windtunnel.service;

import com.windtunnel.entity.RealTimeData;
import com.windtunnel.common.Result;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.lang.NonNull;

/**
 * 批量处理服务接口
 * 
 * 提供批量处理相关功能，用于优化系统性能
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
public interface BatchProcessingService {

    /**
     * 批量保存实时数据
     * 
     * @param realTimeDataList 实时数据列表
     * @return 批量保存结果
     */
    Result<Integer> batchSaveRealTimeData(@NonNull List<RealTimeData> realTimeDataList);

    /**
     * 批量更新实时数据
     * 
     * @param realTimeDataList 实时数据列表
     * @return 批量更新结果
     */
    Result<Integer> batchUpdateRealTimeData(@NonNull List<RealTimeData> realTimeDataList);

    /**
     * 批量删除实时数据
     * 
     * @param ids 数据ID列表
     * @return 批量删除结果
     */
    Result<Integer> batchDeleteRealTimeData(@NonNull List<String> ids);

    /**
     * 批量查询实时数据
     * 
     * @param ids 数据ID列表
     * @return 批量查询结果
     */
    Result<List<RealTimeData>> batchQueryRealTimeData(@NonNull List<String> ids);

    /**
     * 批量按时间范围删除数据
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 删除结果
     */
    Result<Integer> batchDeleteByTimeRange(@NonNull LocalDateTime startTime, @NonNull LocalDateTime endTime);

    /**
     * 批量按数据源删除数据
     * 
     * @param sources 数据源列表
     * @return 删除结果
     */
    Result<Integer> batchDeleteBySources(@NonNull List<String> sources);

    /**
     * 批量处理实时数据（包含验证、清洗、转换等）
     * 
     * @param realTimeDataList 实时数据列表
     * @return 批量处理结果
     */
    Result<List<RealTimeData>> batchProcessRealTimeData(@NonNull List<RealTimeData> realTimeDataList);
}