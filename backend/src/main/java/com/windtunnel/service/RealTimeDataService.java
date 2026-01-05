package com.windtunnel.service;

import com.windtunnel.entity.RealTimeData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 实时数据服务接口
 * 
 * 定义实时数据的业务操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
public interface RealTimeDataService {

    /**
     * 保存实时数据
     * 
     * @param realTimeData 实时数据实体
     * @return 保存后的实时数据实体
     */
    RealTimeData save(RealTimeData realTimeData);

    /**
     * 根据ID查询实时数据
     * 
     * @param id 实时数据ID
     * @return 实时数据实体
     */
    RealTimeData findById(String id);

    /**
     * 查询所有实时数据
     * 
     * @return 实时数据列表
     */
    List<RealTimeData> findAll();

    /**
     * 分页查询实时数据
     * 
     * @param pageable 分页参数
     * @return 分页结果
     */
    Page<RealTimeData> findAll(Pageable pageable);

    /**
     * 根据数据源查询实时数据
     * 
     * @param source 数据源
     * @return 实时数据列表
     */
    List<RealTimeData> findBySource(String source);

    /**
     * 根据时间范围查询实时数据
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 实时数据列表
     */
    List<RealTimeData> findByDataTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 删除过期数据
     * 
     * @param expiredTime 过期时间点
     * @return 删除的数据条数
     */
    int deleteExpiredData(LocalDateTime expiredTime);

    /**
     * 根据实验室ID查询实时数据
     * 
     * @param laboratoryId 实验室ID
     * @return 实时数据列表
     */
    List<RealTimeData> findByLaboratoryId(Long laboratoryId);

}