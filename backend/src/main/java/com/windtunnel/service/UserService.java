package com.windtunnel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.windtunnel.entity.User;
import com.windtunnel.common.Result;

import java.util.List;

/**
 * 用户服务接口
 * 
 * 提供用户相关的业务逻辑处理方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名查询用户
     * 
     * @param username 用户名
     * @return 用户实体
     */
    User findByUsername(String username);

    /**
     * 用户登录
     * 
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    Result<String> login(String username, String password);

    /**
     * 用户注册
     * 
     * @param user 用户实体
     * @return 注册结果
     */
    Result<User> register(User user);

    /**
     * 更新用户信息
     * 
     * @param user 用户实体
     * @return 更新结果
     */
    Result<User> updateUserInfo(User user);

    /**
     * 更新用户状态
     * 
     * @param userId 用户ID
     * @param status 状态
     * @return 更新结果
     */
    Result<Boolean> updateUserStatus(Long userId, Integer status);

    /**
     * 根据实验室ID查询用户列表
     * 
     * @param laboratoryId 实验室ID
     * @return 用户列表
     */
    Result<List<User>> findByLaboratoryId(Long laboratoryId);

    /**
     * 根据角色ID查询用户列表
     * 
     * @param roleId 角色ID
     * @return 用户列表
     */
    Result<List<User>> findByRoleId(Long roleId);

    /**
     * 分页查询用户列表
     * 
     * @param page 页码
     * @param size 页面大小
     * @return 用户列表
     */
    Result<List<User>> findUsers(int page, int size);

    /**
     * 删除用户
     * 
     * @param userId 用户ID
     * @return 删除结果
     */
    Result<Boolean> deleteUser(Long userId);

    /**
     * 重置用户密码
     * 
     * @param userId 用户ID
     * @return 重置结果
     */
    Result<Boolean> resetPassword(Long userId);

}