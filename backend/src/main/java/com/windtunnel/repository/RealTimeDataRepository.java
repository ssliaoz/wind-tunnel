package com.windtunnel.repository;

import com.windtunnel.entity.RealTimeData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 实时数据数据访问层
 * 
 * 提供实时数据相关的MongoDB操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Repository
public interface RealTimeDataRepository extends MongoRepository<RealTimeData, String> {

    /**
     * 根据数据来源查询实时数据列表
     * 
     * @param source 数据来源
     * @return 实时数据列表
     */
    List<RealTimeData> findBySource(String source);

    /**
     * 根据时间范围查询实时数据列表
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 实时数据列表
     */
    List<RealTimeData> findByDataTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据设备ID查询实时数据列表
     * 
     * @param equipmentId 设备ID
     * @return 实时数据列表
     */
    List<RealTimeData> findByEquipmentId(Long equipmentId);

    /**
     * 根据数据来源和时间范围查询实时数据列表
     * 
     * @param source 数据来源
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 实时数据列表
     */
    List<RealTimeData> findBySourceAndDataTimeBetween(String source, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据设备ID和时间范围查询实时数据列表
     * 
     * @param equipmentId 设备ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 实时数据列表
     */
    List<RealTimeData> findByEquipmentIdAndDataTimeBetween(Long equipmentId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据数据状态查询实时数据列表
     * 
     * @param status 数据状态
     * @return 实时数据列表
     */
    List<RealTimeData> findByStatus(Integer status);

    /**
     * 根据风险等级查询实时数据列表
     * 
     * @param riskLevel 风险等级
     * @return 实时数据列表
     */
    List<RealTimeData> findByRiskLevel(Integer riskLevel);

    /**
     * 根据设备ID查询最新的实时数据
     * 
     * @param equipmentId 设备ID
     * @return 最新实时数据
     */
    @Query(value = "{'equipmentId': ?0}", sort = "{'dataTime': -1}")
    RealTimeData findTopByEquipmentIdOrderByDataTimeDesc(Long equipmentId);

    /**
     * 根据数据来源查询最新的实时数据
     * 
     * @param source 数据来源
     * @return 最新实时数据
     */
    @Query(value = "{'source': ?0}", sort = "{'dataTime': -1}")
    RealTimeData findTopBySourceOrderByDataTimeDesc(String source);

    /**
     * 根据时间范围删除实时数据
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 删除记录数
     */
    long deleteByDataTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据设备ID删除实时数据
     * 
     * @param equipmentId 设备ID
     * @return 删除记录数
     */
    long deleteByEquipmentId(Long equipmentId);

    /**
     * 根据数据来源删除实时数据
     * 
     * @param source 数据来源
     * @return 删除记录数
     */
    long deleteBySource(String source);

    /**
     * 根据时间点之前的数据进行查询
     * 
     * @param dateTime 时间点
     * @return 实时数据列表
     */
    List<RealTimeData> findByDataTimeBefore(LocalDateTime dateTime);

    /**
     * 根据实验室ID查询实时数据列表
     * 
     * @param laboratoryId 实验室ID
     * @return 实时数据列表
     */
    List<RealTimeData> findByLaboratoryId(Long laboratoryId);

}