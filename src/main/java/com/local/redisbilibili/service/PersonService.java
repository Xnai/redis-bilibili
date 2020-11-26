package com.local.redisbilibili.service;

import com.local.redisbilibili.dao.PersonMapper;
import com.local.redisbilibili.entity.Person;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PersonService {

    @Resource
    private PersonMapper personMapper;

    public Person getById(String id) {
        return personMapper.selectById(id);
    }
}
