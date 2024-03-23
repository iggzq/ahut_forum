package com.forum.article.vo;

import com.forum.article.entity.CommentArticle;
import lombok.Data;

@Data
public class CommentArticleVO extends CommentArticle {
   private UserVO user;
   private ReplyListVO reply;
}
