FROM maven:3.8.6-openjdk-17 AS build

WORKDIR /app
COPY backend/pom.xml .
COPY backend/src src/

# 预先下载依赖（利用Docker缓存）
RUN mvn dependency:go-offline -B

# 构建应用
RUN mvn clean package -DskipTests -B

# 运行时使用官方OpenJDK镜像
FROM openjdk:17-jre-slim

WORKDIR /app

# 从构建阶段复制jar文件
COPY --from=build /app/target/wind-tunnel-backend-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]