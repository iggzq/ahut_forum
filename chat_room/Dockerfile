FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /chatroom

# 将jar文件复制到容器中
COPY ./target/chat_room-1.0-SNAPSHOT.jar  chatroom.jar

# EXPOSE PORT
EXPOSE 8082

# SET ENV VAR
ENV DATABASE_URL=""
ENV DATABASE_USERNAME=""
ENV DATABASE_PASSWORD=""

# SET RUN COMMAND

CMD ["java", "-Xms300m", "-Xmx512m", "-Dserver.port=8082", "-Dspring.datasource.url=${DATABASE_URL}", "-Dspring.datasource.username=${DATABASE_USERNAME}", "-Dspring.datasource.password=${DATABASE_PASSWORD}", "-jar", "chatroom.jar"]
