package com.forum.article.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author lituizi
 */
@Configuration
public class RedisConfig {

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);
		// 使用 Jackson2 的 JSON 序列化器
		GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();

		// 设置 key 的序列化方式为 String
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());

		// 设置 value 的序列化方式为 JSON
		redisTemplate.setValueSerializer(jsonSerializer);
		redisTemplate.setHashValueSerializer(jsonSerializer);

		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	@Bean
	public RedisTemplate<Long, Integer> redisHotSave(RedisConnectionFactory factory) {
		RedisTemplate<Long, Integer> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);

		// 使用GenericToStringSerializer序列化Long类型的key
		template.setKeySerializer(new GenericToStringSerializer<>(Long.class));

		// 使用GenericToStringSerializer序列化和反序列化Integer类型的value
		template.setValueSerializer(new GenericToStringSerializer<>(Integer.class));

		// 如果你需要操作Hash类型的数据，也需要配置相应的序列化器
		template.setHashKeySerializer(new GenericToStringSerializer<>(Long.class));
		template.setHashValueSerializer(new GenericToStringSerializer<>(Integer.class));

		template.afterPropertiesSet();
		return template;
	}

}
