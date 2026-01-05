package com.windtunnel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 智慧风洞管理系统主应用类
 * 
 * 该应用是一个集成化的管理平台，旨在对风洞试验的全生命周期、
 * 设备资产、安全风险、知识库及系统基础进行数字化管理。
 * 系统核心功能围绕试验流程、设备运维与数据分析展开。
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@SpringBootApplication
@EnableFeignClients
public class WindTunnelApplication {

    public static void main(String[] args) {
        SpringApplication.run(WindTunnelApplication.class, args);
    }

}