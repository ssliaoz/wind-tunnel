package com.windtunnel.controller;

import com.windtunnel.common.Result;
import com.windtunnel.entity.User;
import com.windtunnel.service.UserService;
import com.windtunnel.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 * 
 * 处理用户认证相关的请求
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "认证管理", description = "用户认证相关的API接口")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * 
     * @param loginRequest 登录请求参数
     * @return 认证结果
     */
    @Operation(summary = "用户登录", description = "用户认证登录，返回JWT令牌")
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        log.info("用户登录请求，用户名: {}", username);

        try {
            // 使用Spring Security进行认证
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 生成JWT令牌
            User user = userService.findByUsername(username);
            String token = jwtUtil.generateToken(user.getId(), user.getUsername());

            // 返回令牌和用户信息
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("tokenType", "Bearer");
            response.put("userId", user.getId());
            response.put("username", user.getUsername());

            log.info("用户登录成功，用户名: {}", username);
            return Result.success("登录成功", response);
        } catch (Exception e) {
            log.error("用户登录失败: {}", e.getMessage());
            return Result.error("用户名或密码错误");
        }
    }

    /**
     * 用户登出
     * 
     * @return 登出结果
     */
    @Operation(summary = "用户登出", description = "用户登出，清除认证状态")
    @PostMapping("/logout")
    public Result<String> logout() {
        // 在JWT方案中，服务器端不维护会话状态
        // 客户端需要删除本地存储的令牌
        SecurityContextHolder.clearContext();
        return Result.success("登出成功");
    }

    /**
     * 刷新令牌
     * 
     * @param refreshToken 刷新令牌
     * @return 新的访问令牌
     */
    @Operation(summary = "刷新令牌", description = "刷新JWT访问令牌")
    @PostMapping("/refresh")
    public Result<Map<String, String>> refresh(@RequestBody Map<String, String> refreshToken) {
        // 在实际应用中，这里应该验证刷新令牌并生成新的访问令牌
        // 由于简化实现，这里直接返回错误
        return Result.error("刷新令牌功能暂未实现");
    }

    /**
     * 获取当前认证用户信息
     * 
     * @param auth 认证对象
     * @return 用户信息
     */
    @Operation(summary = "获取当前用户", description = "获取当前认证用户的详细信息")
    @GetMapping("/me")
    public Result<User> getCurrentUser(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            String username = auth.getName();
            User user = userService.findByUsername(username);
            if (user != null) {
                // 移除敏感信息
                user.setPassword(null);
                return Result.success("获取用户信息成功", user);
            }
        }
        return Result.error("未找到当前用户信息");
    }

}