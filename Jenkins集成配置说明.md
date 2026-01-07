# Jenkins 集成配置说明

本文档详细介绍如何在智慧风洞管理系统中集成 Jenkins 进行 CI/CD 流水线部署。

## 1. Jenkins 环境要求

### 系统要求
- 操作系统：Linux/macOS
- Java 版本：Java 8 或更高版本
- Docker
- Kubernetes (kubectl) - 可选，用于 K8s 部署

### 硬件要求
- CPU：至少 2 核
- 内存：至少 4GB
- 磁盘：至少 20GB 可用空间

## 2. 安装 Jenkins

### 使用脚本安装
```bash
# 安装 Jenkins
./jenkins-setup.sh install

# 配置 Jenkins
./jenkins-setup.sh configure

# 安装必需插件
./jenkins-setup.sh plugins
```

### 手动安装步骤
1. 下载 Jenkins WAR 文件或使用包管理器安装
2. 启动 Jenkins 服务
3. 访问 `http://localhost:8080`
4. 使用初始管理员密码完成初始化设置

## 3. Jenkins 插件配置

### 必需插件
- Git Plugin：版本控制集成
- Pipeline：流水线支持
- Docker Pipeline：Docker 集成
- Kubernetes：Kubernetes 部署支持
- Blue Ocean：现代化 UI
- Email Extension：邮件通知
- Slack：Slack 通知集成

### 插件安装方法
```bash
# 使用插件列表文件安装
java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:password install-plugin plugins.txt
```

## 4. 系统配置

### 全局工具配置
1. **JDK 配置**：
   - 添加 JDK 17
   - 设置 JAVA_HOME

2. **Maven 配置**：
   - 添加 Maven 3.8.x
   - 配置 settings.xml

3. **Docker 配置**：
   - 确保 Jenkins 用户有 Docker 执行权限
   - 配置 Docker 证书路径

4. **Kubernetes 配置**：
   - 配置 Kubernetes 集群连接信息
   - 设置服务账户和权限

### 凭据配置
1. **Git 凭据**：用于代码仓库访问
2. **Docker Registry 凭据**：用于镜像推送
3. **Kubernetes 凭据**：用于集群访问
4. **Slack/Email 凭据**：用于通知发送

## 5. 流水线配置

### Jenkinsfile 详解
项目根目录下的 `Jenkinsfile` 包含完整的流水线定义：

```groovy
// 参数化构建
parameters {
    choice(name: 'ENVIRONMENT', choices: ['dev', 'test', 'prod'])
    string(name: 'VERSION', defaultValue: 'latest')
    booleanParam(name: 'SKIP_TESTS', defaultValue: false)
}

// 构建阶段
stages {
    stage('代码检出') { ... }
    stage('代码质量检查') { ... }
    stage('构建应用') { ... }
    stage('构建Docker镜像') { ... }
    stage('推送镜像到仓库') { ... }
    stage('部署到Kubernetes') { ... }
    stage('健康检查') { ... }
}
```

### 流水线阶段说明
1. **代码检出**：从 Git 仓库检出代码
2. **代码质量检查**：执行静态代码分析和安全扫描
3. **构建应用**：编译和打包应用程序
4. **构建Docker镜像**：创建 Docker 镜像
5. **推送镜像**：推送到镜像仓库
6. **部署**：部署到目标环境
7. **健康检查**：验证服务状态

## 6. 多环境部署

### 环境配置
- **开发环境 (dev)**：单副本，较低资源配置
- **测试环境 (test)**：双副本，中等资源配置
- **生产环境 (prod)**：三副本，高可用配置

### 配置差异化
根据不同环境自动调整：
- Pod 副本数量
- 资源限制 (CPU/Memory)
- 环境变量
- 健康检查参数

## 7. 监控和通知

### 构建通知
- Slack 通知：构建成功/失败
- 邮件通知：详细构建报告
- 钉钉通知：可选配置

### 部署监控
- 服务健康检查
- 性能指标监控
- 日志收集和分析

## 8. 安全配置

### 访问控制
- 用户认证和授权
- 角色权限管理
- SSH 密钥管理

### 安全最佳实践
- 定期更新插件
- 最小权限原则
- 敏感信息加密存储
- 审计日志启用

## 9. 故障排除

### 常见问题
1. **权限问题**：确保 Jenkins 用户有必要的系统权限
2. **网络问题**：检查防火墙和代理设置
3. **资源不足**：增加 JVM 堆内存和系统资源
4. **插件冲突**：定期更新和兼容性检查

### 调试方法
- 查看 Jenkins 系统日志
- 检查流水线执行日志
- 验证凭据配置
- 测试网络连通性

## 10. 维护和升级

### 定期维护
- 备份 Jenkins 配置
- 清理旧的构建历史
- 更新插件和 Jenkins 核心

### 升级策略
- 测试环境先行升级
- 逐步推广到生产环境
- 准备回滚计划

## 11. 扩展功能

### 自定义构建步骤
- 集成代码质量工具 (SonarQube)
- 自动化测试执行
- 性能测试集成

### 高级部署策略
- 蓝绿部署
- 金丝雀发布
- 滚动更新策略

通过以上配置，您可以将 Jenkins 完全集成到智慧风洞管理系统的 CI/CD 流程中，实现自动化构建、测试和部署。