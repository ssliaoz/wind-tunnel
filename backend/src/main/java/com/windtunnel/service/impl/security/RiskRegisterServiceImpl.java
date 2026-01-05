package com.windtunnel.service.impl.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.windtunnel.entity.security.RiskRegister;
import com.windtunnel.repository.security.RiskRegisterRepository;
import com.windtunnel.service.security.RiskRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 风险台账服务实现类
 * 
 * 实现风险台账相关的业务操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Service
public class RiskRegisterServiceImpl implements RiskRegisterService {

    @Autowired
    private RiskRegisterRepository riskRegisterRepository;

    /**
     * 保存风险台账
     * 
     * @param riskRegister 风险台账实体
     * @return 保存后的风险台账实体
     */
    @Override
    public RiskRegister save(RiskRegister riskRegister) {
        log.debug("保存风险台账: {}", riskRegister.getRiskName());
        if (riskRegister.getId() == null || riskRegister.getId() <= 0) {
            riskRegisterRepository.insert(riskRegister);
        } else {
            riskRegisterRepository.updateById(riskRegister);
        }
        return riskRegister;
    }

    /**
     * 根据ID查询风险台账
     * 
     * @param id 风险台账ID
     * @return 风险台账实体
     */
    @Override
    public RiskRegister findById(Long id) {
        log.debug("根据ID查询风险台账: {}", id);
        return riskRegisterRepository.selectById(id);
    }

    /**
     * 查询所有风险台账
     * 
     * @return 风险台账列表
     */
    @Override
    public List<RiskRegister> findAll() {
        log.debug("查询所有风险台账");
        return riskRegisterRepository.selectList(null);
    }

    /**
     * 分页查询风险台账
     * 
     * @param pageable 分页参数
     * @return 分页结果
     */
    @Override
    public org.springframework.data.domain.Page<RiskRegister> findAll(Pageable pageable) {
        log.debug("分页查询风险台账");
        Page<RiskRegister> mpPage = new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        Page<RiskRegister> resultPage = riskRegisterRepository.selectPage(mpPage, null);
        
        // 转换为Spring Data分页对象
        return new PageImpl<>(
            resultPage.getRecords(),
            pageable,
            resultPage.getTotal()
        );
    }

    /**
     * 根据实验室ID查询风险台账
     * 
     * @param laboratoryId 实验室ID
     * @return 风险台账列表
     */
    @Override
    public List<RiskRegister> findByLaboratoryId(Long laboratoryId) {
        log.debug("根据实验室ID查询风险台账: {}", laboratoryId);
        QueryWrapper<RiskRegister> wrapper = new QueryWrapper<>();
        wrapper.eq("laboratory_id", laboratoryId);
        return riskRegisterRepository.selectList(wrapper);
    }

    /**
     * 根据试验项目ID查询风险台账
     * 
     * @param experimentProjectId 试验项目ID
     * @return 风险台账列表
     */
    @Override
    public List<RiskRegister> findByExperimentProjectId(Long experimentProjectId) {
        log.debug("根据试验项目ID查询风险台账: {}", experimentProjectId);
        QueryWrapper<RiskRegister> wrapper = new QueryWrapper<>();
        wrapper.eq("experiment_project_id", experimentProjectId);
        return riskRegisterRepository.selectList(wrapper);
    }

    /**
     * 根据设备ID查询风险台账
     * 
     * @param equipmentId 设备ID
     * @return 风险台账列表
     */
    @Override
    public List<RiskRegister> findByEquipmentId(Long equipmentId) {
        log.debug("根据设备ID查询风险台账: {}", equipmentId);
        QueryWrapper<RiskRegister> wrapper = new QueryWrapper<>();
        wrapper.eq("equipment_id", equipmentId);
        return riskRegisterRepository.selectList(wrapper);
    }

    /**
     * 根据风险等级查询风险台账
     * 
     * @param riskLevel 风险等级
     * @return 风险台账列表
     */
    @Override
    public List<RiskRegister> findByRiskLevel(Integer riskLevel) {
        log.debug("根据风险等级查询风险台账: {}", riskLevel);
        QueryWrapper<RiskRegister> wrapper = new QueryWrapper<>();
        wrapper.eq("risk_level", riskLevel);
        return riskRegisterRepository.selectList(wrapper);
    }

    /**
     * 根据风险状态查询风险台账
     * 
     * @param riskStatus 风险状态
     * @return 风险台账列表
     */
    @Override
    public List<RiskRegister> findByRiskStatus(Integer riskStatus) {
        log.debug("根据风险状态查询风险台账: {}", riskStatus);
        QueryWrapper<RiskRegister> wrapper = new QueryWrapper<>();
        wrapper.eq("risk_status", riskStatus);
        return riskRegisterRepository.selectList(wrapper);
    }

