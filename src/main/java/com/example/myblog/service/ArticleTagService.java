package com.example.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myblog.entity.ArticleTag;

import java.util.List;

/***
 *@title ArticleTagService
 *@CreateTime 2024/11/30 17:37
 *@description
 **/
public interface ArticleTagService extends IService<ArticleTag> {
    /**
     * 添加文章标签
     * @param articleTag 文章标签
     */
    void addArticleTag(ArticleTag articleTag);
    /**
     * 根据文章id删除文章标签
     * @param articleId 文章id
     */
    void deleteByArticleId(Integer articleId);
    /**
     * 根据文章id获取标签id
     * @param articleId 文章id
     * @return 标签id
     */
    List<Integer> getTagIdsByArticleId(Integer articleId);
}
