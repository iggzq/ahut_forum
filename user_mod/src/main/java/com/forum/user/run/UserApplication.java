package com.forum.user.run;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 */
@SpringBootApplication
@ComponentScan("com.forum.user")
@MapperScan("com.forum.user.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(UserApplication.class);
        springApplication.run(args);
    }
}
