package com.forum.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *
 * @author lituizi
 */
@SpringBootApplication
@EnableFeignClients
public class ArticleApp {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ArticleApp.class);
        springApplication.run(ArticleApp.class, args);
    }
}
