package com.forum.article.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author lituizi
 */
@Component
public class MyBean {


    public MyBean(ApplicationArguments applicationArguments) {
        Set<String> optionNames = applicationArguments.getOptionNames();
        List<String> nonOptionArgs = applicationArguments.getNonOptionArgs();
        System.out.println(nonOptionArgs);
        System.out.println(optionNames);
    }
}
