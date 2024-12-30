package com.forum.article.dto.mysql;

import lombok.Data;

@Data
public class HotArticleDTO {
    private Long id;

    private Integer hotNum;

    private String title;

    public HotArticleDTO() {
        this.hotNum = 0;
    }

}
