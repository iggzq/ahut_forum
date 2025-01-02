package com.forum.article.vo;

import com.forum.common.entity.Article;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lituizi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleVo extends Article {

	Boolean isLike;

}
