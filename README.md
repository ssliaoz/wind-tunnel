# 智慧风洞管理系统

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

基于Spring Boot 3.x + Spring Cloud的微服务架构风洞管理系统，支持实时数据采集、处理和分析。

## 项目概述

智慧风洞管理系统是一个综合性的风洞试验管理平台，主要功能包括：

- 实时数据采集：通过TCP/IP接口从CWT1 PC、CWT2 PC、CWT3 PC、AAWT PC、公共动力系统PC等设备获取实时数据
- 实验项目管理：管理风洞试验项目、进度和报告
- 设备管理：管理风洞设备和实验室资源
- 用户权限管理：基于角色的权限控制
- 数据分析与可视化：实时数据监控和历史数据分析

## 技术架构

- **后端框架**: Spring Boot 3.x + Spring Cloud
- **数据存储**: MySQL + MongoDB混合存储
- **实时通信**: Netty TCP服务器
- **安全认证**: JWT + Spring Security
- **微服务治理**: Nacos、Gateway、Feign
- **消息队列**: RabbitMQ/Kafka
- **定时任务**: Quartz
- **API文档**: Swagger/OpenAPI
- **容器化部署**: Docker + Docker Compose

## 项目结构

```
wind-tunnel/
├── backend/                    # 后端代码
│   ├── src/main/java/com/windtunnel/
│   │   ├── command/           # 命令模式实现
│   │   ├── common/            # 通用类
│   │   ├── config/            # 配置类
│   │   ├── controller/        # 控制器层
│   │   ├── entity/            # 实体类
│   │   ├── factory/           # 工厂模式实现
│   │   ├── feign/             # Feign客户端
│   │   ├── job/               # 定时任务
│   │   ├── observer/          # 观察者模式实现
│   │   ├── repository/        # 数据访问层
│   │   ├── service/           # 服务层
│   │   ├── strategy/          # 策略模式实现
│   │   ├── tcp/               # TCP服务器
│   │   ├── util/              # 工具类
│   │   └── WindTunnelApplication.java
│   └── pom.xml
├── Dockerfile                 # Docker构建文件
├── docker-compose.yml         # Docker Compose配置
├── start.sh                   # 启动脚本
├── stop.sh                    # 停止脚本
└── 各种架构设计文档
```

## 设计模式应用

项目中应用了多种设计模式：

- **策略模式**: 数据解析和异常检测策略
- **工厂模式**: 策略工厂
- **模板方法模式**: 基础服务类
- **观察者模式**: 数据变更通知
- **命令模式**: 操作封装

## 快速开始

1. 克隆项目
2. 配置环境变量
3. 构建项目: `mvn clean install`
4. 启动服务: `./start.sh`

## Docker部署

项目支持Docker容器化部署，提供了多种配置文件以适应不同的网络环境：

- `docker-compose.yml`: 标准配置
- `docker-compose-cn.yml`: 国内镜像加速配置
- `docker-compose-official.yml`: 官方镜像配置
- `docker-compose-raw.yml`: 无镜像加速配置

## 许可证

MIT License