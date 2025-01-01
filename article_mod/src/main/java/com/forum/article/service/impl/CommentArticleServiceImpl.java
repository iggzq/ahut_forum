package com.forum.article.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forum.article.entity.Article;
import com.forum.article.entity.CommentArticle;
import com.forum.article.mapper.ArticleMapper;
import com.forum.article.mapper.CommentArticleMapper;
import com.forum.article.service.CommentArticleService;
import com.forum.article.vo.*;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.forum.article.constants.CommentArticleStatusConstants.UNREAD;
import static com.forum.article.constants.Constants.ARTICLE_COMMENTS_REDIS_PRE_KEY;

/**
 * <p>
 * 文章评论 服务实现类
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-21 09:44:10
 */
@Service
public class CommentArticleServiceImpl extends ServiceImpl<CommentArticleMapper, CommentArticle>
		implements CommentArticleService {

	@Resource
	private CommentArticleMapper commentArticleMapper;

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Resource
	private ArticleMapper articleMapper;

	@Resource
	private ObjectMapper objectMapper;

	@Override
	public List<CommentArticleVO> getCommentsById(String id) {
		List<CommentArticleVO> commentArticleVos = new ArrayList<>();
		List<CommentArticle> commentArticles;

		String key = ARTICLE_COMMENTS_REDIS_PRE_KEY + id;
		Object cachedData = redisTemplate.opsForValue().get(key);

		if (cachedData == null) {
			// 如果 Redis 中没有缓存，从数据库中查询并存储到 Redis
			LambdaQueryWrapper<CommentArticle> lambdaQueryWrapper = new LambdaQueryWrapper<>();
			lambdaQueryWrapper.eq(CommentArticle::getArticleId, id);
			lambdaQueryWrapper.orderBy(true, false, CommentArticle::getCreateTime);
			commentArticles = commentArticleMapper.selectList(lambdaQueryWrapper);

			if (!CollectionUtils.isEmpty(commentArticles)) {
				// 将 List<CommentArticle> 序列化为 JSON 并存储到 Redis
				redisTemplate.opsForValue().set(key, commentArticles);
			}
		}
		else {
			// 直接反序列化为 List<CommentArticle>
			commentArticles = objectMapper.convertValue(cachedData, new TypeReference<>() {
			});
		}
		// 处理父子评论逻辑
		List<CommentArticle> fatherCommentsArticle = commentArticles.stream()
			.filter(comment -> comment.getParentId() == null)
			.toList();

		List<CommentArticle> sonCommentsArticle = commentArticles.stream()
			.filter(comment -> comment.getParentId() != null)
			.toList();

		Map<Long, List<ReplyVO>> replyMap = sonCommentsArticle.stream()
			.collect(Collectors.groupingBy(CommentArticle::getParentId,
					Collectors.mapping(this::convertToReplyVO, Collectors.toList())));

		for (CommentArticle fatherCommentArticle : fatherCommentsArticle) {
			CommentArticleVO commentArticleVO = new CommentArticleVO();
			UserVO userVO = new UserVO();
			userVO.setUsername(fatherCommentArticle.getUsername());
			commentArticleVO.setUser(userVO);
			BeanUtils.copyProperties(fatherCommentArticle, commentArticleVO);

			ReplyListVO reply = new ReplyListVO();
			reply.setList(replyMap.getOrDefault(fatherCommentArticle.getId(), Collections.emptyList()));
			reply.setTotal(reply.getList().size());

			commentArticleVO.setReply(reply);
			commentArticleVos.add(commentArticleVO);
		}

		return commentArticleVos;
	}

	private ReplyVO convertToReplyVO(CommentArticle sonCommentArticle) {
		ReplyVO replyVO = new ReplyVO();
		UserVO userVo1 = new UserVO();
		userVo1.setUsername(sonCommentArticle.getUsername());
		replyVO.setUser(userVo1);
		BeanUtils.copyProperties(sonCommentArticle, replyVO);
		return replyVO;
	}

	@Override
	public List<CommentUserVO> getCommentsByUserId() {
		Long loginId = Long.valueOf((String) StpUtil.getLoginId());
		// 查询评论(id, articleId, uid, username, content, updateTime)
		LambdaQueryWrapper<CommentArticle> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(CommentArticle::getToUserId, loginId);
		lambdaQueryWrapper.eq(CommentArticle::getStatus, UNREAD);
		lambdaQueryWrapper.ne(CommentArticle::getUid, loginId);
		lambdaQueryWrapper.select(CommentArticle::getId, CommentArticle::getArticleId, CommentArticle::getUid,
				CommentArticle::getUsername, CommentArticle::getContent, CommentArticle::getUpdateTime);
		List<CommentArticle> commentArticles = commentArticleMapper.selectList(lambdaQueryWrapper);
		// 根据articleId查询文章(id, title)
		if (commentArticles.isEmpty()) {
			return null;
		}
		List<Long> artcileIds = commentArticles.stream().map(CommentArticle::getArticleId).toList();
		LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
		articleLambdaQueryWrapper.select(Article::getId, Article::getTitle);
		articleLambdaQueryWrapper.in(Article::getId, artcileIds);
		HashMap<Long, String> articleMap = new HashMap<>();
		articleMapper.selectList(articleLambdaQueryWrapper)
			.forEach(article -> articleMap.put(article.getId(), article.getTitle()));
		// 构造返回值
		List<CommentUserVO> commentUserVos = new ArrayList<>();
		commentArticles.forEach(commentArticle -> {
			CommentUserVO commentUserVO = new CommentUserVO();
			BeanUtils.copyProperties(commentArticle, commentUserVO);
			String content = commentArticle.getContent();
			if(content.contains("回复")){
				int spanEndIndex = content.indexOf("</span>") + "</span>".length();
				String result = content.substring(spanEndIndex).trim(); // 使用 trim() 去除可能存在的前导和尾随空格
				commentUserVO.setContent(result);
			}
			commentUserVO.setArticleTitle(articleMap.get(commentArticle.getArticleId()));
			commentUserVos.add(commentUserVO);
		});
		return commentUserVos;
	}

}
