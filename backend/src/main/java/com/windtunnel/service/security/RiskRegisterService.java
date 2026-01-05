package com.windtunnel.service.security;

import com.windtunnel.entity.security.RiskRegister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 风险台账服务接口
 * 
 * 定义风险台账相关的业务操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
public interface RiskRegisterService {

    /**
     * 保存风险台账
     * 
     * @param riskRegister 风险台账实体
     * @return 保存后的风险台账实体
     */
    RiskRegister save(RiskRegister riskRegister);

    /**
     * 根据ID查询风险台账
     * 
     * @param id 风险台账ID
     * @return 风险台账实体
     */
    RiskRegister findById(Long id);

    /**
     * 查询所有风险台账
     * 
     * @return 风险台账列表
     */
    List<RiskRegister> findAll();

    /**
     * 分页查询风险台账
     * 
     * @param pageable 分页参数
     * @return 分页结果
     */
    Page<RiskRegister> findAll(Pageable pageable);

    /**
     * 根据实验室ID查询风险台账
     * 
     * @param laboratoryId 实验室ID
     * @return 风险台账列表
     */
    List<RiskRegister> findByLaboratoryId(Long laboratoryId);

    /**
     * 根据试验项目ID查询风险台账
     * 
     * @param experimentProjectId 试验项目ID
     * @return 风险台账列表
     */
    List<RiskRegister> findByExperimentProjectId(Long experimentProjectId);

    /**
     * 根据设备ID查询风险台账
     * 
     * @param equipmentId 设备ID
     * @return 风险台账列表
     */
    List<RiskRegister> findByEquipmentId(Long equipmentId);

    /**
     * 根据风险等级查询风险台账
     * 
     * @param riskLevel 风险等级
     * @return 风险台账列表
     */
    List<RiskRegister> findByRiskLevel(Integer riskLevel);

    /**
     * 根据风险状态查询风险台账
     * 
     * @param riskStatus 风险状态
     * @return 风险台账列表
     */
    List<RiskRegister> findByRiskStatus(Integer riskStatus);

    /**
     * 根据时间范围查询风险台账
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 风险台账列表
     */
    List<RiskRegister> findByIdentificationTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据风险责任人ID查询风险台账
     * 
     * @param responsiblePersonId 风险责任人ID
     * @return 风险台账列表
     */
    List<RiskRegister> findByResponsiblePersonId(Long responsiblePersonId);

    /**
     * 根据风险值范围查询风险台账
     * 
     * @param minValue 最小风险值
     * @param maxValue 最大风险值
     * @return 风险台账列表
     */
    List<RiskRegister> findByRiskValueBetween(BigDecimal minValue, BigDecimal maxValue);

    /**
     * 删除风险台账
     * 
     * @param id 风险台账ID
     * @return 删除结果
     */
    boolean deleteById(Long id);

    /**
     * 更新风险状态
     * 
     * @param id 风险台账ID
     * @param riskStatus 新状态
     * @return 更新结果
     */
    boolean updateRiskStatus(Long id, Integer riskStatus);

    /**
     * 更新风险等级
     * 
     * @param id 风险台账ID
     * @param riskLevel 新等级
     * @return 更新结果
     */
    boolean updateRiskLevel(Long id, Integer riskLevel);

    /**
     * 评估风险值
     * 
     * @param probability 风险概率
     * @param impact 风险影响
     * @return 风险值
     */
    BigDecimal calculateRiskValue(BigDecimal probability, BigDecimal impact);

    /**
     * 生成风险评估报告
     * 
     * @param laboratoryId 实验室ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 风险评估报告内容
     */
    String generateRiskAssessmentReport(Long laboratoryId, LocalDateTime startTime, LocalDateTime endTime);

}