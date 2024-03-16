package com.forum;

import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisTest {

    @Test
    public void testRedisSave() {
        RedisTemplate<String,String> redisTemplate = new RedisTemplate<>();
        redisTemplate.opsForValue().set("name","张三");
    }
}
