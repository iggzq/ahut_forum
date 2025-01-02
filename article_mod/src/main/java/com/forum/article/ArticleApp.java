package com.forum.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lituizi
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.forum.article.feign")
public class ArticleApp {
	public static void main(String[] args) {
		SpringApplication.run(ArticleApp.class, args);
	}

}
