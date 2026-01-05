package com.windtunnel.service.impl;

import com.windtunnel.entity.RealTimeData;
import com.windtunnel.repository.RealTimeDataRepository;
import com.windtunnel.service.RealTimeDataService;
import com.windtunnel.service.base.MongoBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 实时数据服务实现类
 * 
 * 实现实时数据的业务操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Service
public class RealTimeDataServiceImpl extends MongoBaseService<RealTimeData, String, RealTimeDataRepository> implements RealTimeDataService {

    @Autowired
    private RealTimeDataRepository realTimeDataRepository;

    @Override
    protected RealTimeDataRepository getRepository() {
        return realTimeDataRepository;
    }

    /**
     * 保存实时数据
     * 
     * @param realTimeData 实时数据实体
     * @return 保存后的实时数据实体
     */
    @Override
    public RealTimeData save(RealTimeData realTimeData) {
        return super.save(realTimeData);
    }

    /**
     * 根据ID查询实时数据
     * 
     * @param id 实时数据ID
     * @return 实时数据实体
     */
    @Override
    public RealTimeData findById(@org.springframework.lang.NonNull String id) {
        return super.findById(id);
    }

    /**
     * 查询所有实时数据
     * 
     * @return 实时数据列表
     */
    @Override
    public List<RealTimeData> findAll() {
        return super.findAll();
    }

    /**
     * 分页查询实时数据
     * 
     * @param pageable 分页参数
     * @return 分页结果
     */
    @Override
    public Page<RealTimeData> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    /**
     * 根据数据源查询实时数据
     * 
     * @param source 数据源
     * @return 实时数据列表
     */
    @Override
    public List<RealTimeData> findBySource(@org.springframework.lang.NonNull String source) {
        logger.debug("根据数据源查询实时数据: {}", source);
        return realTimeDataRepository.findBySource(source);
    }

    /**
     * 根据时间范围查询实时数据
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 实时数据列表
     */
    @Override
    public List<RealTimeData> findByDataTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        logger.debug("根据时间范围查询实时数据: {} - {}", startTime, endTime);
        return realTimeDataRepository.findByDataTimeBetween(startTime, endTime);
    }

    /**
     * 删除过期数据
     * 
     * @param expiredTime 过期时间点
     * @return 删除的数据条数
     */
    @Override
    public int deleteExpiredData(@org.springframework.lang.NonNull LocalDateTime expiredTime) {
        logger.info("开始删除过期数据，过期时间: {}", expiredTime);
        List<RealTimeData> expiredDataList = realTimeDataRepository.findByDataTimeBefore(expiredTime);
        int count = expiredDataList.size();
        deleteAll(expiredDataList);
        logger.info("删除过期数据完成，共删除 {} 条数据", count);
        return count;
    }

    /**
     * 根据实验室ID查询实时数据
     * 
     * @param laboratoryId 实验室ID
     * @return 实时数据列表
     */
    @Override
    public List<RealTimeData> findByLaboratoryId(@org.springframework.lang.NonNull Long laboratoryId) {
        logger.debug("根据实验室ID查询实时数据: {}", laboratoryId);
        return realTimeDataRepository.findByLaboratoryId(laboratoryId);
    }

}