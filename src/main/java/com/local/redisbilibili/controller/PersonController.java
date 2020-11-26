package com.local.redisbilibili.controller;

import com.local.redisbilibili.entity.Person;
import com.local.redisbilibili.service.PersonService;
import com.local.redisbilibili.util.Rst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/getPersonById")
    public Rst.RstBuilder getPersonById(@RequestParam("id") String id) {
        Person person = personService.selectById(id);
        return new Rst.RstBuilder().setCode(200).setMsg("查询成功！").setData(person);
    }

}
