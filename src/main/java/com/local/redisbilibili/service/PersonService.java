package com.local.redisbilibili.service;

import com.local.redisbilibili.dao.PersonMapper;
import com.local.redisbilibili.entity.Person;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PersonService {

    @Resource
    private PersonMapper personMapper;

    public Person getById(String id) {
        return personMapper.selectById(id);
    }

    public List<Person> list() {
        return personMapper.selectList(null);
    }

    public List<String> getAllIds() {
        return personMapper.getAllIds();
    }
}
