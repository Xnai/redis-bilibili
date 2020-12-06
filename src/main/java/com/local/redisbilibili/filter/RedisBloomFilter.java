package com.local.redisbilibili.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.util.List;

@ConfigurationProperties(prefix = "bloom.filter") // 要想使用该注解，一定要在初始化赋值的属性上提供 get/set 方法
@Component
public class RedisBloomFilter {

    // 预计插入量
    //@Value("bloom.filter.expectedInsertions")
    private long expectedInsertions;

    // 容错率
    //@Value("bloom.filter.fpp")
    private double fpp;

    public long getExpectedInsertions() {
        return expectedInsertions;
    }

    public void setExpectedInsertions(long expectedInsertions) {
        this.expectedInsertions = expectedInsertions;
    }

    public double getFpp() {
        return fpp;
    }

    public void setFpp(double fpp) {
        this.fpp = fpp;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    // bit数组的长度
    private long numBits;

    private int numHashFunctions;

    // Spirngboot 的注解，该注解实现的功能是在类创建时，执行此方法
    @PostConstruct
    public void init() {
        this.numBits = optimalNumOfBit(expectedInsertions, fpp);
        this.numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, numBits);
    }

    // 计算布隆过滤器初始化时需要的hash函数的数量，该方法与谷歌的 guava 计算哈希函数数量的方法相同
    private int optimalNumOfHashFunctions(long expectedInsertions, long numBits) {
        return Math.max(1, (int) Math.round((double)numBits / expectedInsertions * Math.log(2)));
    }

    // 计算布隆过滤器位数组大小方法，该方法与谷歌的 guava 计算位数组大小的方法相同
    private long optimalNumOfBit(long expectedInsertions, double fpp) {
        if(fpp == 0) {
            fpp = Double.MIN_VALUE;
        }
        return (long) (-expectedInsertions * Math.log(fpp) / (Math.log(2) * Math.log(2)));
    }

    /**
     * 判断 key 是否存在于我们自定义的布隆过滤器中
     * @param key
     * @return
     */
    public boolean isExist(String key) {
        long[] indexs = getIndex(key);
        List list = redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Nullable
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.openPipeline();
                for(long index : indexs) {
                    redisConnection.getBit("bf:redisBloomFilter".getBytes(), index);
                }
                redisConnection.close();
                return null;
            }
        });
        return !list.contains(false); // 这里的 false 是值为0的意思
    }

    // 往布隆过滤器中添加数据
    public void put(String key) {
        long[] indexs = getIndex(key);
        redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Nullable
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.openPipeline();
                for(long index : indexs) {
                    redisConnection.setBit("bf:redisBloomFilter".getBytes(), index, true); // 这里的 true 是值为1的意思
                }
                redisConnection.close();
                return null;
            }
        });
    }

    /**
     * 根据 key 获取 bitmap 下标
     * @param key
     * @return
     */
    private long[] getIndex(String key) {
        long hash1 = hash(key);
        long hash2 = hash1 >>> 16;
        long[] result = new long[numHashFunctions];
        for(int i = 0; i < numHashFunctions; i++) {
            Long combinedHash = hash1 + i * hash2;
            if(combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            result[i] = combinedHash % numBits;
        }
        return result;
    }

    // 一个hash算法
    private long hash(String key) {
        return key.hashCode();
    }

}
