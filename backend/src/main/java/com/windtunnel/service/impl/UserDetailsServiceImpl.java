package com.windtunnel.service.impl;

import com.windtunnel.entity.User;
import com.windtunnel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户详情服务实现类
 * 
 * 实现Spring Security的UserDetailsService接口，用于加载用户认证信息
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        // 检查用户状态
        if (user.getStatus() != null && user.getStatus() == 0) { // 0表示禁用
            throw new UsernameNotFoundException("用户已被禁用: " + username);
        }

        // 构建用户权限列表
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 这里可以根据用户的角色添加权限，简化实现中添加一个默认权限
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        // 返回用户详情对象
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

}