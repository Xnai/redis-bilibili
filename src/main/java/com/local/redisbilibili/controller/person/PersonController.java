package com.local.redisbilibili.controller.person;

import com.local.redisbilibili.entity.Person;
import com.local.redisbilibili.service.PersonService;
import com.local.redisbilibili.service.RedisService;
import com.local.redisbilibili.util.NullObject;
import com.local.redisbilibili.util.Rst;
import com.local.redisbilibili.util.UtilObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController extends AbstractPersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private RedisService redisService;

    @GetMapping("/getById")
    public Rst getPersonById(@RequestParam("id") String id) {
        // 查询缓存
        Object redisObject = redisService.getItemStr(String.valueOf(id));

        if(UtilObject.isNotEmpty(redisObject)) {
            // 缓存空对象处理
            //if(redisObject instanceof NullObject) {
            //    return Rst.create().setCode(500).setData(new NullObject()).setMsg("查询无果！");
            //}
            return Rst.create().setCode(200).setData(redisObject).setMsg("OK!");
        }
        try{
            Person person = personService.getById(id);
            if(UtilObject.isNotEmpty(person)) {
                redisService.setItemStr(String.valueOf(id), person.toString());
                return Rst.create().setCode(200).setMsg("OK!").setData(person);
            }
            //else {
            //    redisService.setItemStr(String.valueOf(id), new NullObject().toString());
            //}
        }finally {
        }
        return Rst.create().setCode(500).setData(new NullObject()).setMsg("查询无果！");
    }

}
