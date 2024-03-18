package com.forum.article.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.article.entity.Article;
import com.forum.article.mapper.ArticleMapper;
import com.forum.article.service.ArticleService;
import com.forum.article.vo.SaveArticleVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 存储文章数据 服务实现类
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-16 09:27:56
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Boolean saveArticle(SaveArticleVO saveArticleVO) {
        Article article = new Article();
        BeanUtils.copyProperties(saveArticleVO, article);
        //获取数据
        long articleId = IdUtil.getSnowflakeNextId();
        LocalDateTime now = LocalDateTime.now();
        long loginId = StpUtil.getLoginIdAsLong();
        String name = (String) StpUtil.getExtra("name");
        //补充数据
        article.setId(articleId);
        article.setUserName(name);
        article.setCreateTime(now);
        article.setUpdateTime(now);
        article.setUserId(loginId);
        //数据库保存
        try {
            articleMapper.insert(article);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    @Override
    public List<Article> getArticles(int page, int size) {
        return articleMapper.getArticleByPage(page * size, size);
    }
}