    /**
     * 根据时间范围查询风险台账
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 风险台账列表
     */
    @Override
    public List<RiskRegister> findByIdentificationTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        log.debug("根据时间范围查询风险台账: {} - {}", startTime, endTime);
        QueryWrapper<RiskRegister> wrapper = new QueryWrapper<>();
        wrapper.between("identification_time", startTime, endTime);
        return riskRegisterRepository.selectList(wrapper);
    }

    /**
     * 根据风险责任人ID查询风险台账
     * 
     * @param responsiblePersonId 风险责任人ID
     * @return 风险台账列表
     */
    @Override
    public List<RiskRegister> findByResponsiblePersonId(Long responsiblePersonId) {
        log.debug("根据风险责任人ID查询风险台账: {}", responsiblePersonId);
        QueryWrapper<RiskRegister> wrapper = new QueryWrapper<>();
        wrapper.eq("responsible_person_id", responsiblePersonId);
        return riskRegisterRepository.selectList(wrapper);
    }

    /**
     * 根据风险值范围查询风险台账
     * 
     * @param minValue 最小风险值
     * @param maxValue 最大风险值
     * @return 风险台账列表
     */
    @Override
    public List<RiskRegister> findByRiskValueBetween(BigDecimal minValue, BigDecimal maxValue) {
        log.debug("根据风险值范围查询风险台账: {} - {}", minValue, maxValue);
        QueryWrapper<RiskRegister> wrapper = new QueryWrapper<>();
        wrapper.between("risk_value", minValue, maxValue);
        return riskRegisterRepository.selectList(wrapper);
    }

    /**
     * 删除风险台账
     * 
     * @param id 风险台账ID
     * @return 删除结果
     */
    @Override
    public boolean deleteById(Long id) {
        log.debug("删除风险台账: {}", id);
        return riskRegisterRepository.deleteById(id) > 0;
    }

    /**
     * 更新风险状态
     * 
     * @param id 风险台账ID
     * @param riskStatus 新状态
     * @return 更新结果
     */
    @Override
    public boolean updateRiskStatus(Long id, Integer riskStatus) {
        log.info("更新风险台账状态: {}, 状态: {}", id, riskStatus);
        RiskRegister riskRegister = findById(id);
        if (riskRegister != null) {
            riskRegister.setRiskStatus(riskStatus);
            riskRegister.setUpdateTime(LocalDateTime.now());
            return riskRegisterRepository.updateById(riskRegister) > 0;
        }
        return false;
    }

    /**
     * 更新风险等级
     * 
     * @param id 风险台账ID
     * @param riskLevel 新等级
     * @return 更新结果
     */
    @Override
    public boolean updateRiskLevel(Long id, Integer riskLevel) {
        log.info("更新风险台账等级: {}, 等级: {}", id, riskLevel);
        RiskRegister riskRegister = findById(id);
        if (riskRegister != null) {
            riskRegister.setRiskLevel(riskLevel);
            riskRegister.setUpdateTime(LocalDateTime.now());
            return riskRegisterRepository.updateById(riskRegister) > 0;
        }
        return false;
    }

    /**
     * 评估风险值
     * 
     * @param probability 风险概率
     * @param impact 风险影响
     * @return 风险值
     */
    @Override
    public BigDecimal calculateRiskValue(BigDecimal probability, BigDecimal impact) {
        log.debug("计算风险值，概率: {}, 影响: {}", probability, impact);
        if (probability == null || impact == null) {
            return BigDecimal.ZERO;
        }
        return probability.multiply(impact);
    }

    /**
     * 生成风险评估报告
     * 
     * @param laboratoryId 实验室ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 风险评估报告内容
     */
    @Override
    public String generateRiskAssessmentReport(Long laboratoryId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("生成风险评估报告，实验室ID: {}, 时间范围: {} - {}", laboratoryId, startTime, endTime);
        
        // 查询相关风险数据
        List<RiskRegister> risks;
        if (laboratoryId != null) {
            QueryWrapper<RiskRegister> wrapper = new QueryWrapper<>();
            wrapper.eq("laboratory_id", laboratoryId);
            if (startTime != null && endTime != null) {
                wrapper.between("identification_time", startTime, endTime);
            }
            risks = riskRegisterRepository.selectList(wrapper);
        } else if (startTime != null && endTime != null) {
            risks = findByIdentificationTimeBetween(startTime, endTime);
        } else {
            risks = findAll();
        }
        
        // 生成报告内容
        StringBuilder report = new StringBuilder();
        report.append("风险评估报告\n");
        report.append("生成时间: ").append(LocalDateTime.now()).append("\n");
        report.append("实验室ID: ").append(laboratoryId).append("\n");
        report.append("时间范围: ").append(startTime).append(" - ").append(endTime).append("\n\n");
        
        report.append("风险统计:\n");
        int highRiskCount = 0;
        int mediumRiskCount = 0;
        int lowRiskCount = 0;
        int criticalRiskCount = 0;
        
        for (RiskRegister risk : risks) {
            if (risk.getRiskLevel() != null) {
                switch (risk.getRiskLevel()) {
                    case 4: criticalRiskCount++; break;
                    case 3: highRiskCount++; break;
                    case 2: mediumRiskCount++; break;
                    case 1: lowRiskCount++; break;
                }
            }
        }
        
        report.append("极高风险: ").append(criticalRiskCount).append("\n");
        report.append("高风险: ").append(highRiskCount).append("\n");
        report.append("中风险: ").append(mediumRiskCount).append("\n");
        report.append("低风险: ").append(lowRiskCount).append("\n\n");
        
        report.append("风险详情:\n");
        for (RiskRegister risk : risks) {
            report.append("风险名称: ").append(risk.getRiskName()).append("\n");
            report.append("风险编号: ").append(risk.getRiskCode()).append("\n");
            report.append("风险等级: ").append(risk.getRiskLevel()).append("\n");
            report.append("风险值: ").append(risk.getRiskValue()).append("\n");
            report.append("风险描述: ").append(risk.getRiskDescription()).append("\n");
            report.append("风险状态: ").append(risk.getRiskStatus()).append("\n");
            report.append("----------\n");
        }
        
        return report.toString();
    }

}