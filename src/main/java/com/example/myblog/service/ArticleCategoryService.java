package com.example.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myblog.entity.ArticleCategory;

import java.util.List;

/***
 *@title ArticleCategoryService
 *@CreateTime 2024/11/30 17:32
 *@description
 **/
public interface ArticleCategoryService extends IService<ArticleCategory> {
    /**
     * @param articleCategory 实体对象
     * @description: 添加文章分类映射
     */
    void addArticleCategory(ArticleCategory articleCategory);
    /**
     * @param articleId 文章id
     * @description: 根据文章id删除文章分类映射
     */
    void deleteByArticleId(Integer articleId);
    /**
     * @param articleId 文章id
     * @return java.util.List<java.lang.Integer>
     * @description: 根据文章id获取分类id列表
     */
    List<Integer> getCategoryIdsByArticleId(Integer articleId);
}
