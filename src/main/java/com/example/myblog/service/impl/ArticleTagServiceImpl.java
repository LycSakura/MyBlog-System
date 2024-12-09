package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.entity.ArticleTag;
import com.example.myblog.mapper.ArticleTagMapper;
import com.example.myblog.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 *@title ArticleTagServiceImpl
 *@CreateTime 2024/11/30 17:38
 *@description
 **/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
    @Autowired
    private ArticleTagMapper articleTagMapper;

    /**
     * @param articleTag 文章标签
     * @description: 添加文章标签映射
     */
    @Override
    public void addArticleTag(ArticleTag articleTag) {
        articleTagMapper.insert(articleTag);
    }
    /**
     * @param articleId 文章id
     * @description: 根据文章id删除文章标签映射
     */
    @Override
    public void deleteByArticleId(Integer articleId) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, articleId);
        articleTagMapper.delete(queryWrapper);
    }
    /**
     * @param articleId 文章id
     * @return java.util.List<java.lang.Integer>
     * @description: 根据文章id获取标签id
     */
    @Override
    public List<Integer> getTagIdsByArticleId(Integer articleId) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, articleId);
        List<ArticleTag> articleTags = articleTagMapper.selectList(queryWrapper);
        return articleTags.stream().map(ArticleTag::getTagId).toList();
    }
}
