package com.forum.article.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;

@Configuration
public class AppConfigValidatorConfiguration {

    @Bean
    public static Validator configurationPropertiesValidator() {
        return new MyPropertiesValidator();
    }
}
