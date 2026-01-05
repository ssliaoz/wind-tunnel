package com.windtunnel.service.impl;

import com.windtunnel.common.Result;
import com.windtunnel.entity.RealTimeData;
import com.windtunnel.repository.RealTimeDataRepository;
import com.windtunnel.service.BatchProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 批量处理服务实现类
 * 
 * 实现批量处理相关功能，优化系统性能
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Service
public class BatchProcessingServiceImpl implements BatchProcessingService {

    @Autowired
    private RealTimeDataRepository realTimeDataRepository;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Result<Integer> batchSaveRealTimeData(List<RealTimeData> realTimeDataList) {
        log.info("批量保存实时数据，数量: {}", realTimeDataList != null ? realTimeDataList.size() : 0);
        
        try {
            if (realTimeDataList == null || realTimeDataList.isEmpty()) {
                return Result.success("数据列表为空", 0);
            }
            
            // 设置数据时间
            for (RealTimeData data : realTimeDataList) {
                if (data.getDataTime() == null) {
                    data.setDataTime(LocalDateTime.now());
                }
            }
            
            // 批量保存
            List<RealTimeData> savedList = realTimeDataRepository.saveAll(realTimeDataList);
            
            log.info("批量保存完成，保存数量: {}", savedList.size());
            return Result.success("批量保存成功", savedList.size());
        } catch (Exception e) {
            log.error("批量保存实时数据失败: {}", e.getMessage(), e);
            return Result.error("批量保存失败: " + e.getMessage());
        }
    }

    @Override
    public Result<Integer> batchUpdateRealTimeData(List<RealTimeData> realTimeDataList) {
        log.info("批量更新实时数据，数量: {}", realTimeDataList != null ? realTimeDataList.size() : 0);
        
        try {
            if (realTimeDataList == null || realTimeDataList.isEmpty()) {
                return Result.success("数据列表为空", 0);
            }
            
            int updatedCount = 0;
            for (RealTimeData data : realTimeDataList) {
                if (data.getId() != null) {
                    realTimeDataRepository.save(data);
                    updatedCount++;
                }
            }
            
            log.info("批量更新完成，更新数量: {}", updatedCount);
            return Result.success("批量更新成功", updatedCount);
        } catch (Exception e) {
            log.error("批量更新实时数据失败: {}", e.getMessage(), e);
            return Result.error("批量更新失败: " + e.getMessage());
        }
    }

    @Override
    public Result<Integer> batchDeleteRealTimeData(List<String> ids) {
        log.info("批量删除实时数据，ID数量: {}", ids != null ? ids.size() : 0);
        
        try {
            if (ids == null || ids.isEmpty()) {
                return Result.success("ID列表为空", 0);
            }
            
            // 批量删除
            for (String id : ids) {
                realTimeDataRepository.deleteById(id);
            }
            
            log.info("批量删除完成，删除数量: {}", ids.size());
            return Result.success("批量删除成功", ids.size());
        } catch (Exception e) {
            log.error("批量删除实时数据失败: {}", e.getMessage(), e);
            return Result.error("批量删除失败: " + e.getMessage());
        }
    }

    @Override
    public Result<List<RealTimeData>> batchQueryRealTimeData(List<String> ids) {
        log.info("批量查询实时数据，ID数量: {}", ids != null ? ids.size() : 0);
        
        try {
            if (ids == null || ids.isEmpty()) {
                return Result.success("ID列表为空", null);
            }
            
            // 构建查询条件
            Query query = new Query(Criteria.where("id").in(ids));
            List<RealTimeData> dataList = mongoTemplate.find(query, RealTimeData.class);
            
            log.info("批量查询完成，查询数量: {}", dataList.size());
            return Result.success("批量查询成功", dataList);
        } catch (Exception e) {
            log.error("批量查询实时数据失败: {}", e.getMessage(), e);
            return Result.error("批量查询失败: " + e.getMessage());
        }
    }

    @Override
    public Result<Integer> batchDeleteByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        log.info("批量按时间范围删除数据，时间范围: {} - {}", startTime, endTime);
        
        try {
            if (startTime == null || endTime == null) {
                return Result.error("时间范围不能为空");
            }
            
            // 构建查询条件
            Query query = new Query(Criteria.where("dataTime").gte(startTime).lte(endTime));
            long deletedCount = mongoTemplate.remove(query, RealTimeData.class).getDeletedCount();
            
            log.info("批量按时间范围删除完成，删除数量: {}", deletedCount);
            return Result.success("批量删除完成", (int) deletedCount);
        } catch (Exception e) {
            log.error("批量按时间范围删除数据失败: {}", e.getMessage(), e);
            return Result.error("批量删除失败: " + e.getMessage());
        }
    }

    @Override
    public Result<Integer> batchDeleteBySources(List<String> sources) {
        log.info("批量按数据源删除数据，数据源数量: {}", sources != null ? sources.size() : 0);
        
        try {
            if (sources == null || sources.isEmpty()) {
                return Result.success("数据源列表为空", 0);
            }
            
            // 构建查询条件
            Query query = new Query(Criteria.where("source").in(sources));
            long deletedCount = mongoTemplate.remove(query, RealTimeData.class).getDeletedCount();
            
            log.info("批量按数据源删除完成，删除数量: {}", deletedCount);
            return Result.success("批量删除完成", (int) deletedCount);
        } catch (Exception e) {
            log.error("批量按数据源删除数据失败: {}", e.getMessage(), e);
            return Result.error("批量删除失败: " + e.getMessage());
        }
    }

    @Override
    public Result<List<RealTimeData>> batchProcessRealTimeData(List<RealTimeData> realTimeDataList) {
        log.info("批量处理实时数据，数量: {}", realTimeDataList != null ? realTimeDataList.size() : 0);
        
        try {
            if (realTimeDataList == null || realTimeDataList.isEmpty()) {
                return Result.success("数据列表为空", null);
            }
            
            // 批量处理：验证、清洗、转换等
            for (RealTimeData data : realTimeDataList) {
                // 设置默认时间
                if (data.getDataTime() == null) {
                    data.setDataTime(LocalDateTime.now());
                }
                
                // 数据验证和清洗
                if (data.getWindSpeed() != null && data.getWindSpeed().compareTo(java.math.BigDecimal.valueOf(200)) > 0) {
                    // 对异常值进行处理
                    log.warn("检测到异常风速值: {}", data.getWindSpeed());
                }
                
                // 设置默认状态
                if (data.getStatus() == null) {
                    data.setStatus(0); // 正常状态
                }
            }
            
            // 保存处理后的数据
            List<RealTimeData> processedList = realTimeDataRepository.saveAll(realTimeDataList);
            
            log.info("批量处理完成，处理数量: {}", processedList.size());
            return Result.success("批量处理完成", processedList);
        } catch (Exception e) {
            log.error("批量处理实时数据失败: {}", e.getMessage(), e);
            return Result.error("批量处理失败: " + e.getMessage());
        }
    }
}