package com.windtunnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.windtunnel.common.Constants;
import com.windtunnel.common.Result;
import com.windtunnel.entity.Equipment;
import com.windtunnel.repository.EquipmentRepository;
import com.windtunnel.service.EquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 设备服务实现类
 * 
 * 实现设备相关的业务逻辑处理方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Service
public class EquipmentServiceImpl extends ServiceImpl<EquipmentRepository, Equipment> implements EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public Equipment findByName(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }
        QueryWrapper<Equipment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        queryWrapper.eq("deleted", 0); // 未删除的设备
        return equipmentRepository.selectOne(queryWrapper);
    }

    @Override
    public Equipment findByKksCode(String kksCode) {
        if (!StringUtils.hasText(kksCode)) {
            return null;
        }
        QueryWrapper<Equipment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("kks_code", kksCode);
        queryWrapper.eq("deleted", 0); // 未删除的设备
        return equipmentRepository.selectOne(queryWrapper);
    }

    @Override
    public Result<Equipment> createEquipment(Equipment equipment) {
        log.info("创建设备，设备名称: {}", equipment.getName());
        
        if (!StringUtils.hasText(equipment.getName())) {
            return Result.error("设备名称不能为空");
        }

        if (!StringUtils.hasText(equipment.getKksCode())) {
            return Result.error("KKS编码不能为空");
        }

        // 检查设备名称是否已存在
        Equipment existingEquipment = findByName(equipment.getName());
        if (existingEquipment != null) {
            return Result.error("设备名称已存在");
        }

        // 检查KKS编码是否已存在
        Equipment existingEquipmentByKks = findByKksCode(equipment.getKksCode());
        if (existingEquipmentByKks != null) {
            return Result.error("KKS编码已存在");
        }

        // 设置默认状态
        if (equipment.getStatus() == null) {
            equipment.setStatus(Constants.EquipmentStatus.INACTIVE);
        }

        // 设置默认创建时间
        equipment.setCreateTime(LocalDateTime.now());

        int result = equipmentRepository.insert(equipment);
        if (result > 0) {
            log.info("设备创建成功，设备ID: {}", equipment.getId());
            return Result.success("创建成功", equipment);
        } else {
            log.error("设备创建失败，设备名称: {}", equipment.getName());
            return Result.error("创建失败");
        }
    }

    @Override
    public Result<Equipment> updateEquipment(Equipment equipment) {
        log.info("更新设备信息，设备ID: {}", equipment.getId());
        
        if (equipment.getId() == null) {
            return Result.error("设备ID不能为空");
        }

        // 查询原设备信息
        Equipment existingEquipment = equipmentRepository.selectById(equipment.getId());
        if (existingEquipment == null) {
            return Result.error("设备不存在");
        }

        // 检查KKS编码是否与其他设备冲突
        Equipment existingEquipmentByKks = findByKksCode(equipment.getKksCode());
        if (existingEquipmentByKks != null && !existingEquipmentByKks.getId().equals(equipment.getId())) {
            return Result.error("KKS编码已存在");
        }

        // 更新设备信息
        existingEquipment.setName(equipment.getName());
        existingEquipment.setModel(equipment.getModel());
        existingEquipment.setKksCode(equipment.getKksCode());
        existingEquipment.setCategoryId(equipment.getCategoryId());
        existingEquipment.setTechnicalParams(equipment.getTechnicalParams());
        existingEquipment.setManufacturer(equipment.getManufacturer());
        existingEquipment.setInstallationLocation(equipment.getInstallationLocation());
        existingEquipment.setPurchaseDate(equipment.getPurchaseDate());
        existingEquipment.setWarrantyPeriod(equipment.getWarrantyPeriod());
        existingEquipment.setCalibrationCycle(equipment.getCalibrationCycle());
        existingEquipment.setPurchasePrice(equipment.getPurchasePrice());
        existingEquipment.setCurrentValue(equipment.getCurrentValue());
        existingEquipment.setLaboratoryId(equipment.getLaboratoryId());
        existingEquipment.setRemark(equipment.getRemark());

        int result = equipmentRepository.updateById(existingEquipment);
        if (result > 0) {
            log.info("设备信息更新成功，设备ID: {}", existingEquipment.getId());
            return Result.success("更新成功", existingEquipment);
        } else {
            log.error("设备信息更新失败，设备ID: {}", existingEquipment.getId());
            return Result.error("更新失败");
        }
    }

    @Override
    public Result<Boolean> updateEquipmentStatus(Long equipmentId, Integer status) {
        log.info("更新设备状态，设备ID: {}, 状态: {}", equipmentId, status);
        
        if (equipmentId == null || status == null) {
            return Result.error("设备ID和状态不能为空");
        }

        // 检查状态值是否合法
        if (status < 0 || status > 3) {
            return Result.error("状态值不合法");
        }

        Equipment equipment = new Equipment();
        equipment.setId(equipmentId);
        equipment.setStatus(status);

        int result = equipmentRepository.updateById(equipment);
        if (result > 0) {
            log.info("设备状态更新成功，设备ID: {}", equipmentId);
            return Result.success("状态更新成功", true);
        } else {
            log.error("设备状态更新失败，设备ID: {}", equipmentId);
            return Result.error("状态更新失败");
        }
    }

    @Override
    public Result<List<Equipment>> findByLaboratoryId(Long laboratoryId) {
        log.info("根据实验室ID查询设备列表，实验室ID: {}", laboratoryId);
        
        if (laboratoryId == null) {
            return Result.error("实验室ID不能为空");
        }

        List<Equipment> equipments = equipmentRepository.findByLaboratoryId(laboratoryId);
        return Result.success("查询成功", equipments);
    }

    @Override
    public Result<List<Equipment>> findByCategoryId(Long categoryId) {
        log.info("根据设备分类ID查询设备列表，分类ID: {}", categoryId);
        
        if (categoryId == null) {
            return Result.error("分类ID不能为空");
        }

        List<Equipment> equipments = equipmentRepository.findByCategoryId(categoryId);
        return Result.success("查询成功", equipments);
    }

    @Override
    public Result<List<Equipment>> findByStatus(Integer status) {
        log.info("根据设备状态查询设备列表，状态: {}", status);
        
        if (status == null) {
            return Result.error("状态不能为空");
        }

        List<Equipment> equipments = equipmentRepository.findByStatus(status);
        return Result.success("查询成功", equipments);
    }

    @Override
    public Result<List<Equipment>> findByPurchaseDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("根据采购日期范围查询设备列表，开始日期: {}, 结束日期: {}", startDate, endDate);
        
        if (startDate == null || endDate == null) {
            return Result.error("开始日期和结束日期不能为空");
        }

        List<Equipment> equipments = equipmentRepository.findByPurchaseDateRange(startDate, endDate);
        return Result.success("查询成功", equipments);
    }

    @Override
    public Result<List<Equipment>> findEquipments(int page, int size) {
        log.info("分页查询设备列表，页码: {}, 页面大小: {}", page, size);
        
        if (page < 1) {
            page = 1;
        }
        if (size < 1) {
            size = Constants.DefaultValues.DEFAULT_PAGE_SIZE;
        }

        // 这里简化实现，实际项目中应使用分页插件
        QueryWrapper<Equipment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0); // 未删除的设备
        queryWrapper.orderByDesc("create_time");

        List<Equipment> equipments = equipmentRepository.selectList(queryWrapper);
        return Result.success("查询成功", equipments);
    }

    @Override
    public Result<Boolean> deleteEquipment(Long equipmentId) {
        log.info("删除设备，设备ID: {}", equipmentId);
        
        if (equipmentId == null) {
            return Result.error("设备ID不能为空");
        }

        Equipment equipment = new Equipment();
        equipment.setId(equipmentId);
        equipment.setDeleted(1); // 逻辑删除

        int result = equipmentRepository.updateById(equipment);
        if (result > 0) {
            log.info("设备删除成功，设备ID: {}", equipmentId);
            return Result.success("删除成功", true);
        } else {
            log.error("设备删除失败，设备ID: {}", equipmentId);
            return Result.error("删除失败");
        }
    }

    @Override
    public Result<Boolean> updateCalibrationTime(Long equipmentId, LocalDateTime lastCalibrationTime, LocalDateTime nextCalibrationTime) {
        log.info("更新设备校准时间，设备ID: {}, 上次校准时间: {}, 下次校准时间: {}", 
                equipmentId, lastCalibrationTime, nextCalibrationTime);
        
        if (equipmentId == null) {
            return Result.error("设备ID不能为空");
        }

        int result = equipmentRepository.updateCalibrationTime(equipmentId, lastCalibrationTime, nextCalibrationTime);
        if (result > 0) {
            log.info("设备校准时间更新成功，设备ID: {}", equipmentId);
            return Result.success("校准时间更新成功", true);
        } else {
            log.error("设备校准时间更新失败，设备ID: {}", equipmentId);
            return Result.error("校准时间更新失败");
        }
    }

}