package com.forum.article.utils;

import com.forum.article.entity.Article;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author lituizi
 */
public class ArticleRecommender {

	// 点赞数权重
	final static double BETA = 0.3;

	// 评论数权重
	final static double GAMMA = 0.7;

	// 最大指数值
	final static double MAX_EXPONENT_VALUE = 200;

	// 最小时间衰减因子
	final static double MIN_TIME_DECAY_FACTOR = 0.02;

	public static double calculateRecommendScore(Article article, LocalDateTime now) {
		long duration = Duration.between(now, article.getCreateTime()).toNanos();
		double ageInDays = (double) (duration / (1000 * 60 * 60 * 24));
		// 时间衰减因子
		double timeDecay = Math.exp(Math.min(-0.01 * ageInDays, MAX_EXPONENT_VALUE));
		double score = BETA * article.getLikeCount() + GAMMA * article.getCommentCount();
		return Math.min(score, MIN_TIME_DECAY_FACTOR) * timeDecay;

	}

}
