package com.example.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myblog.entity.Article;
import com.example.myblog.entity.vo.ArticleVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/***
 *@title ArticleMapper
 *@CreateTime 2024/11/23 14:53
 *@description ArticleMapper
 **/
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    List<Article> selectArticleListByTagName(String tagName);

    List<Article> selectArticleListByCategoryName(String categoryName);


}
