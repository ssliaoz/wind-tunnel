#!/bin/bash

echo "智慧风洞管理系统启动脚本"
echo "==========================="

echo "注意：如果遇到镜像拉取问题，请配置Docker镜像加速器"
echo "推荐配置阿里云或其它国内镜像加速器"
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

# 检查是否有可用的配置文件
echo "检查启动配置..."
if [ -f "docker-compose-official.yml" ]; then
  echo "使用官方镜像配置启动..."
  COMPOSE_FILE="-f docker-compose-official.yml"
elif [ -f "docker-compose-raw.yml" ]; then
  echo "使用raw配置启动..."
  COMPOSE_FILE="-f docker-compose-raw.yml"
elif [ -f "docker-compose-cn.yml" ]; then
  echo "使用国内镜像配置启动..."
  COMPOSE_FILE="-f docker-compose-cn.yml"
else
  echo "使用默认配置启动..."
  COMPOSE_FILE="-f docker-compose.yml"
fi

echo "启动所有服务..."
cd /Users/liaozeng/work/QoderWorkSpace/wind-tunnel

# 启动服务
$DOCKER_COMPOSE_CMD $COMPOSE_FILE up -d

echo ""
echo "服务启动中，请等待约60秒..."
sleep 60

# 显示运行状态
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