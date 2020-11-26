package com.local.redisbilibili.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.local.redisbilibili.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface PersonMapper extends BaseMapper<Person> {

}
