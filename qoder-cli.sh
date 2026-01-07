#!/bin/bash

# QoderCLI - 自动化部署工具
# 用于智慧风洞管理系统的自动化构建和部署

set -e  # 遇到错误立即退出

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 默认配置
CONFIG_FILE="qoder-config.json"
DEFAULT_ENV="dev"
K8S_DEPLOYMENT_FILE="k8s-deployment.yaml"
JENKINS_FILE="Jenkinsfile"

# 日志函数
log_info() {
    echo -e "${BLUE}[INFO]${NC} $(date '+%Y-%m-%d %H:%M:%S') - $1"
}

log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $(date '+%Y-%m-%d %H:%M:%S') - $1"
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $(date '+%Y-%m-%d %H:%M:%S') - $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $(date '+%Y-%m-%d %H:%M:%S') - $1"
}

# 检查Jenkins环境
check_jenkins() {
    if ! command -v java >/dev/null 2>&1; then
        log_error "Java 未安装，Jenkins 需要 Java 运行环境"
        exit 1
    fi
    
    # 尝试连接 Jenkins 服务器
    if ! curl -f http://localhost:8080/login >/dev/null 2>&1; then
        log_warn "无法连接到 Jenkins 服务器 (http://localhost:8080)，请确保 Jenkins 已启动"
    fi
}

# 读取配置文件
load_config() {
    local env=${1:-$DEFAULT_ENV}
    
    if [ ! -f "$CONFIG_FILE" ]; then
        log_warn "配置文件 $CONFIG_FILE 不存在，使用默认配置"
        COMPOSE_FILE="docker-compose.yml"
        return
    fi
    
    # 从配置文件中提取环境配置
    if command -v jq >/dev/null 2>&1; then
        COMPOSE_FILE=$(jq -r ".environments.$env.composeFile" $CONFIG_FILE 2>/dev/null || echo "docker-compose.yml")
        PROFILE=$(jq -r ".environments.$env.profile" $CONFIG_FILE 2>/dev/null || echo "docker")
        
        # 设置环境变量
        export SPRING_PROFILES_ACTIVE=$(jq -r ".environments.$env.envVars.SPRING_PROFILES_ACTIVE" $CONFIG_FILE 2>/dev/null || echo "docker")
        export LOG_LEVEL=$(jq -r ".environments.$env.envVars.LOG_LEVEL" $CONFIG_FILE 2>/dev/null || echo "INFO")
        
        JAVA_OPTS=$(jq -r ".environments.$env.envVars.JAVA_OPTS" $CONFIG_FILE 2>/dev/null)
        if [ "$JAVA_OPTS" != "null" ]; then
            export JAVA_OPTS
        fi
    else
        log_warn "jq 命令不可用，使用默认配置"
        COMPOSE_FILE="docker-compose.yml"
    fi
    
    log_info "已加载 $env 环境配置: $COMPOSE_FILE"
}

# 检查Kubernetes环境
check_k8s() {
    if ! command -v kubectl >/dev/null 2>&1; then
        log_error "kubectl 未安装，请先安装 kubectl"
        exit 1
    fi
}

# 显示帮助信息
show_help() {
    echo "QoderCLI - 智慧风洞管理系统自动化部署工具"
    echo ""
    echo "用法: ./qoder-cli.sh [选项]"
    echo ""
    echo "选项:"
    echo "  build                    构建项目"
    echo "  deploy                   部署项目到服务器"
    echo "  deploy:dev               部署到开发环境"
    echo "  deploy:prod              部署到生产环境"
    echo "  deploy:test              部署到测试环境"
    echo "  deploy:k8s               部署到Kubernetes集群"
    echo "  deploy:k8s:dev           部署到Kubernetes开发环境"
    echo "  deploy:k8s:prod          部署到Kubernetes生产环境"
    echo "  start                    启动已部署的服务"
    echo "  stop                     停止运行的服务"
    echo "  restart                  重启服务"
    echo "  status                   查看服务状态"
    echo "  logs                     查看服务日志"
    echo "  rollback [版本号]        回滚到指定版本"
    echo "  clean                    清理构建和部署文件"
    echo "  config [环境]            配置部署环境"
    echo "  k8s:status               查看K8s部署状态"
    echo "  k8s:logs                 查看K8s服务日志"
    echo "  k8s:scale [副本数]       调整K8s服务副本数"
    echo "  jenkins:setup            初始化Jenkins CI/CD环境"
    echo "  jenkins:start            启动Jenkins服务"
    echo "  jenkins:stop             停止Jenkins服务"
    echo "  jenkins:build            触发Jenkins构建"
    echo "  jenkins:status           检查Jenkins服务状态"
    echo "  help                     显示此帮助信息"
    echo ""
    echo "示例:"
    echo "  ./qoder-cli.sh build"
    echo "  ./qoder-cli.sh deploy:dev"
    echo "  ./qoder-cli.sh deploy:prod"
    echo "  ./qoder-cli.sh deploy:k8s:prod"
    echo "  ./qoder-cli.sh jenkins:setup"
    echo "  ./qoder-cli.sh jenkins:build -e ENVIRONMENT=prod"
    echo "  ./qoder-cli.sh logs"
}

