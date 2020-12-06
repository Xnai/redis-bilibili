package com.local.redisbilibili.template;

import com.local.redisbilibili.filter.RedisBloomFilter;
import com.local.redisbilibili.service.RedisService;
import com.local.redisbilibili.util.NullObject;
import com.local.redisbilibili.util.RedisLock;
import com.local.redisbilibili.utils.Rst;
import com.local.redisbilibili.utils.UtilObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

public class CacheTemplate<T> {

    @Autowired
    private RedisBloomFilter redisBloomFilter;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisLock redisLock;

    public Rst redisFindCache(String key, long expire, TimeUnit unit, CacheLoadable<T> cacheLoadable, boolean b) {
        // 使用布隆过滤器解决缓存穿透问题
        if(!redisBloomFilter.isExist(key)) {
            return Rst.create().setCode(600).setMsg("Illegal Access!").setData(new NullObject());
        }
        // 使用分布式锁 + redis 解决缓存击穿问题
        Object redisObject = redisService.getItemStr(key);
        if(UtilObject.isNotEmpty(redisObject)) {
            return Rst.create().setCode(200).setMsg("OK!").setData(redisObject);
        }
        // 加分布式锁
        redisLock.lock(key);
        try {
            redisObject = redisService.getItemStr(key);
            if(UtilObject.isNotEmpty(redisObject)) {
                return Rst.create().setCode(200).setMsg("OK!").setData(redisObject);
            }
            T load = cacheLoadable.load();
            if(UtilObject.isNotEmpty(load)) {
                redisService.setItemStr(key, load.toString(), expire, unit);
                return Rst.create().setCode(200).setMsg("OK!").setData(load);
            }
                return Rst.create().setCode(500).setMsg("No Data!").setData(new NullObject());
        }finally {
            // 分布式解锁
            redisLock.unlock(key);
        }
    }
}
