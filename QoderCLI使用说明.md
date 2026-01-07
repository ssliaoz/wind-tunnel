# QoderCLI 使用说明

QoderCLI 是智慧风洞管理系统的自动化部署工具，提供构建、部署、管理等功能。

## 功能特性

- 自动化构建项目
- 多环境部署（开发、测试、生产）
- 服务管理（启动、停止、重启、状态查看）
- 日志查看
- 版本回滚
- 环境清理

## 系统要求

- Docker
- Docker Compose
- Java 8+
- Maven (或项目自带的 mvnw)

## 快速开始

### 1. 设置执行权限

```bash
chmod +x qoder-cli.sh
```

### 2. 查看帮助

```bash
./qoder-cli.sh help
```

## 命令说明

### 构建项目

```bash
./qoder-cli.sh build
```

### 部署到不同环境

#### 开发环境

```bash
./qoder-cli.sh deploy:dev
```

#### 测试环境

```bash
./qoder-cli.sh deploy:test
```

#### 生产环境

```bash
./qoder-cli.sh deploy:prod
```

#### Kubernetes 集群

```bash
# 部署到K8s开发环境
./qoder-cli.sh deploy:k8s:dev

# 部署到K8s生产环境
./qoder-cli.sh deploy:k8s:prod
```

### 服务管理

#### 启动服务

```bash
./qoder-cli.sh start
```

#### 停止服务

```bash
./qoder-cli.sh stop
```

#### 重启服务

```bash
./qoder-cli.sh restart
```

#### 查看服务状态

```bash
./qoder-cli.sh status
```

#### 查看服务日志

```bash
./qoder-cli.sh logs
```

#### 查看K8s服务状态

```bash
# 查看K8s部署状态
./qoder-cli.sh k8s:status

# 查看K8s服务日志
./qoder-cli.sh k8s:logs

# 调整K8s服务副本数
./qoder-cli.sh k8s:scale 3
```

#### Jenkins 集成

```bash
# 初始化 Jenkins CI/CD 环境
./qoder-cli.sh jenkins:setup

# 启动 Jenkins 服务
./qoder-cli.sh jenkins:start

# 检查 Jenkins 服务状态
./qoder-cli.sh jenkins:status

# 触发 Jenkins 构建
./qoder-cli.sh jenkins:build -e ENVIRONMENT=prod

# 停止 Jenkins 服务
./qoder-cli.sh jenkins:stop
```

### 其他功能

#### 回滚到指定版本

```bash
./qoder-cli.sh rollback [版本号]
```

#### 清理构建文件

```bash
./qoder-cli.sh clean
```

#### 配置环境

```bash
./qoder-cli.sh config [环境名]
```

## 配置文件

`qoder-config.json` 文件定义了不同环境的部署参数：

```json
{
  "environments": {
    "dev": {
      "name": "Development",
      "composeFile": "docker-compose.yml",
      "profile": "dev",
      "port": 8080,
      "envVars": {
        "SPRING_PROFILES_ACTIVE": "docker",
        "LOG_LEVEL": "DEBUG"
      }
    }
  }
}
```

## 部署流程

1. **构建阶段**：编译代码并构建 Docker 镜像
2. **部署阶段**：停止旧服务，启动新服务
3. **验证阶段**：检查服务状态，确认部署成功

## 环境变量

- `SPRING_PROFILES_ACTIVE`：Spring 配置文件环境
- `LOG_LEVEL`：日志级别
- `JAVA_OPTS`：Java 虚拟机选项（生产环境）

## 故障排除

### 镜像拉取失败

配置 Docker 镜像加速器：

```json
{
  "registry-mirrors": [
    "https://hub-mirror.c.163.com",
    "https://mirror.baidubce.com"
  ]
}
```

### 端口被占用

检查并释放占用的端口（8080, 3306, 27017, 6379, 5672, 8848）

### 权限问题

确保当前用户有 Docker 执行权限：

```bash
sudo usermod -aG docker $USER
```

## 最佳实践

1. **生产环境部署前先在测试环境验证**
2. **定期备份配置文件和数据**
3. **监控服务状态和日志**
4. **使用版本控制管理配置文件**
5. **部署前确认服务器资源充足**

## 安全建议

1. **生产环境使用 HTTPS**
2. **定期更新依赖包**
3. **限制服务访问权限**
4. **启用日志审计**
5. **配置防火墙规则**

## 扩展功能

QoderCLI 支持扩展功能，如：

- 集成 CI/CD 流水线
- 自动化测试集成
- 监控告警集成
- 数据库迁移工具
- 配置热更新
- Kubernetes 部署支持

## CI/CD 集成

QoderCLI 支持与 Jenkins 等 CI/CD 工具集成：

- Jenkins 流水线支持
- 自动化构建和部署
- 多环境部署
- 构建历史管理
- 部署状态监控

### Jenkins 集成

包含完整的 Jenkins 配置：

- Jenkinsfile 流水线定义
- jenkins-setup.sh 安装配置脚本
- plugins.txt 插件列表

## Kubernetes 部署

QoderCLI 支持将应用部署到 Kubernetes 集群：

- 支持开发和生产环境部署
- 自动健康检查
- 支持副本数调整
- 服务状态监控
- 日志查看功能