FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /ahut-forum

# 将jar文件复制到容器中
COPY ./article_mod/target/article_mod-1.0-SNAPSHOT.jar  article.jar
COPY ./user_mod/target/user_mod-1.0-SNAPSHOT.jar user.jar
COPY ./chat_room/target/chat_room-1.0-SNAPSHOT.jar chatroom.jar
# EXPOSE PORT
EXPOSE 8081
EXPOSE 8082
EXPOSE 8083

# SET ENV VAR
ENV DATABASE_URL=""
ENV DATABASE_USERNAME=""
ENV DATABASE_PASSWORD=""

# SET RUN COMMAND

CMD ["sh", "-c", "exec java -Xms512m -Xmx1024m -XX:MaxNewSize=256m -XX:MaxPermSize=512m -Dserver.port=8081 \
  -Dspring.datasource.url=${DATABASE_URL} \
  -Dspring.datasource.username=${DATABASE_USERNAME} \
  -Dspring.datasource.password=${DATABASE_PASSWORD} \
  -jar article.jar & \
  exec java -Xms256m -Xmx512m -Dserver.port=8082 \
  -Dspring.datasource.url=${DATABASE_URL} \
  -Dspring.datasource.username=${DATABASE_USERNAME} \
  -Dspring.datasource.password=${DATABASE_PASSWORD} \
  -jar chatroom.jar & \
  exec java -Xms256m -Xmx512m -Dserver.port=8081 \
  -Dspring.datasource.url=${DATABASE_URL} \
  -Dspring.datasource.username=${DATABASE_USERNAME} \
  -Dspring.datasource.password=${DATABASE_PASSWORD} \
  -jar user.jar"]
