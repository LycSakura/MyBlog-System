package com.example.myblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/***
 *@title ArticleCategory
 *@CreateTime 2024/11/19 15:43
 *@description 文章分类关联表
 **/
@Data
@TableName("article_category")
public class ArticleCategory {
    private Integer articleId;
    private Integer categoryId;
}
