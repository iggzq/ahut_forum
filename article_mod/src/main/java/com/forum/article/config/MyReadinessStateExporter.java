package com.forum.article.config;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author lituizi
 */
@Component
public class MyReadinessStateExporter {

    @EventListener
    public void onStateChange(AvailabilityChangeEvent<ReadinessState> changeEvent) {
        switch (changeEvent.getState()) {
            case ACCEPTING_TRAFFIC:
                System.out.println("服务已准备好接收流量");
                break;
            case REFUSING_TRAFFIC:
                System.out.println("服务不可用");
                break;
        }
    }

    @EventListener
    public void onWebServiceReady(ServletWebServerInitializedEvent event){
        System.out.println("Web服务已启动");
//        ServletWebServerApplicationContext applicationContext = event.getApplicationContext();
//        ConfigurableEnvironment environment = applicationContext.getEnvironment();
//        Map<String, Object> systemProperties = environment.getSystemProperties();
//        System.out.println("系统属性:");
//        systemProperties.forEach((k,v)->{
//            System.out.println(k+" : "+v);
//        });
//        System.out.println("系统环境变量:");
//        Map<String, Object> systemEnvironment = environment.getSystemEnvironment();
//        systemEnvironment.forEach((k,v)->{
//            System.out.println(k+" : "+v);
//        });
    }
}
