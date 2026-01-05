package com.windtunnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.windtunnel.common.Constants;
import com.windtunnel.common.Result;
import com.windtunnel.entity.User;
import com.windtunnel.repository.UserRepository;
import com.windtunnel.service.UserService;
import com.windtunnel.service.base.MyBatisBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 用户服务实现类
 * 
 * 实现用户相关的业务逻辑处理方法
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Service
public class UserServiceImpl extends MyBatisBaseService<UserRepository, User, Long> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        if (!StringUtils.hasText(username)) {
            return null;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("deleted", 0); // 未删除的用户
        return userRepository.selectOne(queryWrapper);
    }

    @Override
    public Result<String> login(String username, String password) {
        log.info("用户登录，用户名: {}", username);
        
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return Result.error("用户名或密码不能为空");
        }

        User user = findByUsername(username);
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 验证密码（实际项目中应使用更安全的加密方式）
        String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!user.getPassword().equals(encryptedPassword)) {
            return Result.error("密码错误");
        }

        if (Constants.UserStatus.DISABLED.equals(user.getStatus())) {
            return Result.error("用户已被禁用");
        }

        // 登录成功，返回用户信息或token（这里简化为返回成功消息）
        return Result.success("登录成功", user.getId().toString());
    }

    @Override
    public Result<User> register(User user) {
        log.info("用户注册，用户名: {}", user.getUsername());
        
        if (!StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())) {
            return Result.error("用户名和密码不能为空");
        }

        // 检查用户名是否已存在
        User existingUser = findByUsername(user.getUsername());
        if (existingUser != null) {
            return Result.error("用户名已存在");
        }

        // 加密密码
        String encryptedPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(encryptedPassword);

        // 设置默认状态
        if (user.getStatus() == null) {
            user.setStatus(Constants.UserStatus.ENABLED);
        }

        // 设置默认值
        if (user.getLaboratoryId() == null) {
            user.setLaboratoryId(0L); // 默认实验室
        }

        int result = userRepository.insert(user);
        if (result > 0) {
            log.info("用户注册成功，用户ID: {}", user.getId());
            return Result.success("注册成功", user);
        } else {
            log.error("用户注册失败，用户名: {}", user.getUsername());
            return Result.error("注册失败");
        }
    }

    @Override
    public Result<User> updateUserInfo(User user) {
        log.info("更新用户信息，用户ID: {}", user.getId());
        
        if (user.getId() == null) {
            return Result.error("用户ID不能为空");
        }

        // 查询原用户信息
        User existingUser = userRepository.selectById(user.getId());
        if (existingUser == null) {
            return Result.error("用户不存在");
        }

        // 更新用户信息（除了密码）
        existingUser.setRealName(user.getRealName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setDepartmentId(user.getDepartmentId());
        existingUser.setRoleId(user.getRoleId());
        existingUser.setLaboratoryId(user.getLaboratoryId());
        existingUser.setRemark(user.getRemark());

        int result = userRepository.updateById(existingUser);
        if (result > 0) {
            log.info("用户信息更新成功，用户ID: {}", existingUser.getId());
            return Result.success("更新成功", existingUser);
        } else {
            log.error("用户信息更新失败，用户ID: {}", existingUser.getId());
            return Result.error("更新失败");
        }
    }

    @Override
    public Result<Boolean> updateUserStatus(Long userId, Integer status) {
        log.info("更新用户状态，用户ID: {}, 状态: {}", userId, status);
        
        if (userId == null || status == null) {
            return Result.error("用户ID和状态不能为空");
        }

        // 检查状态值是否合法
        if (!Constants.UserStatus.ENABLED.equals(status) && !Constants.UserStatus.DISABLED.equals(status)) {
            return Result.error("状态值不合法");
        }

        User user = new User();
        user.setId(userId);
        user.setStatus(status);

        int result = userRepository.updateById(user);
        if (result > 0) {
            log.info("用户状态更新成功，用户ID: {}", userId);
            return Result.success("状态更新成功", true);
        } else {
            log.error("用户状态更新失败，用户ID: {}", userId);
            return Result.error("状态更新失败");
        }
    }

    @Override
    public Result<List<User>> findByLaboratoryId(Long laboratoryId) {
        log.info("根据实验室ID查询用户列表，实验室ID: {}", laboratoryId);
        
        if (laboratoryId == null) {
            return Result.error("实验室ID不能为空");
        }

        List<User> users = userRepository.findByLaboratoryId(laboratoryId);
        return Result.success("查询成功", users);
    }

    @Override
    public Result<List<User>> findByRoleId(Long roleId) {
        log.info("根据角色ID查询用户列表，角色ID: {}", roleId);
        
        if (roleId == null) {
            return Result.error("角色ID不能为空");
        }

        List<User> users = userRepository.findByRoleId(roleId);
        return Result.success("查询成功", users);
    }

    @Override
    public Result<List<User>> findUsers(int page, int size) {
        log.info("分页查询用户列表，页码: {}, 页面大小: {}", page, size);
        
        if (page < 1) {
            page = 1;
        }
        if (size < 1) {
            size = Constants.DefaultValues.DEFAULT_PAGE_SIZE;
        }

        // 这里简化实现，实际项目中应使用分页插件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0); // 未删除的用户
        queryWrapper.orderByDesc("create_time");

        List<User> users = userRepository.selectList(queryWrapper);
        return Result.success("查询成功", users);
    }

    @Override
    public Result<Boolean> deleteUser(Long userId) {
        log.info("删除用户，用户ID: {}", userId);
        
        if (userId == null) {
            return Result.error("用户ID不能为空");
        }

        User user = new User();
        user.setId(userId);
        user.setDeleted(1); // 逻辑删除

        int result = userRepository.updateById(user);
        if (result > 0) {
            log.info("用户删除成功，用户ID: {}", userId);
            return Result.success("删除成功", true);
        } else {
            log.error("用户删除失败，用户ID: {}", userId);
            return Result.error("删除失败");
        }
    }

    @Override
    public Result<Boolean> resetPassword(Long userId) {
        log.info("重置用户密码，用户ID: {}", userId);
        
        if (userId == null) {
            return Result.error("用户ID不能为空");
        }

        User user = new User();
        user.setId(userId);
        // 使用默认密码并加密
        String defaultPassword = DigestUtils.md5DigestAsHex(Constants.DefaultValues.DEFAULT_PASSWORD.getBytes());
        user.setPassword(defaultPassword);

        int result = userRepository.updateById(user);
        if (result > 0) {
            log.info("用户密码重置成功，用户ID: {}", userId);
            return Result.success("密码重置成功", true);
        } else {
            log.error("用户密码重置失败，用户ID: {}", userId);
            return Result.error("密码重置失败");
        }
    }

}