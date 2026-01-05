package com.windtunnel.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.windtunnel.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户数据访问层
 * 
 * 提供用户相关的数据库操作方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Mapper
public interface UserRepository extends BaseMapper<User> {

    /**
     * 根据用户名查询用户
     * 
     * @param username 用户名
     * @return 用户实体
     */
    User findByUsername(@Param("username") String username);

    /**
     * 根据实验室ID查询用户列表
     * 
     * @param laboratoryId 实验室ID
     * @return 用户列表
     */
    List<User> findByLaboratoryId(@Param("laboratoryId") Long laboratoryId);

    /**
     * 根据角色ID查询用户列表
     * 
     * @param roleId 角色ID
     * @return 用户列表
     */
    List<User> findByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据部门ID查询用户列表
     * 
     * @param departmentId 部门ID
     * @return 用户列表
     */
    List<User> findByDepartmentId(@Param("departmentId") Long departmentId);

    /**
     * 更新用户状态
     * 
     * @param userId 用户ID
     * @param status 状态
     * @return 更新记录数
     */
    int updateUserStatus(@Param("userId") Long userId, @Param("status") Integer status);

}