package com.nasir.rediscache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisTemplateHelper {

    @Value("${application.redis.lifetime}")
    private Long redisCacheLifeTime;

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisTemplateHelper(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void store(String key, Object value){
        log.info("storing to redis:{}", key);
        redisTemplate.opsForValue().set("accounting-api-redis-template-products-" + key, value, redisCacheLifeTime, TimeUnit.MINUTES);
    }

    public Object fetch(String key){
        if (redisTemplate.hasKey("accounting-api-redis-template-products-" + key)) {
            log.info("receiving form redis:{}", key);
            return redisTemplate.boundValueOps("accounting-api-redis-template-products-" + key).get();
        }
        return null;
    }

    public void delete(String key){
        log.info("storing to redis:{}", key);
        if (redisTemplate.hasKey("accounting-api-redis-template-products-" + key)) {
            log.info("receiving form redis:{}", key);
            redisTemplate.boundValueOps("accounting-api-redis-template-products-" + key).expire(1, TimeUnit.MILLISECONDS);
        }
    }

}
