package com.forum.article.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lituizi
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class ConfigProperties {

    @NotNull
    private  String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
