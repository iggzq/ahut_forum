package com.forum.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lituizi
 */
@SpringBootApplication
@MapperScan("com.forum.user.mapper")
@EnableFeignClients
public class UserApp {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(UserApp.class);
		springApplication.run(args);
	}

}
