package com.local.redisbilibili.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.local.redisbilibili.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PersonMapper extends BaseMapper<Person> {

    @Select("SELECT F_ID FROM T_PERSON")
    public List<String> getAllIds();

}
