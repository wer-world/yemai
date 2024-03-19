package com.kgc.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
@Component
public class RedisUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    public void setValueToRedis(String key, String value) {

        stringRedisTemplate.opsForValue().set(key, value, 300, TimeUnit.SECONDS);
    }

    public String getValueForRedis(String key) {
        String value = stringRedisTemplate.opsForValue().get(key);
        return value;
    }
}
