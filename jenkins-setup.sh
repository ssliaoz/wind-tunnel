#!/bin/bash

# Jenkins 设置脚本
# 用于在服务器上安装和配置 Jenkins 以支持智慧风洞管理系统的 CI/CD

set -e  # 遇到错误立即退出

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

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

# 显示帮助信息
show_help() {
    echo "Jenkins 设置脚本"
    echo ""
    echo "用法: ./jenkins-setup.sh [选项]"
    echo ""
    echo "选项:"
    echo "  install                   安装 Jenkins"
    echo "  configure                 配置 Jenkins"
    echo "  setup-pipeline           设置流水线"
    echo "  start                    启动 Jenkins"
    echo "  stop                     停止 Jenkins"
    echo "  restart                  重启 Jenkins"
    echo "  status                   检查 Jenkins 状态"
    echo "  plugins                  安装必需的插件"
    echo "  cleanup                  清理 Jenkins"
    echo "  help                     显示此帮助信息"
    echo ""
    echo "示例:"
    echo "  ./jenkins-setup.sh install"
    echo "  ./jenkins-setup.sh configure"
    echo "  ./jenkins-setup.sh setup-pipeline"
}

# 检查依赖
check_dependencies() {
    log_info "检查依赖..."

    if ! command -v java &> /dev/null; then
        log_error "Java 未安装，请先安装 Java 8 或更高版本"
        exit 1
    fi

    if ! command -v docker &> /dev/null; then
        log_error "Docker 未安装，请先安装 Docker"
        exit 1
    fi

    if ! command -v kubectl &> /dev/null; then
        log_warn "kubectl 未安装，Kubernetes 部署功能将受限"
    fi

    log_success "依赖检查完成"
}

# 安装 Jenkins
install_jenkins() {
    log_info "开始安装 Jenkins..."

    if [[ "$OSTYPE" == "linux-gnu"* ]]; then
        # Ubuntu/Debian
        if command -v apt-get &> /dev/null; then
            sudo apt-get update
            sudo apt-get install -y fontconfig openjdk-11-jre
            wget -q -O - https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo apt-key add -
            sudo sh -c 'echo deb https://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'
            sudo apt-get update
            sudo apt-get install -y jenkins
        # CentOS/RHEL
        elif command -v yum &> /dev/null; then
            sudo yum update -y
            sudo yum install -y java-11-openjdk-devel
            sudo wget -O /etc/yum.repos.d/jenkins.repo \
                https://pkg.jenkins.io/redhat-stable/jenkins.repo
            sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io.key
            sudo yum install -y jenkins
        fi
    elif [[ "$OSTYPE" == "darwin"* ]]; then
        # macOS
        if command -v brew &> /dev/null; then
            brew install jenkins
        else
            log_error "请先安装 Homebrew 或手动安装 Jenkins"
            exit 1
        fi
    else
        log_error "不支持的操作系统"
        exit 1
    fi

    log_success "Jenkins 安装完成"
}

# 配置 Jenkins
configure_jenkins() {
    log_info "配置 Jenkins..."

    # 创建 Jenkins 配置目录
    sudo mkdir -p /var/lib/jenkins/init.groovy.d/
    
    # 创建安全配置脚本
    cat << 'EOF' | sudo tee /var/lib/jenkins/init.groovy.d/basic-security.groovy
import jenkins.model.*
import hudson.security.*

def instance = Jenkins.instance

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount("admin", "admin123")
instance.setSecurityRealm(hudsonRealm)

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
strategy.setAllowAnonymousRead(false)
instance.setAuthorizationStrategy(strategy)

instance.save()
EOF

    # 重启 Jenkins 以应用配置
    if systemctl is-active --quiet jenkins; then
        sudo systemctl restart jenkins
    fi

    log_success "Jenkins 配置完成"
}

# 安装必需插件
install_plugins() {
    log_info "安装必需的 Jenkins 插件..."

    # 获取 Jenkins CLI jar 如果可用
    if [ -f "/usr/share/jenkins/jenkins.war" ]; then
        JENKINS_CLI_JAR="/usr/share/jenkins/jenkins.war"
    else
        # 下载 Jenkins CLI
        wget -q -O jenkins-cli.jar http://localhost:8080/jnlpJars/jenkins-cli.jar
        JENKINS_CLI_JAR="jenkins-cli.jar"
    fi

    # 定义必需的插件列表
    PLUGINS=(
        "git"
        "docker-workflow"
        "kubernetes"
        "workflow-aggregator"
        "blueocean"
        "pipeline-stage-view"
        "slack"
        "email-ext"
        "build-timeout"
        "timestamper"
        "ansicolor"
        "docker-build-publish"
        "promoted-builds"
        "parameter-separator"
    )

    # 安装插件
    for plugin in "${PLUGINS[@]}"; do
        log_info "安装插件: $plugin"
        java -jar $JENKINS_CLI_JAR -s http://localhost:8080 -auth admin:admin123 install-plugin $plugin || true
    done

    # 重启 Jenkins 以应用插件
    sudo systemctl restart jenkins

    log_success "插件安装完成"
}

