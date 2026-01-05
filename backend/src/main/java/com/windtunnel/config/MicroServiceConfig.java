package com.windtunnel.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * 微服务配置类
 * 
 * 启用微服务相关功能，包括服务发现、负载均衡、Feign客户端等
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Configuration
@EnableFeignClients(basePackages = "com.windtunnel.feign")
public class MicroServiceConfig {

}