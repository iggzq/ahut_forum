FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /user

# 将jar文件复制到容器中
COPY ./target/user_mod-1.0-SNAPSHOT.jar  user.jar

# EXPOSE PORT
EXPOSE 8083

# SET ENV VAR
ENV DATABASE_URL=""
ENV DATABASE_USERNAME=""
ENV DATABASE_PASSWORD=""

# SET RUN COMMAND

CMD ["java", "-Xms400m", "-Xmx512m", "-Dserver.port=8083", "-Dspring.datasource.url=${DATABASE_URL}", "-Dspring.datasource.username=${DATABASE_USERNAME}", "-Dspring.datasource.password=${DATABASE_PASSWORD}", "-jar", "user.jar"]
