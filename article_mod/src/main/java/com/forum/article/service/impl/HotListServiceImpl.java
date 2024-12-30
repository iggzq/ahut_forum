package com.forum.article.service.impl;

import jakarta.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HotListServiceImpl {

	private static final String HOT_LIST_KEY = "hot_list";

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 更新指定项的热度分数。
	 */
	public void updateScore(String itemId) {
		ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
		zSetOps.incrementScore(HOT_LIST_KEY, itemId, 1.0);
	}

	/**
	 * 获取前10个热门项。
	 */
	public Map<Long, Integer> getTopTen() {
		ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
		Set<ZSetOperations.TypedTuple<Object>> typedTuples = zSetOps.reverseRangeWithScores(HOT_LIST_KEY, 0, 9);
		Map<Long, Integer> topTenMap = new HashMap<>();
        if (typedTuples != null) {
           topTenMap = typedTuples.stream()
                    .collect(Collectors.toMap(
                            tuple -> Long.parseLong(tuple.getValue().toString()), // Convert value to Long
                            tuple -> (int) Math.round(tuple.getScore())           // Convert score to Integer
                    ));
        }
		return topTenMap;
    }

	/**
	 * 根据多个itemIds获取它们的热度分数，使用Redis管道优化性能。
	 */
	public Map<String, Double> getScoresByIds(List<String> itemIds) {
		return redisTemplate.execute(new SessionCallback<>() {
			@Override
			public <K, V> Map<String, Double> execute(RedisOperations<K, V> operations) throws DataAccessException {
				operations.multi();

				ZSetOperations<K, V> zSetOps = operations.opsForZSet();
				for (String itemId : itemIds) {
					zSetOps.score((K) HOT_LIST_KEY, itemId);
				}

				List<Object> results = operations.exec();
				Map<String, Double> scores = new HashMap<>();

				for (int i = 0; i < results.size(); i++) {
					Double score = (Double) results.get(i);
					if (score != null) {
						scores.put(itemIds.get(i), score);
					}
				}

				return scores;
			}
		});
	}

}
