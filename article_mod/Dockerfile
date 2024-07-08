FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /article

# 将jar文件复制到容器中
COPY ./target/article_mod-1.0-SNAPSHOT.jar  article.jar

# EXPOSE PORT
EXPOSE 8081

# SET ENV VAR
ENV DATABASE_URL=""
ENV DATABASE_USERNAME=""
ENV DATABASE_PASSWORD=""

# SET RUN COMMAND

CMD ["java", "-Xms512m -Xmx1024m -XX:MaxNewSize=256m -XX:MaxPermSize=512m -Dserver.port=8081", "-Dspring.datasource.url=${DATABASE_URL}", "-Dspring.datasource.username=${DATABASE_USERNAME}", "-Dspring.datasource.password=${DATABASE_PASSWORD}", "-jar", "article.jar"]
