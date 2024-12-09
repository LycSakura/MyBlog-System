package com.example.myblog.entity.vo;

import com.example.myblog.entity.Article;
import lombok.Data;

import java.util.List;

/***
 *@title ArticleVo
 *@CreateTime 2024/11/30 21:25
 *@description
 **/
@Data
public class ArticleVo extends Article {
    private String author;
    private List<String> categoryNames;
    private List<String> tagNames;
}
