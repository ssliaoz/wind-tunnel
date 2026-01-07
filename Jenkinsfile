pipeline {
    agent any

    environment {
        // 项目配置
        PROJECT_NAME = 'wind-tunnel'
        DOCKER_IMAGE = 'wind-tunnel-backend'
        DOCKER_REGISTRY = 'registry.example.com'  // 替换为实际的镜像仓库地址
        NAMESPACE = 'wind-tunnel'
        
        // 构建配置
        JAVA_OPTS = '-Xms1g -Xmx2g'
        MAVEN_OPTS = '-Dmaven.repo.local=/tmp/maven-repo'
    }

    parameters {
        choice(
            name: 'ENVIRONMENT',
            choices: ['dev', 'test', 'prod'],
            description: '选择部署环境'
        )
        string(
            name: 'VERSION',
            defaultValue: 'latest',
            description: '指定部署版本号，留空则使用最新构建'
        )
        booleanParam(
            name: 'SKIP_TESTS',
            defaultValue: false,
            description: '是否跳过测试'
        )
        booleanParam(
            name: 'ROLLBACK',
            defaultValue: false,
            description: '是否执行回滚操作'
        )
    }

    stages {
        stage('代码检出') {
            steps {
                checkout scm
                script {
                    env.BUILD_VERSION = sh(
                        script: "git describe --tags --always",
                        returnStdout: true
                    ).trim()
                    
                    if (params.VERSION != 'latest') {
                        env.BUILD_VERSION = params.VERSION
                    }
                    
                    echo "构建版本: ${env.BUILD_VERSION}"
                }
            }
        }

        stage('代码质量检查') {
            parallel {
                stage('代码风格检查') {
                    steps {
                        sh 'mvn checkstyle:check -q'
                    }
                }
                stage('安全扫描') {
                    steps {
                        sh 'mvn dependency:check -q'
                    }
                }
                stage('单元测试') {
                    when {
                        not { params.SKIP_TESTS }
                    }
                    steps {
                        sh 'mvn test -q'
                    }
                }
            }
        }

        stage('构建应用') {
            steps {
                script {
                    if (params.ROLLBACK) {
                        echo "回滚模式，跳过构建"
                        return
                    }
                    
                    if (params.SKIP_TESTS) {
                        sh 'mvn clean package -DskipTests -q'
                    } else {
                        sh 'mvn clean package -q'
                    }
                }
            }
        }

        stage('构建Docker镜像') {
            when {
                not { params.ROLLBACK }
            }
            steps {
                script {
                    def dockerImage = docker.build("${env.DOCKER_IMAGE}:${env.BUILD_VERSION}", 
                        "--build-arg JAR_FILE=target/*.jar -f Dockerfile .")
                    
                    // 标记为latest
                    dockerImage.push('latest')
                    dockerImage.push(env.BUILD_VERSION)
                }
            }
        }

        stage('推送镜像到仓库') {
            when {
                allOf {
                    not { params.ROLLBACK }
                    anyOf {
                        expression { params.ENVIRONMENT == 'test' }
                        expression { params.ENVIRONMENT == 'prod' }
                    }
                }
            }
            steps {
                script {
                    def dockerImage = docker.image("${env.DOCKER_IMAGE}:${env.BUILD_VERSION}")
                    docker.withRegistry("https://${env.DOCKER_REGISTRY}", 'docker-registry-credentials') {
                        dockerImage.push("${env.DOCKER_REGISTRY}/${env.DOCKER_IMAGE}:${env.BUILD_VERSION}")
                        dockerImage.push("${env.DOCKER_REGISTRY}/${env.DOCKER_IMAGE}:latest")
                    }
                }
            }
        }

        stage('部署到Kubernetes') {
            when {
                not { params.ROLLBACK }
            }
            steps {
                script {
                    deployToKubernetes(params.ENVIRONMENT, env.BUILD_VERSION)
                }
            }
        }

        stage('健康检查') {
            when {
                not { params.ROLLBACK }
            }
            steps {
                script {
                    healthCheck(params.ENVIRONMENT)
                }
            }
        }

        stage('回滚') {
            when {
                expression { params.ROLLBACK }
            }
            steps {
                script {
                    rollbackDeployment(params.ENVIRONMENT)
                }
            }
        }
    }

    post {
        success {
            script {
                def slackMessage = """
                    ✅ *${env.PROJECT_NAME} 部署成功*
                    - 环境: ${params.ENVIRONMENT}
                    - 版本: ${env.BUILD_VERSION}
                    - 构建号: ${env.BUILD_NUMBER}
                    - 提交者: ${env.CHANGE_AUTHOR}
                """.stripIndent()
                
                // 发送通知（需要配置Slack集成）
                // slackSend(channel: '#deployments', color: 'good', message: slackMessage)
                
                echo "部署成功: ${env.BUILD_VERSION}"
            }
        }
        failure {
            script {
                def slackMessage = """
                    ❌ *${env.PROJECT_NAME} 部署失败*
                    - 环境: ${params.ENVIRONMENT}
                    - 版本: ${env.BUILD_VERSION}
                    - 构建号: ${env.BUILD_NUMBER}
                    - 错误: ${currentBuild.description ?: '构建失败'}
                """.stripIndent()
                
                // 发送通知（需要配置Slack集成）
                // slackSend(channel: '#deployments', color: 'danger', message: slackMessage)
                
                echo "部署失败: ${env.BUILD_VERSION}"
            }
        }
        always {
            // 清理构建产物
            cleanWs()
        }
    }
}

