package com.example.myblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/***
 *@title ArticleTag
 *@CreateTime 2024/11/19 15:44
 *@description 文章标签关联表
 **/
@Data
@TableName("article_tag")
public class ArticleTag {
    private Integer articleId;
    private Integer tagId;
}