# 设置流水线
setup_pipeline() {
    log_info "设置 Jenkins 流水线..."

    # 检查 Jenkins 是否正在运行
    if ! sudo systemctl is-active --quiet jenkins; then
        log_error "Jenkins 未运行，请先启动 Jenkins"
        exit 1
    fi

    # 等待 Jenkins 启动完成
    log_info "等待 Jenkins 启动完成..."
    until curl -f http://localhost:8080/login > /dev/null 2>&1; do
        sleep 5
    done

    # 创建项目目录
    sudo mkdir -p /var/lib/jenkins/jobs/wind-tunnel/

    # 创建项目配置
    cat << 'EOF' | sudo tee /var/lib/jenkins/jobs/wind-tunnel/config.xml
<?xml version='1.1' encoding='UTF-8'?>
<flow-definition plugin="workflow-job@2.40">
  <actions>
    <org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobAction plugin="pipeline-model-definition@1.8.4"/>
    <org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobPropertyTrackerAction plugin="pipeline-model-definition@1.8.4">
      <jobProperties/>
      <triggers/>
      <parameters>
        <string>ENVIRONMENT</string>
        <string>VERSION</string>
      </parameters>
      <options/>
    </org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobPropertyTrackerAction>
  </actions>
  <description>智慧风洞管理系统 CI/CD 流水线</description>
  <keepDependencies>false</keepDependencies>
  <properties>
    <jenkins.model.BuildDiscarderProperty>
      <strategy class="hudson.tasks.LogRotator">
        <daysToKeep>-1</daysToKeep>
        <numToKeep>10</numToKeep>
        <artifactDaysToKeep>-1</artifactDaysToKeep>
        <artifactNumToKeep>-1</artifactNumToKeep>
      </strategy>
    </jenkins.model.BuildDiscarderProperty>
    <org.jenkinsci.plugins.workflow.job.properties.DisableConcurrentBuildsJobProperty/>
    <com.coravy.hudson.plugins.github.GithubProjectProperty plugin="github@1.33.1">
      <projectUrl>https://github.com/your-org/wind-tunnel/</projectUrl>
      <displayName></displayName>
    </com.coravy.hudson.plugins.github.GithubProjectProperty>
  </properties>
  <definition class="org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition" plugin="workflow-cps@2.90">
    <scm class="hudson.plugins.git.GitSCM" plugin="git@4.7.1">
      <configVersion>2</configVersion>
      <userRemoteConfigs>
        <hudson.plugins.git.UserRemoteConfig>
          <url>https://github.com/your-org/wind-tunnel.git</url>
        </hudson.plugins.git.UserRemoteConfig>
      </userRemoteConfigs>
      <branches>
        <hudson.plugins.git.BranchSpec>
          <name>*/main</name>
        </hudson.plugins.git.BranchSpec>
      </branches>
      <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>
      <submoduleCfg class="list"/>
      <extensions/>
    </scm>
    <scriptPath>Jenkinsfile</scriptPath>
    <lightweight>true</lightweight>
  </definition>
  <triggers/>
  <disabled>false</disabled>
</flow-definition>
EOF

    # 重启 Jenkins 以加载配置
    sudo systemctl restart jenkins

    log_success "流水线设置完成"
}

# 启动 Jenkins
start_jenkins() {
    log_info "启动 Jenkins..."

    sudo systemctl start jenkins
    sudo systemctl enable jenkins

    # 等待 Jenkins 启动
    log_info "等待 Jenkins 启动完成..."
    until curl -f http://localhost:8080/login > /dev/null 2>&1; do
        sleep 5
        log_info "等待 Jenkins 启动中..."
    done

    log_success "Jenkins 启动完成"
    log_info "访问地址: http://localhost:8080"
    log_info "默认凭据: admin / admin123 (请在首次访问后更改密码)"
}

# 停止 Jenkins
stop_jenkins() {
    log_info "停止 Jenkins..."

    sudo systemctl stop jenkins

    log_success "Jenkins 已停止"
}

# 重启 Jenkins
restart_jenkins() {
    log_info "重启 Jenkins..."

    sudo systemctl restart jenkins

    # 等待 Jenkins 重启
    log_info "等待 Jenkins 重启完成..."
    until curl -f http://localhost:8080/login > /dev/null 2>&1; do
        sleep 5
        log_info "等待 Jenkins 重启中..."
    done

    log_success "Jenkins 重启完成"
}

# 检查 Jenkins 状态
check_status() {
    log_info "检查 Jenkins 状态..."

    if sudo systemctl is-active --quiet jenkins; then
        log_success "Jenkins 正在运行"
        echo "Jenkins URL: http://localhost:8080"
        sudo systemctl status jenkins --no-pager
    else
        log_warn "Jenkins 未运行"
    fi
}

# 清理 Jenkins
cleanup() {
    log_info "清理 Jenkins..."

    sudo systemctl stop jenkins

    # 备份重要数据（可选）
    # sudo tar -czf /tmp/jenkins-backup-$(date +%Y%m%d_%H%M%S).tar.gz /var/lib/jenkins

    # 清理 Jenkins 数据
    sudo rm -rf /var/lib/jenkins/*
    sudo rm -f jenkins-cli.jar

    log_success "Jenkins 清理完成"
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
        "install")
            install_jenkins
            ;;
        "configure")
            configure_jenkins
            ;;
        "plugins")
            install_plugins
            ;;
        "setup-pipeline")
            setup_pipeline
            ;;
        "start")
            start_jenkins
            ;;
        "stop")
            stop_jenkins
            ;;
        "restart")
            restart_jenkins
            ;;
        "status")
            check_status
            ;;
        "cleanup")
            cleanup
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