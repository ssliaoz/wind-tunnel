#!/bin/bash

echo "智慧风洞管理系统启动脚本（简化版）"
echo "================================="

echo "注意：如果遇到镜像拉取问题，请先配置Docker镜像加速器"
echo ""

# 检查Docker是否安装
if ! [ -x "$(command -v docker)" ]; then
  echo "错误: Docker未安装，请先安装Docker"
  exit 1
fi

# 检查Docker Compose是否可用
if ! [ -x "$(command -v docker-compose)" ]; then
  echo "警告: docker-compose未安装，将使用docker compose (v2)"
  DOCKER_COMPOSE_CMD="docker compose"
else
  DOCKER_COMPOSE_CMD="docker-compose"
fi

# 尝试拉取关键镜像
echo "正在预拉取关键镜像..."

# 尝试从官方源拉取镜像
echo "尝试从官方源拉取镜像..."
docker pull mysql:8.0
docker pull mongo:5.0
docker pull redis:7.0-alpine
docker pull rabbitmq:3.11-management
docker pull nacos/nacos-server:v2.2.3

if [ $? -ne 0 ]; then
  echo "警告: 官方源镜像拉取失败，可能需要配置镜像加速器"
  echo "请参考 Docker配置说明.md 文件进行配置"
  
  # 尝试使用官方镜像配置文件
  if [ -f "docker-compose-official.yml" ]; then
    echo "正在尝试使用官方镜像配置文件启动..."
    COMPOSE_FILE="-f docker-compose-official.yml"
  elif [ -f "docker-compose-raw.yml" ]; then
    echo "正在尝试使用raw配置文件启动..."
    COMPOSE_FILE="-f docker-compose-raw.yml"
  else
    COMPOSE_FILE="-f docker-compose.yml"
  fi
else
  COMPOSE_FILE="-f docker-compose.yml"
fi

echo "启动基础服务（MySQL, MongoDB, Redis, RabbitMQ, Nacos）..."
cd /Users/liaozeng/work/QoderWorkSpace/wind-tunnel

# 启动基础服务
$DOCKER_COMPOSE_CMD $COMPOSE_FILE up -d mysql mongodb redis rabbitmq nacos

echo ""
echo "等待基础服务启动中，请等待约45秒..."
sleep 45

# 检查基础服务状态
echo "检查基础服务状态:"
$DOCKER_COMPOSE_CMD $COMPOSE_FILE ps mysql mongodb redis rabbitmq nacos

echo ""
echo "构建并启动应用服务..."

# 构建应用服务
$DOCKER_COMPOSE_CMD $COMPOSE_FILE build wind-tunnel-backend

# 启动应用服务
$DOCKER_COMPOSE_CMD $COMPOSE_FILE up -d wind-tunnel-backend

echo ""
echo "服务启动中，请再等待约30秒..."
sleep 30

# 显示所有服务状态
echo "当前运行的服务:"
$DOCKER_COMPOSE_CMD $COMPOSE_FILE ps

echo ""
echo "服务启动完成!"
echo "访问地址:"
echo "- 应用服务: http://localhost:8080"
echo "- API文档: http://localhost:8080/swagger-ui.html"
echo "- RabbitMQ管理界面: http://localhost:15672 (用户名: guest, 密码: guest)"
echo "- Nacos管理界面: http://localhost:8848 (用户名: nacos, 密码: nacos)"

echo ""
echo "查看应用日志:"
echo "$DOCKER_COMPOSE_CMD $COMPOSE_FILE logs -f wind_tunnel_backend"