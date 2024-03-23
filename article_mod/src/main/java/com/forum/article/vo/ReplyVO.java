package com.forum.article.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyVO {

    private Long id;
    private Long parentId;
    private Long uid;
    private String content;
    private Integer likes;
    private LocalDateTime createTime;
    private UserVO user;
}
