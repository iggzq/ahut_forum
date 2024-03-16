package com.forum.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.article.entity.Article;
import com.forum.article.mapper.ArticleMapper;
import com.forum.article.service.ArticleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 存储文章数据 服务实现类
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-16 09:27:56
 */
@Service
public class ArticleServiceImp extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
