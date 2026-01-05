#!/bin/bash

echo "修复Docker镜像拉取问题"
echo "====================="

# 检查是否在macOS上运行
if [[ "$OSTYPE" == "darwin"* ]]; then
    echo "检测到macOS系统"
    echo "请按以下步骤操作："
    echo ""
    echo "1. 打开 Docker Desktop 应用"
    echo "2. 点击右上角的 Docker 图标"
    echo "3. 选择 'Dashboard'"
    echo "4. 点击右上角的设置图标 (Settings)"
    echo "5. 选择 'Docker Engine' 选项卡"
    echo "6. 将以下配置复制到编辑器中（替换所有现有内容）："
    echo ""
    echo "{"
    echo "  \"debug\": false,"
    echo "  \"experimental\": false,"
    echo "  \"insecure-registries\": [],"
    echo "  \"registry-mirrors\": ["
    echo "    \"https://hub-mirror.c.163.com\","
    echo "    \"https://mirror.baidubce.com\","
    echo "    \"https://docker.mirrors.ustc.edu.cn\""
    echo "  ]"
    echo "}"
    echo ""
    echo "7. 点击 'Apply & Restart' 按钮重启 Docker"
    echo ""
    echo "注意：请确保移除有问题的阿里云镜像加速器地址"
else
    echo "检测到非macOS系统"
    echo "请编辑 ~/.docker/daemon.json 文件，移除有问题的阿里云镜像加速器地址"
    echo "然后重启 Docker 服务：sudo systemctl restart docker"
fi

echo ""
echo "配置修复后，请运行以下命令之一："
echo "./start.sh"
echo "或"
echo "./start-simple.sh"