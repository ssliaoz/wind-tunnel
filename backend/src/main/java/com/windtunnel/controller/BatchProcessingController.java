package com.windtunnel.controller;

import com.windtunnel.common.Result;
import com.windtunnel.entity.RealTimeData;
import com.windtunnel.service.BatchProcessingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.lang.NonNull;

/**
 * 批量处理控制器
 * 
 * 提供批量处理相关的REST API接口
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/api/batch-processing")
@Tag(name = "批量处理服务", description = "提供批量数据处理功能")
public class BatchProcessingController {

    @Autowired
    private BatchProcessingService batchProcessingService;

    /**
     * 批量保存实时数据
     */
    @PostMapping("/realtime-data/save")
    @Operation(summary = "批量保存实时数据", description = "批量保存实时数据以提高性能")
    @SuppressWarnings("null")
    public Result<Integer> batchSaveRealTimeData(@NonNull @RequestBody List<RealTimeData> realTimeDataList) {
        log.info("批量保存实时数据请求，数据数量: {}", realTimeDataList != null ? realTimeDataList.size() : 0);
        return batchProcessingService.batchSaveRealTimeData(realTimeDataList != null ? realTimeDataList : java.util.Collections.emptyList());
    }

    /**
     * 批量更新实时数据
     */
    @PutMapping("/realtime-data/update")
    @Operation(summary = "批量更新实时数据", description = "批量更新实时数据以提高性能")
    @SuppressWarnings("null")
    public Result<Integer> batchUpdateRealTimeData(@NonNull @RequestBody List<RealTimeData> realTimeDataList) {
        log.info("批量更新实时数据请求，数据数量: {}", realTimeDataList != null ? realTimeDataList.size() : 0);
        return batchProcessingService.batchUpdateRealTimeData(realTimeDataList != null ? realTimeDataList : java.util.Collections.emptyList());
    }

    /**
     * 批量删除实时数据
     */
    @DeleteMapping("/realtime-data/delete")
    @Operation(summary = "批量删除实时数据", description = "根据ID列表批量删除实时数据")
    @SuppressWarnings("null")
    public Result<Integer> batchDeleteRealTimeData(@NonNull @RequestBody List<String> ids) {
        log.info("批量删除实时数据请求，ID数量: {}", ids != null ? ids.size() : 0);
        return batchProcessingService.batchDeleteRealTimeData(ids != null ? ids : java.util.Collections.emptyList());
    }

    /**
     * 批量查询实时数据
     */
    @PostMapping("/realtime-data/query")
    @Operation(summary = "批量查询实时数据", description = "根据ID列表批量查询实时数据")
    @SuppressWarnings("null")
    public Result<List<RealTimeData>> batchQueryRealTimeData(@NonNull @RequestBody List<String> ids) {
        log.info("批量查询实时数据请求，ID数量: {}", ids != null ? ids.size() : 0);
        Result<List<RealTimeData>> result = batchProcessingService.batchQueryRealTimeData(ids != null ? ids : java.util.Collections.emptyList());
        // 确保返回结果中的数据列表不为null
        if (result.getData() == null) {
            result.setData(java.util.Collections.emptyList());
        }
        return result;
    }

    /**
     * 批量按时间范围删除数据
     */
    @DeleteMapping("/realtime-data/delete-by-timerange")
    @Operation(summary = "按时间范围批量删除", description = "按时间范围批量删除实时数据")
    @SuppressWarnings("null")
    public Result<Integer> batchDeleteByTimeRange(
            @RequestParam String startTime,
            @RequestParam String endTime) {
        log.info("按时间范围批量删除请求，时间范围: {} - {}", startTime, endTime);
        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);
        return batchProcessingService.batchDeleteByTimeRange(start, end);
    }

    /**
     * 批量按数据源删除数据
     */
    @DeleteMapping("/realtime-data/delete-by-sources")
    @Operation(summary = "按数据源批量删除", description = "按数据源批量删除实时数据")
    @SuppressWarnings("null")
    public Result<Integer> batchDeleteBySources(@NonNull @RequestBody List<String> sources) {
        log.info("按数据源批量删除请求，数据源数量: {}", sources != null ? sources.size() : 0);
        return batchProcessingService.batchDeleteBySources(sources != null ? sources : java.util.Collections.emptyList());
    }

    /**
     * 批量处理实时数据
     */
    @PostMapping("/realtime-data/process")
    @Operation(summary = "批量处理实时数据", description = "批量处理实时数据（验证、清洗、转换等）")
    @SuppressWarnings("null")
    public Result<List<RealTimeData>> batchProcessRealTimeData(@NonNull @RequestBody List<RealTimeData> realTimeDataList) {
        log.info("批量处理实时数据请求，数据数量: {}", realTimeDataList != null ? realTimeDataList.size() : 0);
        Result<List<RealTimeData>> result = batchProcessingService.batchProcessRealTimeData(realTimeDataList != null ? realTimeDataList : java.util.Collections.emptyList());
        // 确保返回结果中的数据列表不为null
        if (result.getData() == null) {
            result.setData(java.util.Collections.emptyList());
        }
        return result;
    }
}