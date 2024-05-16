package com.forum.article.config;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MyPropertiesValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ConfigProperties.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // 添加自定义验证逻辑
        ConfigProperties configProperties = (ConfigProperties) target;
        System.out.println(configProperties.getUrl());
    }
}
