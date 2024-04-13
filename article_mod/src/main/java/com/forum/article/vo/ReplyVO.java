package com.forum.article.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lituizi
 */
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
