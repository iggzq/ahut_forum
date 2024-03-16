package com.forum.config;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoginFilter implements GlobalFilter {

    private static final Logger LOG = LoggerFactory.getLogger(LoginFilter.class);
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (path.contains("/user/test")
                || path.contains("/user/login")
                || path.contains("/user/register")
                || path.contains("/article/test")){
            LOG.info("不需要登陆验证：『{}』", path);
            return chain.filter(exchange);
        } else{
            LOG.info("需要登陆验证:『{}』", path);
        }
        String token = exchange.getRequest().getHeaders().getFirst("satoken");
        LOG.info("会员登陆验证开始，token:『{}』", token);
        if (token == null || token.isEmpty()) {
            LOG.info("token为空，请求被拦截");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        //校验token是否有效
        Object o = redisTemplate.opsForValue().get(token);
        if (o == null) {
            LOG.info("token无效，拦截请求");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }


        return exchange.getResponse().setComplete();




    }
}
