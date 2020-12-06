package com.local.redisbilibili.controller.person;

import com.local.redisbilibili.entity.Person;
import com.local.redisbilibili.filter.RedisBloomFilter;
import com.local.redisbilibili.service.PersonService;
import com.local.redisbilibili.service.RedisService;
import com.local.redisbilibili.template.CacheLoadable;
import com.local.redisbilibili.template.CacheTemplate;
import com.local.redisbilibili.utils.Rst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class PersonController extends AbstractPersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisBloomFilter redisBloomFilter;

    @Autowired
    private CacheTemplate cacheTemplate;

    @GetMapping("/getById")
    public Rst getPersonById(@RequestParam("id")String id) {
        return cacheTemplate.redisFindCache(id, 10, TimeUnit.MINUTES, new CacheLoadable<Person>() {
            @Override
            public Person load() {
                return personService.getById(id);
            }
        }, false);
    }

    //@GetMapping("/getById")
    //public Rst getPersonById(@RequestParam("id") String id) {
    //    // 先使用布隆过滤器判断该值存在  解决缓存穿透
    //    boolean exist = redisBloomFilter.isExist(id);
    //    if(!exist) {
    //        return Rst.create().setCode(600).setMsg("非法访问！").setData(new NullObject());
    //    }
    //    // 查询缓存 解决缓存击穿
    //    Object redisObject = redisService.getItemStr(String.valueOf(id));
    //
    //    if(UtilObject.isNotEmpty(redisObject)) {
    //        // 缓存空对象处理
    //        //if(redisObject instanceof NullObject) {
    //        //    return Rst.create().setCode(500).setData(new NullObject()).setMsg("查询无果！");
    //        //}
    //        return Rst.create().setCode(200).setData(redisObject).setMsg("OK!");
    //    }
    //    try{
    //        Person person = personService.getById(id);
    //        if(UtilObject.isNotEmpty(person)) {
    //            redisService.setItemStr(String.valueOf(id), person.toString());
    //            return Rst.create().setCode(200).setMsg("OK!").setData(person);
    //        }
    //        //else {
    //        //    redisService.setItemStr(String.valueOf(id), new NullObject().toString());
    //        //}
    //    }finally {
    //    }
    //    return Rst.create().setCode(500).setData(new NullObject()).setMsg("查询无果！");
    //}

}
