# Docker镜像加速器配置说明

如果在启动服务时遇到镜像拉取失败的问题（如 "failed to resolve reference" 错误），请按照以下步骤配置Docker镜像加速器。

## 配置方法

### 1. Docker Desktop 配置

1. 打开 Docker Desktop 应用
2. 点击右上角的设置图标（Settings）
3. 选择 "Docker Engine" 选项卡
4. 在编辑器中添加或修改以下配置：

```json
{
  "registry-mirrors": [
    "https://hub-mirror.c.163.com",
    "https://mirror.baidubce.com",
    "https://docker.mirrors.ustc.edu.cn",
    "https://registry.docker-cn.com"
  ],
  "insecure-registries": [],
  "debug": false,
  "experimental": false
}
```

5. 点击 "Apply & Restart" 保存并重启Docker

### 2. 阿里云镜像加速器（推荐）

1. 访问阿里云容器镜像服务：https://cr.console.aliyun.com/
2. 注册阿里云账号并登录
3. 在镜像加速器页面获取专属加速器地址
4. 将加速器地址添加到Docker Engine配置中：

```json
{
  "registry-mirrors": [
    "你的阿里云加速器地址"
  ]
}
```

### 3. 使用国内镜像配置文件

本项目提供了使用国内镜像的docker-compose配置文件，可尝试使用：

```bash
# 启动服务（使用国内镜像配置）
docker-compose -f docker-compose-cn.yml up -d

# 查看服务状态
docker-compose -f docker-compose-cn.yml ps

# 查看日志
docker-compose -f docker-compose-cn.yml logs -f wind_tunnel_backend

# 停止服务
docker-compose -f docker-compose-cn.yml down
```

### 4. 常见问题解决

如果仍然遇到镜像拉取问题，可尝试以下方法：

1. 清理Docker缓存：
```bash
docker system prune -a
```

2. 手动拉取镜像：
```bash
docker pull mysql:8.0
docker pull mongo:5.0
docker pull redis:7.0-alpine
docker pull rabbitmq:3.11-management
docker pull nacos/nacos-server:v2.2.3
```

3. 检查网络连接，确保能够访问Docker Hub

## 启动服务

配置完成后，使用以下命令启动服务：

```bash
./start.sh
```

## 验证服务

服务启动后，可以通过以下方式验证：

1. 访问应用：http://localhost:8080
2. 访问API文档：http://localhost:8080/swagger-ui.html
3. 检查服务状态：`docker-compose ps`
4. 查看应用日志：`docker-compose logs wind_tunnel_backend`

## 故障排除

如果服务启动失败，请检查：

1. Docker是否正常运行
2. 端口是否被占用（8080, 3306, 27017, 6379, 5672, 8848）
3. 磁盘空间是否充足
4. 防火墙设置

如需停止服务，运行：
```bash
./stop.sh
```