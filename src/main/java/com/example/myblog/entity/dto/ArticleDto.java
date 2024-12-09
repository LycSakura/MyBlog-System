package com.example.myblog.entity.dto;

import com.example.myblog.entity.Article;
import lombok.Data;

import java.util.List;

/***
 *@title ArticleDto
 *@CreateTime 2024/11/30 17:09
 *@description ArticleDto
 **/
@Data
public class ArticleDto extends Article {
    private Integer userId;
    private String title;
    private String content;
    private List<String> categoryNames;
    private List<String> tagNames;
}
