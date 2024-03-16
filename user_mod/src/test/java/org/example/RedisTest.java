package org.example;

import com.forum.user.config.RedisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {RedisConfig.class})
public class RedisTest {
    @Test
    public void test() {
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.opsForValue().set("test", "test");
    }
}
