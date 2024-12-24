package com.forum.article.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
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
		// 设置Key的序列化方式
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		// 设置value的序列化方式
		Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.activateDefaultTyping(new LaissezFaireSubTypeValidator(), ObjectMapper.DefaultTyping.EVERYTHING);
		redisTemplate.setValueSerializer(serializer);
		redisTemplate.setHashValueSerializer(serializer);

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
