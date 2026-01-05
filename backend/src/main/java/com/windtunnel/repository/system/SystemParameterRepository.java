package com.windtunnel.repository.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.windtunnel.entity.system.SystemParameter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统参数数据访问层
 * 
 * 提供系统参数相关的数据库操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Mapper
public interface SystemParameterRepository extends BaseMapper<SystemParameter> {

    /**
     * 根据参数键查询系统参数
     * 
     * @param paramKey 参数键
     * @return 系统参数
     */
    SystemParameter findByParamKey(String paramKey);

    /**
     * 根据参数分组查询系统参数列表
     * 
     * @param paramGroup 参数分组
     * @return 系统参数列表
     */
    List<SystemParameter> findByParamGroup(String paramGroup);

    /**
     * 根据参数类型查询系统参数列表
     * 
     * @param paramType 参数类型
     * @return 系统参数列表
     */
    List<SystemParameter> findByParamType(Integer paramType);

    /**
     * 根据是否启用查询系统参数列表
     * 
     * @param enabled 是否启用
     * @return 系统参数列表
     */
    List<SystemParameter> findByEnabled(Integer enabled);

    /**
     * 根据参数名称查询系统参数列表
     * 
     * @param paramName 参数名称
     * @return 系统参数列表
     */
    List<SystemParameter> findByParamName(String paramName);

}