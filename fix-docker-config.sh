#!/bin/bash

echo "创建临时Docker配置以绕过镜像拉取问题"
echo "====================================="

# 创建临时Docker配置，不使用有问题的镜像加速器
cat > /tmp/daemon.json << EOF
{
  "debug": false,
  "experimental": false,
  "insecure-registries": [],
  "registry-mirrors": [
    "https://hub-mirror.c.163.com",
    "https://mirror.baidubce.com",
    "https://docker.mirrors.ustc.edu.cn"
  ]
}
EOF

echo "临时配置已创建在 /tmp/daemon.json"
echo ""
echo "请按以下步骤操作："
echo "1. 打开 Docker Desktop 设置"
echo "2. 选择 Docker Engine 选项卡"
echo "3. 将以下内容复制到配置中（替换现有内容）："
echo ""
cat /tmp/daemon.json
echo ""
echo "4. 点击 Apply & Restart 重启 Docker"
echo ""
echo "重启后，再运行 ./start.sh 或 ./start-simple.sh 启动服务"