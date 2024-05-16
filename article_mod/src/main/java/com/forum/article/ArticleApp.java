package com.forum.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *
 * @author lituizi
 */
@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = "com.forum.article.config")
public class ArticleApp {
    public static void main(String[] args) {
//        SpringApplication.run(ArticleApp.class, args);
        SpringApplication springApplication = new SpringApplication(ArticleApp.class);
        springApplication.run(args);
    }
}
