FROM nginx:latest

# 将Nginx默认配置文件删除，以便我们自定义配置
RUN rm /etc/nginx/nginx.conf

# 添加新的Nginx配置文件
COPY ./nginx.conf /etc/nginx/

# 清除Nginx默认的html文件夹内容
RUN rm -rf /usr/share/nginx/html/*

# 将构建的前端资源复制到Nginx的html目录下
COPY ./dist /usr/share/nginx/html

EXPOSE 80

# 启动Nginx服务
CMD ["nginx", "-g", "daemon off;"]