# 检查依赖
check_dependencies() {
    log_info "检查依赖..."

    if ! command -v docker &> /dev/null; then
        log_error "Docker 未安装，请先安装 Docker"
        exit 1
    fi

    if ! command -v docker-compose &> /dev/null; then
        log_warn "docker-compose 未安装，将使用 docker compose (v2)"
        DOCKER_COMPOSE_CMD="docker compose"
    else
        DOCKER_COMPOSE_CMD="docker-compose"
    fi

    log_success "依赖检查完成"
}

# 构建项目
build_project() {
    log_info "开始构建项目..."

    if [ -f "pom.xml" ]; then
        log_info "检测到 Maven 项目，执行构建..."
        if [ -f "mvnw" ]; then
            ./mvnw clean package -DskipTests
        else
            mvn clean package -DskipTests
        fi
    else
        log_error "未找到 pom.xml 文件，无法构建项目"
        exit 1
    fi

    log_info "构建 Docker 镜像..."
    $DOCKER_COMPOSE_CMD -f docker-compose.yml build wind-tunnel-backend

    log_success "项目构建完成"
}

# 配置环境
cfg_env() {
    local env=${1:-"dev"}
    log_info "配置 $env 环境..."
    
    load_config $env
    
    log_success "$env 环境配置完成"
}

# 部署到指定环境
deploy_to_env() {
    local env=${1:-"dev"}
    log_info "部署到 $env 环境..."

    # 加载环境配置
    load_config $env

    # 检查配置文件是否存在
    if [ ! -f "$COMPOSE_FILE" ]; then
        log_error "配置文件 $COMPOSE_FILE 不存在"
        exit 1
    fi

    # 构建项目
    build_project

    # 停止现有服务
    if $DOCKER_COMPOSE_CMD -f $COMPOSE_FILE ps | grep -q "wind_tunnel_backend"; then
        log_info "停止现有服务..."
        $DOCKER_COMPOSE_CMD -f $COMPOSE_FILE down
    fi

    # 启动服务
    log_info "启动服务..."
    $DOCKER_COMPOSE_CMD -f $COMPOSE_FILE up -d

    # 等待服务启动
    log_info "等待服务启动中，请等待约60秒..."
    sleep 60

    # 检查服务状态
    log_info "检查服务状态..."
    $DOCKER_COMPOSE_CMD -f $COMPOSE_FILE ps

    log_success "$env 环境部署完成"
}

# 启动服务
start_services() {
    log_info "启动服务..."
    
    # 加载默认开发环境配置
    load_config "dev"

    $DOCKER_COMPOSE_CMD -f $COMPOSE_FILE up -d

    log_success "服务启动完成"
}

# 停止服务
stop_services() {
    log_info "停止服务..."
    
    # 加载默认开发环境配置以获取正确的compose文件
    load_config "dev"

    $DOCKER_COMPOSE_CMD -f $COMPOSE_FILE down

    log_success "服务已停止"
}

# 重启服务
restart_services() {
    log_info "重启服务..."
    stop_services
    sleep 10
    start_services
    log_success "服务重启完成"
}

# 查看服务状态
check_status() {
    log_info "检查服务状态..."
    
    # 加载默认开发环境配置
    load_config "dev"

    $DOCKER_COMPOSE_CMD -f $COMPOSE_FILE ps

    log_success "状态检查完成"
}

# 查看服务日志
show_logs() {
    log_info "查看服务日志..."
    
    # 加载默认开发环境配置
    load_config "dev"

    $DOCKER_COMPOSE_CMD -f $COMPOSE_FILE logs -f wind_tunnel_backend
}

