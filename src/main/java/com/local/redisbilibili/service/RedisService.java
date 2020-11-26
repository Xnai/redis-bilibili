package com.local.redisbilibili.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired()
    private StringRedisTemplate stringRedisTemplate;

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> valOpsStr;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource(name = "redisTemplate")
    private ValueOperations<Object, Object> valOpsObj;

    // 增加 redis 缓存
    public void setItemStr(String key, String value) {
        valOpsStr.set(key, value, 10, TimeUnit.MINUTES); // 加入缓存，缓存10分钟
    }

    // 获取 redis 缓存
    public String getItemStr(String key) {
        return valOpsStr.get(key);
    }

    // 删除 redis 缓存
    public void deleteItemStr(String key) {
        stringRedisTemplate.delete(key);
    }

    public void setItemObj(Object key, Object value) {
        valOpsObj.set(key, value);
    }

    public Object getItemObj(Object key) {
        return valOpsObj.get(key);
    }

    public void deleteItemObj(Object key) {
        redisTemplate.delete(key);
    }


}
