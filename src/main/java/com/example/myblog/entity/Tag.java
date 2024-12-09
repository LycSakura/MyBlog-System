package com.example.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/***
 *@title tag
 *@CreateTime 2024/11/19 15:41
 *@description 标签表
 **/
@Data
@TableName("tags")
public class Tag {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String tagName;
}
