package com.example.myblog.entity.vo;

import com.example.myblog.entity.Category;
import lombok.Data;

/***
 *@title CategoryVo
 *@CreateTime 2024/11/23 23:21
 *@description
 **/
@Data
public class CategoryVo extends Category {
    private Integer articleCount;
}
