package com.forum.article.vo;

import lombok.Data;

import java.util.List;

@Data
public class ReplyListVO {
    private Integer total;
    private List<ReplyVO> list;
}
