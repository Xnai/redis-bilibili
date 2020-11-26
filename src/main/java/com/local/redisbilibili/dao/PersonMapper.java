package com.local.redisbilibili.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.local.redisbilibili.entity.Person;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonMapper extends BaseMapper<Person> {

}
