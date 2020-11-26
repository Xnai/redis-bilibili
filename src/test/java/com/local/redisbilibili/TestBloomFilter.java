package com.local.redisbilibili;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.ArrayList;
import java.util.List;

public class TestBloomFilter {

    private static int size = 1000000;

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
