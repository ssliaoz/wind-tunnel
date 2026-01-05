package com.windtunnel.service.system;

import com.windtunnel.entity.system.SystemParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 系统参数服务接口
 * 
 * 定义系统参数相关的业务操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
public interface SystemParameterService {

    /**
     * 保存系统参数
     * 
     * @param systemParameter 系统参数实体
     * @return 保存后的系统参数实体
     */
    SystemParameter save(SystemParameter systemParameter);

    /**
     * 根据ID查询系统参数
     * 
     * @param id 系统参数ID
     * @return 系统参数实体
     */
    SystemParameter findById(Long id);

    /**
     * 查询所有系统参数
     * 
     * @return 系统参数列表
     */
    List<SystemParameter> findAll();

    /**
     * 分页查询系统参数
     * 
     * @param pageable 分页参数
     * @return 分页结果
     */
    Page<SystemParameter> findAll(Pageable pageable);

    /**
     * 根据参数键查询系统参数
     * 
     * @param paramKey 参数键
     * @return 系统参数
     */
    SystemParameter findByParamKey(String paramKey);

    /**
     * 根据参数分组查询系统参数
     * 
     * @param paramGroup 参数分组
     * @return 系统参数列表
     */
    List<SystemParameter> findByParamGroup(String paramGroup);

    /**
     * 根据参数类型查询系统参数
     * 
     * @param paramType 参数类型
     * @return 系统参数列表
     */
    List<SystemParameter> findByParamType(Integer paramType);

    /**
     * 根据是否启用查询系统参数
     * 
     * @param enabled 是否启用
     * @return 系统参数列表
     */
    List<SystemParameter> findByEnabled(Integer enabled);

    /**
     * 根据参数名称查询系统参数
     * 
     * @param paramName 参数名称
     * @return 系统参数列表
     */
    List<SystemParameter> findByParamName(String paramName);

    /**
     * 删除系统参数
     * 
     * @param id 系统参数ID
     * @return 删除结果
     */
    boolean deleteById(Long id);

    /**
     * 更新系统参数值
     * 
     * @param paramKey 参数键
     * @param paramValue 新参数值
     * @return 更新结果
     */
    boolean updateParamValue(String paramKey, String paramValue);

    /**
     * 获取参数值
     * 
     * @param paramKey 参数键
     * @return 参数值
     */
    String getParamValue(String paramKey);

    /**
     * 获取参数值，带默认值
     * 
     * @param paramKey 参数键
     * @param defaultValue 默认值
     * @return 参数值
     */
    String getParamValue(String paramKey, String defaultValue);

    /**
     * 批量更新参数
     * 
     * @param parameters 参数列表
     * @return 更新结果
     */
    boolean batchUpdate(List<SystemParameter> parameters);

    /**
     * 加载所有启用的参数到缓存
     * 
     * @return 加载结果
     */
    boolean loadParametersToCache();

    /**
     * 清除参数缓存
     * 
     * @return 清除结果
     */
    boolean clearParameterCache();

    /**
     * 验证参数值是否符合规则
     * 
     * @param parameter 系统参数
     * @return 验证结果
     */
    boolean validateParameter(SystemParameter parameter);

    /**
     * 获取参数选项
     * 
     * @param paramKey 参数键
     * @return 参数选项JSON字符串
     */
    String getParameterOptions(String paramKey);

}