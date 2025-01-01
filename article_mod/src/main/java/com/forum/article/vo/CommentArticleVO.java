package com.forum.article.vo;

import com.forum.article.entity.CommentArticle;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author lituizi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentArticleVO extends CommentArticle implements Serializable{

	private UserVO user;

	private ReplyListVO reply;

}
