package com.windtunnel.repository.security;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.windtunnel.entity.security.RiskRegister;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 风险台账数据访问层
 * 
 * 提供风险台账相关的数据库操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Mapper
public interface RiskRegisterRepository extends BaseMapper<RiskRegister> {

    /**
     * 根据实验室ID查询风险台账列表
     * 
     * @param laboratoryId 实验室ID
     * @return 风险台账列表
     */
    List<RiskRegister> findByLaboratoryId(Long laboratoryId);

    /**
     * 根据试验项目ID查询风险台账列表
     * 
     * @param experimentProjectId 试验项目ID
     * @return 风险台账列表
     */
    List<RiskRegister> findByExperimentProjectId(Long experimentProjectId);

    /**
     * 根据设备ID查询风险台账列表
     * 
     * @param equipmentId 设备ID
     * @return 风险台账列表
     */
    List<RiskRegister> findByEquipmentId(Long equipmentId);

    /**
     * 根据风险等级查询风险台账列表
     * 
     * @param riskLevel 风险等级
     * @return 风险台账列表
     */
    List<RiskRegister> findByRiskLevel(Integer riskLevel);

    /**
     * 根据风险状态查询风险台账列表
     * 
     * @param riskStatus 风险状态
     * @return 风险台账列表
     */
    List<RiskRegister> findByRiskStatus(Integer riskStatus);

    /**
     * 根据时间范围查询风险台账列表
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 风险台账列表
     */
    List<RiskRegister> findByIdentificationTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据风险责任人ID查询风险台账列表
     * 
     * @param responsiblePersonId 风险责任人ID
     * @return 风险台账列表
     */
    List<RiskRegister> findByResponsiblePersonId(Long responsiblePersonId);

    /**
     * 根据风险值范围查询风险台账列表
     * 
     * @param minValue 最小风险值
     * @param maxValue 最大风险值
     * @return 风险台账列表
     */
    List<RiskRegister> findByRiskValueBetween(java.math.BigDecimal minValue, java.math.BigDecimal maxValue);

}