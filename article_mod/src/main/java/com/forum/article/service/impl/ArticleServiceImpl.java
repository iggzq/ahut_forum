package com.forum.article.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.article.entity.Article;
import com.forum.article.entity.CommentArticle;
import com.forum.article.entity.LikeArticle;
import com.forum.article.mapper.ArticleMapper;
import com.forum.article.mapper.CommentArticleMapper;
import com.forum.article.mapper.LikeArticleMapper;
import com.forum.article.service.ArticleService;
import com.forum.article.utils.ArticleRecommender;
import com.forum.article.vo.ArticleVo;
import com.forum.article.vo.LikeArticleVO;
import com.forum.article.vo.SaveArticleVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.forum.article.constants.Constants.*;

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
		// 获取数据
		SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator();
		Long articleId = snowflakeGenerator.next();
		LocalDateTime now = LocalDateTime.now();
		long loginId = StpUtil.getLoginIdAsLong();
		String name = (String) StpUtil.getExtra(LOGIN_USERNAME);
		Integer admissionYear = Integer.valueOf(String.valueOf(StpUtil.getExtra(LOGIN_ADMISSION_YEAR)));
		// 补充数据
		article.setId(articleId);
		article.setUserName(name);
		article.setAdmissionYear(admissionYear);
		article.setCreateTime(now);
		article.setUpdateTime(now);
		article.setUserId(loginId);
		// 数据库保存
		try {
			articleMapper.insert(article);
			return true;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}

	@Override
	public List<Article> getArticles(int page, int size, Byte topicType) {
		List<Article> articleByPage = articleMapper.getArticleByPage(page * size, size, topicType);
		LocalDateTime now = LocalDateTime.now();
		articleByPage.sort((o1, o2) -> {
			double score1 = ArticleRecommender.calculateRecommendScore(o1, now);
			double score2 = ArticleRecommender.calculateRecommendScore(o2, now);
			// 注意这里使用降序排列
			return Double.compare(score2, score1);
		});
		return articleByPage;
	}

	/**
	 * 点赞 还未做点赞重复校验和异常处理
	 */
	@Override
	@Transactional
	public Boolean likeArticle(LikeArticleVO likeArticleVO) {
		LambdaQueryWrapper<LikeArticle> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(LikeArticle::getArticleId, likeArticleVO.getArticleId());
		lambdaQueryWrapper.eq(LikeArticle::getUserId, likeArticleVO.getUserId());
		lambdaQueryWrapper.eq(LikeArticle::getStatus, 0);
		boolean isLike = likeArticleMapper.exists(lambdaQueryWrapper);
		if (isLike) {
			LambdaUpdateWrapper<LikeArticle> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
			lambdaUpdateWrapper.eq(LikeArticle::getArticleId, likeArticleVO.getArticleId());
			lambdaUpdateWrapper.eq(LikeArticle::getUserId, likeArticleVO.getUserId());
			lambdaUpdateWrapper.eq(LikeArticle::getStatus, 0);
			lambdaUpdateWrapper.set(LikeArticle::getStatus, 1);
			likeArticleMapper.update(lambdaUpdateWrapper);
			articleMapper.subLikeNumber(likeArticleVO.getArticleId());
		}
		else {
			SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator();
			Long next = snowflakeGenerator.next();
			Long loginId = Long.valueOf(StpUtil.getLoginId().toString());
			likeArticleVO.setId(next);
			likeArticleVO.setLikeUserId(loginId);
			likeArticleMapper.insert(likeArticleVO);
			articleMapper.addLikeNumber(likeArticleVO.getArticleId());
		}
		return true;
	}

	/**
	 * 发布评论 还未做异常处理
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

	@Override
	public ArticleVo getArticleById(Long articleId) {
		ArticleVo articleVo = new ArticleVo();
		Article article = articleMapper.selectById(articleId);
		BeanUtils.copyProperties(article, articleVo);
		Long loginId = Long.valueOf(StpUtil.getLoginId().toString());
		LambdaQueryWrapper<LikeArticle> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(LikeArticle::getArticleId, articleId);
		lambdaQueryWrapper.eq(LikeArticle::getUserId, loginId);
		lambdaQueryWrapper.eq(LikeArticle::getStatus, 0);
		boolean isLike = likeArticleMapper.exists(lambdaQueryWrapper);
		articleVo.setIsLike(isLike);
		return articleVo;
	}

}
