package com.local.redisbilibili.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("T_PERSON")
public class Person implements Serializable {

    @TableId("F_ID")
    private String id;

    @TableField("F_USERNAME")
    private String username;

    @TableField("F_GENDER")
    private String gender;

    @TableField("F_BIRTHDAY")
    private String birthday;

    @TableField("F_HEIGHT")
    private String height;

    @TableField("F_WEIGHT")
    private String weight;
}
