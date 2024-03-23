package com.forum.article.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.article.entity.Article;
import com.forum.article.entity.CommentArticle;
import com.forum.article.mapper.ArticleMapper;
import com.forum.article.mapper.CommentArticleMapper;
import com.forum.article.mapper.LikeArticleMapper;
import com.forum.article.service.ArticleService;
import com.forum.article.vo.LikeArticleVO;
import com.forum.article.vo.SaveArticleVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.forum.article.constants.Constants.ARTICLE_COMMENTS_REDIS_PRE_KEY;
import static com.forum.article.constants.Constants.LOGIN_USERNAME;

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

    @Resource
    private LikeArticleMapper likeArticleMapper;

    @Resource
    private CommentArticleMapper commentArticleMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Boolean saveArticle(SaveArticleVO saveArticleVO) {
        Article article = new Article();
        BeanUtils.copyProperties(saveArticleVO, article);
        //获取数据
        long articleId = IdUtil.getSnowflakeNextId();
        LocalDateTime now = LocalDateTime.now();
        long loginId = StpUtil.getLoginIdAsLong();
        String name = (String) StpUtil.getExtra(LOGIN_USERNAME);
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

    /**
     * 点赞
     * 还未做点赞重复校验和异常处理
     */
    @Override
    public Boolean likeArticle(LikeArticleVO likeArticleVO) {
        SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator();
        Long next = snowflakeGenerator.next();
        System.out.println(Long.valueOf(StpUtil.getLoginId().toString()));
        Long loginId = Long.valueOf(StpUtil.getLoginId().toString());
        likeArticleVO.setId(next);
        likeArticleVO.setLikeUserId(loginId);
        likeArticleMapper.insert(likeArticleVO);
        articleMapper.addLikeNumber(likeArticleVO.getArticleId());
        return true;
    }

    /**
     * 发布评论
     * 还未做异常处理
     */
    @Override
    public Boolean commentArticle(CommentArticle commentArticle) {
        SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator();
        Long id = snowflakeGenerator.next();
        Date createUpdateTime = DateTime.now();
        Long userId = Long.valueOf(StpUtil.getLoginId().toString());
        String userName = StpUtil.getExtra(LOGIN_USERNAME).toString();
        commentArticle.setId(id);
        commentArticle.setUid(userId);
        commentArticle.setUsername(userName);
        commentArticle.setCreateTime(createUpdateTime);
        commentArticle.setUpdateTime(createUpdateTime);
        commentArticleMapper.insert(commentArticle);
        articleMapper.addCommentNumber(commentArticle.getArticleId());
        redisTemplate.delete(ARTICLE_COMMENTS_REDIS_PRE_KEY + commentArticle.getArticleId());
        return true;
    }
}
