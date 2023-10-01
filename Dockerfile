# 使用OpenJDK 17作為基礎映像
FROM openjdk:17-jdk-slim

# 設定工作目錄
WORKDIR /app

# 將你的Spring Boot jar文件複製到映像中
COPY target/cathayLibrarySystemDemo-0.0.1-SNAPSHOT.jar /app/app.jar

# 設定啟動命令
CMD ["java", "-jar", "/app/app.jar"]

