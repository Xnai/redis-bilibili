package com.local.redisbilibili;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类为谷歌实现的布隆过滤器的测试类
 */
public class TestBloomFilter {
    // 预计插入量
    private static int size = 1000000;

    /**
     * 谷歌 guava 框架实现的布隆过滤器的位数组 最大21亿，并且数据保存在 jvm 中，数据不会进行持久化， 256M
     * 我们自己实现的布隆过滤器，使用 Redis 实现 最大42亿位，保存在 redis 内存中，数据会持久化，所以使用自定义的布隆过滤器，就算服务器断电也不用担心 512M
     *  Redis 的为什么是 42亿位，因为我们使用的是 String 类型，根据官方文档，String 类型最大的 value 值为42亿位
     */

    // size 初始大小，预计插入量
    // fpp 误判概率 不能写为0，会抛出异常，误判率会根据设定改变，误判率越低，内存开销越大
    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, 0.001);

    public static void main(String[] args) {
        for(int i = 1; i <= size; i++) {
            bloomFilter.put(i);
        }
        List<Integer> list = new ArrayList<>(10000);
        // 故意取 10000 个不在过滤器里的值， 看看有多少个会被认为在过滤器中
        for(int i = size + 10000; i < size + 20000; i++) {
            if(bloomFilter.mightContain(i)) { // 误判
                list.add(i);
            }
        }
        System.out.println("误判数量：" + list.size());
    }
}