# 部署到Kubernetes
deploy_to_k8s() {
    local env=${1:-"dev"}
    log_info "部署到Kubernetes $env 环境..."

    check_k8s
    
    if [ ! -f "$K8S_DEPLOYMENT_FILE" ]; then
        log_error "Kubernetes部署文件 $K8S_DEPLOYMENT_FILE 不存在"
        exit 1
    fi
    
    # 应用Kubernetes部署
    kubectl apply -f $K8S_DEPLOYMENT_FILE
    
    log_info "等待服务启动中..."
    sleep 30
    
    # 检查部署状态
    kubectl get pods -n wind-tunnel
    
    log_success "Kubernetes $env 环境部署完成"
}

# 查看Kubernetes状态
k8s_status() {
    log_info "检查Kubernetes部署状态..."
    
    check_k8s
    
    kubectl get deployments,pods,services -n wind-tunnel
    
    log_success "K8s状态检查完成"
}

# 查看Kubernetes日志
k8s_logs() {
    log_info "查看Kubernetes服务日志..."
    
    check_k8s
    
    # 获取pod名称
    POD_NAME=$(kubectl get pods -n wind-tunnel -l app=wind-tunnel-backend -o jsonpath='{.items[0].metadata.name}' 2>/dev/null)
    
    if [ -n "$POD_NAME" ]; then
        kubectl logs -f $POD_NAME -n wind-tunnel
    else
        log_error "未找到 wind-tunnel-backend pod"
    fi
}

# 调整Kubernetes副本数
k8s_scale() {
    local replicas=${1:-"1"}
    log_info "调整K8s服务副本数到: $replicas"
    
    check_k8s
    
    kubectl scale deployment wind-tunnel-backend -n wind-tunnel --replicas=$replicas
    
    log_success "副本数已调整为: $replicas"
}

# 初始化Jenkins环境
jenkins_setup() {
    log_info "初始化 Jenkins CI/CD 环境..."
    
    if [ ! -f "$JENKINS_FILE" ]; then
        log_error "Jenkinsfile 不存在: $JENKINS_FILE"
        exit 1
    fi
    
    if [ ! -f "jenkins-setup.sh" ]; then
        log_error "jenkins-setup.sh 脚本不存在"
        exit 1
    fi
    
    log_info "Jenkins 环境初始化检查完成"
    log_info "请运行: ./jenkins-setup.sh install 来安装 Jenkins"
    log_info "请运行: ./jenkins-setup.sh setup-pipeline 来配置流水线"
    
    log_success "Jenkins 环境初始化完成"
}

# 启动Jenkins服务
jenkins_start() {
    log_info "启动 Jenkins 服务..."
    
    check_jenkins
    
    if command -v systemctl >/dev/null 2>&1; then
        sudo systemctl start jenkins
    elif command -v brew >/dev/null 2>&1 && [[ "$OSTYPE" == "darwin"* ]]; then
        brew services start jenkins
    else
        log_error "无法启动 Jenkins，请手动启动"
        exit 1
    fi
    
    # 等待 Jenkins 启动
    log_info "等待 Jenkins 启动完成..."
    until curl -f http://localhost:8080/login >/dev/null 2>&1; do
        sleep 5
        log_info "等待 Jenkins 启动中..."
    done
    
    log_success "Jenkins 服务启动完成"
    log_info "访问地址: http://localhost:8080"
    log_info "默认凭据: admin / admin123 (请在首次访问后更改密码)"
}

# 停止Jenkins服务
jenkins_stop() {
    log_info "停止 Jenkins 服务..."
    
    if command -v systemctl >/dev/null 2>&1; then
        sudo systemctl stop jenkins
    elif command -v brew >/dev/null 2>&1 && [[ "$OSTYPE" == "darwin"* ]]; then
        brew services stop jenkins
    else
        log_error "无法停止 Jenkins，请手动停止"
        exit 1
    fi
    
    log_success "Jenkins 服务已停止"
}

# 触发Jenkins构建
jenkins_build() {
    log_info "触发 Jenkins 构建..."
    
    check_jenkins
    
    # 检查 Jenkins 是否运行
    if ! curl -f http://localhost:8080/login >/dev/null 2>&1; then
        log_error "Jenkins 服务未运行，请先启动 Jenkins"
        exit 1
    fi
    
    # 获取 Jenkins crumb token
    JENKINS_CRUMB=$(curl -s --user admin:admin123 'http://localhost:8080/crumbIssuer/api/json' | jq -r '.crumb' 2>/dev/null)
    
    # 触发构建
    curl -X POST \
        --user admin:admin123 \
        --header "Jenkins-Crumb:$JENKINS_CRUMB" \
        "http://localhost:8080/job/wind-tunnel/buildWithParameters?ENVIRONMENT=${PARAM_ENV:-dev}&VERSION=${PARAM_VERSION:-latest}&SKIP_TESTS=${PARAM_SKIP_TESTS:-false}"
    
    log_success "Jenkins 构建已触发"
    log_info "请访问 http://localhost:8080/job/wind-tunnel/ 查看构建状态"
}

