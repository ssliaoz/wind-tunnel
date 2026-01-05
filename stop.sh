#!/bin/bash

echo "停止智慧风洞管理系统"
echo "====================="

# 检查Docker Compose是否可用
if ! [ -x "$(command -v docker-compose)" ]; then
  DOCKER_COMPOSE_CMD="docker compose"
else
  DOCKER_COMPOSE_CMD="docker-compose"
fi

# 检查配置文件
cd /Users/liaozeng/work/QoderWorkSpace/wind-tunnel

if [ -f "docker-compose-official.yml" ]; then
  COMPOSE_FILE="-f docker-compose-official.yml"
elif [ -f "docker-compose-raw.yml" ]; then
  COMPOSE_FILE="-f docker-compose-raw.yml"
elif [ -f "docker-compose-cn.yml" ]; then
  COMPOSE_FILE="-f docker-compose-cn.yml"
else
  COMPOSE_FILE="-f docker-compose.yml"
fi

# 停止服务
$DOCKER_COMPOSE_CMD $COMPOSE_FILE down

echo "服务已停止"