package com.forum.article.entity.redisEntity;

import lombok.Data;

import java.io.Serializable;

@Data
public class HotArticle implements Serializable {
    private Long id;
    private Integer hotNum;
    private String title;

    public HotArticle() {
        this.hotNum = 0;
    }
}
