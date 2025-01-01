package com.forum.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author lituizi
 */
@SpringBootApplication
@EnableFeignClients
@EnableElasticsearchRepositories
public class ArticleApp {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(ArticleApp.class);
		springApplication.run(ArticleApp.class, args);
	}

}
