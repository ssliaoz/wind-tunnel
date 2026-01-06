package com.windtunnel.service.impl;

import com.windtunnel.common.Result;
import com.windtunnel.entity.RealTimeData;
import com.windtunnel.repository.RealTimeDataRepository;
import com.windtunnel.service.DataCollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 数据采集服务实现类
 * 
 * 实现实时数据采集、处理和存储相关的业务逻辑处理方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Service
public class DataCollectionServiceImpl implements DataCollectionService {

    @Autowired
    private RealTimeDataRepository realTimeDataRepository;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Result<String> saveRealTimeData(RealTimeData realTimeData) {
        log.info("保存实时数据，数据来源: {}", realTimeData.getSource());
        
        try {
            // 设置数据时间
            if (realTimeData.getDataTime() == null) {
                realTimeData.setDataTime(LocalDateTime.now());
            }
            
            // 保存数据
            RealTimeData savedData = realTimeDataRepository.save(realTimeData);
            
            log.info("实时数据保存成功，数据ID: {}", savedData.getId());
            return Result.success("数据保存成功", savedData.getId());
        } catch (Exception e) {
            log.error("保存实时数据失败: {}", e.getMessage(), e);
            return Result.error("数据保存失败: " + e.getMessage());
        }
    }

    @Override
    public Result<List<RealTimeData>> findBySource(String source) {
        log.info("根据数据来源查询实时数据，来源: {}", source);
        
        try {
            List<RealTimeData> dataList = realTimeDataRepository.findBySource(source);
            return Result.success("查询成功", dataList);
        } catch (Exception e) {
            log.error("查询实时数据失败: {}", e.getMessage(), e);
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    @Override
    public Result<List<RealTimeData>> findByEquipmentId(Long equipmentId) {
        log.info("根据设备ID查询实时数据，设备ID: {}", equipmentId);
        
        try {
            List<RealTimeData> dataList = realTimeDataRepository.findByEquipmentId(equipmentId);
            return Result.success("查询成功", dataList);
        } catch (Exception e) {
            log.error("查询实时数据失败: {}", e.getMessage(), e);
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("null")
    public Result<List<RealTimeData>> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        log.info("根据时间范围查询实时数据，开始时间: {}, 结束时间: {}", startTime, endTime);
        
        try {
            // 由于MongoDB Repository没有直接的按时间范围查询方法，这里查询所有数据再过滤
            // 在实际项目中，应该在Repository中添加对应的方法
            // 使用MongoTemplate进行时间范围查询
            org.springframework.data.mongodb.core.query.Query query = 
                new org.springframework.data.mongodb.core.query.Query();
            query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("dataTime")
                .gte(startTime).lte(endTime));
            List<RealTimeData> dataList = mongoTemplate.find(query, RealTimeData.class);
            
            return Result.success("查询成功", dataList);
        } catch (Exception e) {
            log.error("查询实时数据失败: {}", e.getMessage(), e);
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("null")
    public Result<List<RealTimeData>> findBySourceAndTimeRange(String source, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("根据数据来源和时间范围查询实时数据，来源: {}, 开始时间: {}, 结束时间: {}", source, startTime, endTime);
        
        try {
            List<RealTimeData> dataList = realTimeDataRepository.findBySourceAndDataTimeBetween(source, startTime, endTime);
            return Result.success("查询成功", dataList);
        } catch (Exception e) {
            log.error("查询实时数据失败: {}", e.getMessage(), e);
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    @Override
    public Result<RealTimeData> getLatestDataBySource(String source) {
        log.info("获取最新实时数据，来源: {}", source);
        
        try {
            RealTimeData latestData = realTimeDataRepository.findTopBySourceOrderByDataTimeDesc(source);
            if (latestData != null) {
                return Result.success("查询成功", latestData);
            } else {
                return Result.error("未找到数据");
            }
        } catch (Exception e) {
            log.error("查询最新实时数据失败: {}", e.getMessage(), e);
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    @Override
    public Result<RealTimeData> getLatestDataByEquipmentId(Long equipmentId) {
        log.info("根据设备ID获取最新实时数据，设备ID: {}", equipmentId);
        
        try {
            RealTimeData latestData = realTimeDataRepository.findTopByEquipmentIdOrderByDataTimeDesc(equipmentId);
            if (latestData != null) {
                return Result.success("查询成功", latestData);
            } else {
                return Result.error("未找到数据");
            }
        } catch (Exception e) {
            log.error("查询最新实时数据失败: {}", e.getMessage(), e);
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("null")
    public Result<Boolean> deleteByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        log.info("根据时间范围删除实时数据，开始时间: {}, 结束时间: {}", startTime, endTime);
        
        try {
            long deletedCount = realTimeDataRepository.deleteByDataTimeBetween(startTime, endTime);
            log.info("删除了 {} 条实时数据", deletedCount);
            return Result.success("删除成功", true);
        } catch (Exception e) {
            log.error("删除实时数据失败: {}", e.getMessage(), e);
            return Result.error("删除失败: " + e.getMessage());
        }
    }

    @Override
    public Result<Boolean> checkForAnomalies(RealTimeData realTimeData) {
        log.info("检查数据异常，数据ID: {}", realTimeData.getId());
        
        try {
            boolean hasAnomaly = false;
            StringBuilder anomalyDescription = new StringBuilder();
            
            // 检查风速
            if (realTimeData.getWindSpeed() != null) {
                if (realTimeData.getWindSpeed().compareTo(new java.math.BigDecimal("0")) < 0 || 
                    realTimeData.getWindSpeed().compareTo(new java.math.BigDecimal("200")) > 0) {
                    hasAnomaly = true;
                    anomalyDescription.append("风速超出正常范围;");
                }
            }
            
            // 检查温度
            if (realTimeData.getTemperature() != null) {
                if (realTimeData.getTemperature().compareTo(new java.math.BigDecimal("-50")) < 0 || 
                    realTimeData.getTemperature().compareTo(new java.math.BigDecimal("150")) > 0) {
                    hasAnomaly = true;
                    anomalyDescription.append("温度超出正常范围;");
                }
            }
            
            // 检查压力
            if (realTimeData.getPressure() != null) {
                if (realTimeData.getPressure().compareTo(new java.math.BigDecimal("0")) < 0 || 
                    realTimeData.getPressure().compareTo(new java.math.BigDecimal("200")) > 0) {
                    hasAnomaly = true;
                    anomalyDescription.append("压力超出正常范围;");
                }
            }
            
            // 检查电压
            if (realTimeData.getVoltage() != null) {
                if (realTimeData.getVoltage().compareTo(new java.math.BigDecimal("0")) < 0 || 
                    realTimeData.getVoltage().compareTo(new java.math.BigDecimal("500")) > 0) {
                    hasAnomaly = true;
                    anomalyDescription.append("电压超出正常范围;");
                }
            }
            
            // 检查电流
            if (realTimeData.getCurrent() != null) {
                if (realTimeData.getCurrent().compareTo(new java.math.BigDecimal("0")) < 0 || 
                    realTimeData.getCurrent().compareTo(new java.math.BigDecimal("100")) > 0) {
                    hasAnomaly = true;
                    anomalyDescription.append("电流超出正常范围;");
                }
            }
            
            if (hasAnomaly) {
                realTimeData.setStatus(1); // 异常状态
                realTimeData.setAnomalyDescription(anomalyDescription.toString());
                log.warn("检测到数据异常: {}", anomalyDescription.toString());
                
                // 触发告警
                triggerAlert(realTimeData);
                
                return Result.success("检测到异常", true);
            } else {
                realTimeData.setStatus(0); // 正常状态
                return Result.success("数据正常", false);
            }
        } catch (Exception e) {
            log.error("检查数据异常失败: {}", e.getMessage(), e);
            return Result.error("检查异常失败: " + e.getMessage());
        }
    }

    @Override
    public Result<Boolean> triggerAlert(RealTimeData realTimeData) {
        log.info("触发告警，设备: {}, 异常: {}", realTimeData.getSource(), realTimeData.getAnomalyDescription());
        
        try {
            // 这里可以实现具体的告警逻辑，如发送邮件、短信或站内信
            // 为了简化，这里只记录日志
            log.warn("告警已触发 - 设备: {}, 异常描述: {}, 风险等级: {}", 
                    realTimeData.getSource(), realTimeData.getAnomalyDescription(), realTimeData.getRiskLevel());
            
            // 在实际项目中，可以调用通知服务发送告警
            // notificationService.sendAlert(realTimeData);
            
            return Result.success("告警已触发", true);
        } catch (Exception e) {
            log.error("触发告警失败: {}", e.getMessage(), e);
            return Result.error("告警触发失败: " + e.getMessage());
        }
    }

}