def deployToKubernetes(environment, version) {
    def k8sManifest = readFile('k8s-deployment.yaml')
    
    // 替换镜像版本
    k8sManifest = k8sManifest.replaceAll(/image: wind-tunnel-backend:latest/, "image: wind-tunnel-backend:${version}")
    
    // 为不同环境设置不同的资源配置
    if (environment == 'prod') {
        k8sManifest = k8sManifest.replaceAll(/replicas: 1/, 'replicas: 3')
        k8sManifest = k8sManifest.replaceAll(/memory: "512Mi"/, 'memory: "1Gi"')
        k8sManifest = k8sManifest.replaceAll(/cpu: "250m"/, 'cpu: "500m"')
    } else if (environment == 'dev') {
        k8sManifest = k8sManifest.replaceAll(/memory: "512Mi"/, 'memory: "256Mi"')
        k8sManifest = k8sManifest.replaceAll(/cpu: "250m"/, 'cpu: "125m"')
    }
    
    writeFile file: "k8s-deployment-${environment}.yaml", text: k8sManifest
    
    // 应用Kubernetes配置
    sh """
        kubectl apply -f k8s-deployment-${environment}.yaml
        kubectl rollout status deployment/wind-tunnel-backend -n ${env.NAMESPACE} --timeout=300s
    """
}

def healthCheck(environment) {
    sh """
        echo "执行健康检查..."
        sleep 30
        
        # 检查Pod状态
        kubectl get pods -n ${env.NAMESPACE} -l app=wind-tunnel-backend
        kubectl wait --for=condition=ready pod -l app=wind-tunnel-backend -n ${env.NAMESPACE} --timeout=300s
        
        # 检查服务是否响应
        COUNT=0
        MAX_ATTEMPTS=30
        until [ \$COUNT -ge \$MAX_ATTEMPTS ]; do
            if kubectl get svc wind-tunnel-service -n ${env.NAMESPACE} -o jsonpath='{.status.loadBalancer.ingress[0].ip}' > /dev/null 2>&1; then
                SERVICE_IP=\$(kubectl get svc wind-tunnel-service -n ${env.NAMESPACE} -o jsonpath='{.status.loadBalancer.ingress[0].ip}')
                if curl -f http://\$SERVICE_IP/actuator/health > /dev/null 2>&1; then
                    echo "服务健康检查通过"
                    break
                fi
            fi
            sleep 10
            COUNT=\$((COUNT + 1))
        done
        
        if [ \$COUNT -ge \$MAX_ATTEMPTS ]; then
            echo "健康检查失败"
            exit 1
        fi
    """
}

def rollbackDeployment(environment) {
    sh """
        echo "执行回滚操作..."
        kubectl rollout undo deployment/wind-tunnel-backend -n ${env.NAMESPACE}
        kubectl rollout status deployment/wind-tunnel-backend -n ${env.NAMESPACE} --timeout=300s
    """
}