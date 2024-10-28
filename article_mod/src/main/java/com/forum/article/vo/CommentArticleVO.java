package com.forum.article.vo;

import com.forum.article.entity.CommentArticle;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lituizi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentArticleVO extends CommentArticle {

	private UserVO user;

	private ReplyListVO reply;

}
