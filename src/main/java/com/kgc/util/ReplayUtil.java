package com.kgc.util;

import com.kgc.config.ReplayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 重放攻击防御攻击工具类
 * 待解决：添加定时器，扫描使用的随机数，超过时间删除随机数，定义使用时间常量
 *
 * @Author: 魏小可
 * @Date: 2024-03-18-15:23
 */
@Component
public class ReplayUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ReplayConfig replayConfig;

    private final List<String> randomList = new ArrayList<>(); // 待使用的随机数

    private final Map<String, String> useRandomMap = new HashMap<>(); // 使用的随机数

    /**
     * 将随机数加载至maxRandom的数量
     */
    public void createUUIDToRedis(Integer num) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        for (int i = 0; i < replayConfig.getMaxRandom() - num; i++) {
            String random = UUID.randomUUID().toString();
            randomList.add(random);
            operations.set(random, random);
        }
    }

    /**
     * 创建单个随机数
     */
    public void createOneUUIDToRedis() {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String random = UUID.randomUUID().toString();
        operations.set(random, random);
        randomList.add(random);
    }

    /**
     * 检查随机数
     *
     * @param random 需要检查的随机数
     * @return 返回随机数
     */
    public String checkRandom(String random) {
        String redisRandom = stringRedisTemplate.opsForValue().get(random);
        if (randomList.size() <= replayConfig.getMinRandom()) {
            createUUIDToRedis(randomList.size());
        }
        return redisRandom;
    }

    /**
     * 获取随机数
     *
     * @return 返回随机数
     */
    public String getRandom() {
        int index = randomList.size() - 1;
        String random = randomList.get(index);
        useRandomMap.put(random, String.valueOf(new Date(new Date().getTime() + 60 * 60 * 1000)));
        randomList.remove(index);
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String encodedMd5 = MD5Util.encodeMd5(random, replayConfig.getReplayMd5Key());
        operations.set(encodedMd5, random);
        return encodedMd5;
    }

    /**
     * 删除随机数
     *
     * @param random 需要删除的随机数
     */
    public void removeRandom(String random) {
        stringRedisTemplate.delete(random);
        useRandomMap.remove(random);
        createOneUUIDToRedis();
    }
}
