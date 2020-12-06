package com.local.redisbilibili.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class UtilRedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 默认过期时长
     */
    public static final long DEFAULT_EXPIRE_TIME = 60 * 60 * 24;

    /**
     * 不设定过期时长
     */
    public static final long NOT_EXPIRE = -1;

    public boolean existsKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 重命名key，如果 newKey 的值已存在，则会被覆盖
     * @param oldKey
     * @param newKey
     */
    public void renameKey(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * newKey 不存在时才重新命名
     * @param oldKey
     * @param newKey
     * @return
     */
    public boolean renameKeyNotExist(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 删除单个 key
     * @param key
     * @return
     */
    public boolean deleteKey(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除多个 key
     * @param key
     */
    public void deleteKey(String... key) {
        Set<String> kSet = Stream.of(key).map( k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     * 删除集合 key
     * @param keys
     */
    public void deleteKey(Collection<String> keys) {
        Set<String> kSet = keys.stream().map( k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     * 设置 key 声明周期
     * @param key
     * @param time
     * @param unit
     */
    public void expireKey(String key, long time, TimeUnit unit) {
        redisTemplate.expire(key, time, unit);
    }

    /**
     * 设置 key 在指定时间过期
     * @param key
     * @param date
     */
    public void expireKeyAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    /**
     * 获取 key 的声明周期
     * @param key
     * @param unit
     * @return
     */
    public long getKeyExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 设定 key 永久有效
     * @param key
     */
    public void persistKey(String key) {
        redisTemplate.persist(key);
    }

}
