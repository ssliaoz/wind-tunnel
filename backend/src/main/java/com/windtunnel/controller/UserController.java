package com.windtunnel.controller;

import com.windtunnel.common.Result;
import com.windtunnel.entity.User;
import com.windtunnel.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 * 
 * 提供用户相关的REST API接口
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
@Tag(name = "用户管理", description = "用户相关的API接口")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * 
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    @Operation(summary = "用户登录", description = "根据用户名和密码进行用户登录")
    @PostMapping("/login")
    public Result<String> login(@RequestParam String username, @RequestParam String password) {
        log.info("用户登录请求，用户名: {}", username);
        return userService.login(username, password);
    }

    /**
     * 用户注册
     * 
     * @param user 用户实体
     * @return 注册结果
     */
    @Operation(summary = "用户注册", description = "创建新用户")
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        log.info("用户注册请求，用户名: {}", user.getUsername());
        return userService.register(user);
    }

    /**
     * 根据ID获取用户信息
     * 
     * @param id 用户ID
     * @return 用户信息
     */
    @Operation(summary = "根据ID获取用户", description = "根据用户ID获取用户详细信息")
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        log.info("获取用户信息请求，用户ID: {}", id);
        User user = userService.getById(id);
        if (user != null && user.getDeleted() == 0) {
            return Result.success("查询成功", user);
        } else {
            return Result.error("用户不存在");
        }
    }

    /**
     * 更新用户信息
     * 
     * @param user 用户实体
     * @return 更新结果
     */
    @Operation(summary = "更新用户信息", description = "更新用户的基本信息")
    @PutMapping
    public Result<User> updateUserInfo(@RequestBody User user) {
        log.info("更新用户信息请求，用户ID: {}", user.getId());
        return userService.updateUserInfo(user);
    }

    /**
     * 更新用户状态
     * 
     * @param userId 用户ID
     * @param status 状态
     * @return 更新结果
     */
    @Operation(summary = "更新用户状态", description = "更新用户的启用/禁用状态")
    @PutMapping("/status")
    public Result<Boolean> updateUserStatus(@RequestParam Long userId, @RequestParam Integer status) {
        log.info("更新用户状态请求，用户ID: {}, 状态: {}", userId, status);
        return userService.updateUserStatus(userId, status);
    }

    /**
     * 根据实验室ID查询用户列表
     * 
     * @param laboratoryId 实验室ID
     * @return 用户列表
     */
    @Operation(summary = "根据实验室查询用户", description = "根据实验室ID获取该实验室下的所有用户")
    @GetMapping("/laboratory/{laboratoryId}")
    public Result<List<User>> getUsersByLaboratoryId(@PathVariable Long laboratoryId) {
        log.info("根据实验室ID查询用户列表请求，实验室ID: {}", laboratoryId);
        return userService.findByLaboratoryId(laboratoryId);
    }

    /**
     * 根据角色ID查询用户列表
     * 
     * @param roleId 角色ID
     * @return 用户列表
     */
    @Operation(summary = "根据角色查询用户", description = "根据角色ID获取具有该角色的所有用户")
    @GetMapping("/role/{roleId}")
    public Result<List<User>> getUsersByRoleId(@PathVariable Long roleId) {
        log.info("根据角色ID查询用户列表请求，角色ID: {}", roleId);
        return userService.findByRoleId(roleId);
    }

    /**
     * 分页查询用户列表
     * 
     * @param page 页码
     * @param size 页面大小
     * @return 用户列表
     */
    @Operation(summary = "分页查询用户", description = "分页获取用户列表")
    @GetMapping
    public Result<List<User>> getUsers(@RequestParam(defaultValue = "1") int page, 
                                     @RequestParam(defaultValue = "10") int size) {
        log.info("分页查询用户列表请求，页码: {}, 页面大小: {}", page, size);
        return userService.findUsers(page, size);
    }

    /**
     * 删除用户
     * 
     * @param userId 用户ID
     * @return 删除结果
     */
    @Operation(summary = "删除用户", description = "根据用户ID删除用户（逻辑删除）")
    @DeleteMapping("/{userId}")
    public Result<Boolean> deleteUser(@PathVariable Long userId) {
        log.info("删除用户请求，用户ID: {}", userId);
        return userService.deleteUser(userId);
    }

    /**
     * 重置用户密码
     * 
     * @param userId 用户ID
     * @return 重置结果
     */
    @Operation(summary = "重置用户密码", description = "重置指定用户的密码")
    @PutMapping("/reset-password/{userId}")
    public Result<Boolean> resetPassword(@PathVariable Long userId) {
        log.info("重置用户密码请求，用户ID: {}", userId);
        return userService.resetPassword(userId);
    }

}