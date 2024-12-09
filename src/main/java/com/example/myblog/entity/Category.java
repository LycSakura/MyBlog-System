package com.example.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/***
 *@title Category
 *@CreateTime 2024/11/19 15:40
 *@description 分类表
 **/
@Data
@TableName("categories")
public class Category {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String categoryName;
}
