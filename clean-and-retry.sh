#!/bin/bash

echo "清理Docker环境并重试镜像拉取"
echo "============================="

# 检查Docker是否安装
if ! [ -x "$(command -v docker)" ]; then
  echo "错误: Docker未安装，请先安装Docker"
  exit 1
fi

# 清理Docker缓存
echo "清理Docker缓存..."
docker system prune -f

# 清理构建缓存
docker builder prune -f

# 停止所有运行的容器
echo "停止所有运行的容器..."
docker stop $(docker ps -aq) 2>/dev/null

# 删除所有停止的容器
echo "删除所有停止的容器..."
docker rm $(docker ps -aq) 2>/dev/null

# 删除所有未使用的镜像
echo "删除所有未使用的镜像..."
docker rmi $(docker images -q) 2>/dev/null

echo "清理完成，开始拉取镜像..."

# 拉取关键镜像
docker pull mysql:8.0
if [ $? -ne 0 ]; then
  echo "MySQL镜像拉取失败"
else
  echo "MySQL镜像拉取成功"
fi

docker pull mongo:5.0
if [ $? -ne 0 ]; then
  echo "MongoDB镜像拉取失败"
else
  echo "MongoDB镜像拉取成功"
fi

docker pull redis:7.0-alpine
if [ $? -ne 0 ]; then
  echo "Redis镜像拉取失败"
else
  echo "Redis镜像拉取成功"
fi

docker pull rabbitmq:3.11-management
if [ $? -ne 0 ]; then
  echo "RabbitMQ镜像拉取失败"
else
  echo "RabbitMQ镜像拉取成功"
fi

docker pull nacos/nacos-server:v2.2.3
if [ $? -ne 0 ]; then
  echo "Nacos镜像拉取失败"
else
  echo "Nacos镜像拉取成功"
fi

echo ""
echo "镜像拉取完成，您可以运行 ./start.sh 或 ./start-simple.sh 启动服务"