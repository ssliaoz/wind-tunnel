package com.windtunnel.feign;

import com.windtunnel.common.Result;
import com.windtunnel.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户服务Feign客户端
 * 
 * 用于微服务之间的用户信息调用
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@FeignClient(name = "user-service", path = "/api/users")
public interface UserFeignClient {

    /**
     * 根据ID获取用户信息
     * 
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    Result<User> getUserById(@PathVariable("id") Long id);

    /**
     * 根据用户名查询用户
     * 
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/username/{username}")
    Result<User> getUserByUsername(@PathVariable("username") String username);

    /**
     * 更新用户信息
     * 
     * @param user 用户实体
     * @return 更新结果
     */
    @PutMapping
    Result<User> updateUser(@RequestBody User user);

    /**
     * 分页查询用户列表
     * 
     * @param page 页码
     * @param size 页面大小
     * @return 用户列表
     */
    @GetMapping
    Result<List<User>> getUsers(@RequestParam(defaultValue = "1") int page, 
                               @RequestParam(defaultValue = "10") int size);

}