# 检查Jenkins状态
jenkins_status() {
    log_info "检查 Jenkins 服务状态..."
    
    if curl -f http://localhost:8080/login >/dev/null 2>&1; then
        log_success "Jenkins 服务正在运行"
        log_info "Jenkins URL: http://localhost:8080"
        
        # 获取 Jenkins 版本信息
        JENKINS_VERSION=$(curl -s --user admin:admin123 http://localhost:8080/api/json | jq -r '.version' 2>/dev/null)
        if [ -n "$JENKINS_VERSION" ] && [ "$JENKINS_VERSION" != "null" ]; then
            log_info "Jenkins 版本: $JENKINS_VERSION"
        fi
    else
        log_warn "Jenkins 服务未运行"
    fi
}

# 回滚到指定版本
rollback() {
    local version=${1:-"latest"}
    log_info "回滚到版本: $version"

    log_warn "回滚功能需要预先保存的镜像版本，当前实现为重启最新版本"
    restart_services

    log_success "回滚完成"
}

# 清理构建文件
clean_build() {
    log_info "清理构建和部署文件..."

    # 清理 Maven 构建文件
    if [ -f "pom.xml" ]; then
        if [ -f "mvnw" ]; then
            ./mvnw clean
        else
            mvn clean
        fi
    fi

    # 停止所有服务
    for file in docker-compose*.yml; do
        if [ -f "$file" ]; then
            $DOCKER_COMPOSE_CMD -f $file down
        fi
    done

    # 清理 Docker 构建缓存
    docker system prune -f

    log_success "清理完成"
}

# 主函数
main() {
    if [ $# -eq 0 ]; then
        show_help
        exit 1
    fi

    # 检查依赖
    check_dependencies

    case $1 in
        "build")
            build_project
            ;;
        "deploy")
            deploy_to_env "dev"
            ;;
        "deploy:dev")
            deploy_to_env "dev"
            ;;
        "deploy:prod")
            deploy_to_env "prod"
            ;;
        "deploy:test")
            deploy_to_env "test"
            ;;
        "deploy:k8s"|"deploy:k8s:dev")
            deploy_to_k8s "dev"
            ;;
        "deploy:k8s:prod")
            deploy_to_k8s "prod"
            ;;
        "start")
            start_services
            ;;
        "stop")
            stop_services
            ;;
        "restart")
            restart_services
            ;;
        "status")
            check_status
            ;;
        "logs")
            show_logs
            ;;
        "k8s:status")
            k8s_status
            ;;
        "k8s:logs")
            k8s_logs
            ;;
        "k8s:scale")
            k8s_scale $2
            ;;
        "jenkins:setup")
            jenkins_setup
            ;;
        "jenkins:start")
            jenkins_start
            ;;
        "jenkins:stop")
            jenkins_stop
            ;;
        "jenkins:build")
            # 解析参数
            if [[ $2 == -* ]]; then
                # 处理参数如 -e ENVIRONMENT=prod
                while [[ $# -gt 1 ]]; do
                    key="${2}"
                    case $key in
                        -e|--environment)
                            PARAM_ENV="${3}"
                            shift
                            shift
                            ;;
                        -v|--version)
                            PARAM_VERSION="${3}"
                            shift
                            shift
                            ;;
                        -s|--skip-tests)
                            PARAM_SKIP_TESTS="${3}"
                            shift
                            shift
                            ;;
                        *)
                            shift
                            ;;
                    esac
                done
            fi
            jenkins_build
            ;;
        "jenkins:status")
            jenkins_status
            ;;
        "rollback")
            rollback $2
            ;;
        "clean")
            clean_build
            ;;
        "config")
            cfg_env $2
            ;;
        "help"|"--help"|"-h")
            show_help
            ;;
        *)
            log_error "未知命令: $1"
            show_help
            exit 1
            ;;
    esac
}

# 执行主函数
main "$@"