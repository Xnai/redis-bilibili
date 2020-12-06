package com.local.redisbilibili.datainit;

import com.local.redisbilibili.filter.RedisBloomFilter;
import com.local.redisbilibili.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class RedisDataInit {

    @Autowired
    private PersonService personService;

    @Autowired
    private RedisBloomFilter redisBloomFilter;

    @PostConstruct
    public void init() {
        // 这里只获取查询时需要的列的值，因为在接口请求的第一步，要使用布隆过滤器来过滤数据的存在的可能性
        List<String> idList = personService.getAllIds();
        // 在程序初始化时，就要将表中所有的 需要的列 的值保存在 bloomFilter 中
        for(String id : idList) {
            redisBloomFilter.put(id);
        }
    }
}
