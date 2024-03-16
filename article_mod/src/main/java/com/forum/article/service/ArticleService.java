package com.forum.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.forum.article.entity.Article;
import com.forum.article.vo.SaveArticleVO;

/**
 * <p>
 * 存储文章数据 服务类
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-16 09:27:56
 */
public interface ArticleService extends IService<Article> {

    public Boolean saveArticle(SaveArticleVO saveArticleVO);
}
