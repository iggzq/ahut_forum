package com.forum.user.config;

import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lituizi
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 所有接口
		registry.addMapping("/**")
			// 是否发送 Cookie
			.allowCredentials(true)
			// 支持域
			.allowedOriginPatterns("**")
			// 支持方法
			.allowedMethods("GET", "POST", "PUT", "DELETE")
			.allowedHeaders("*")
			.exposedHeaders("*");
	}

	// Sa-Token 整合 jwt (Simple 简单模式)
	@Bean
	public StpLogic getStpLogicJwt() {
		return new StpLogicJwtForSimple();
	}

}
