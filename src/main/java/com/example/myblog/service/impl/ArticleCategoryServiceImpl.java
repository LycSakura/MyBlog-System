package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.entity.ArticleCategory;
import com.example.myblog.mapper.ArticleCategoryMapper;
import com.example.myblog.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 *@title ArticleCategoryServiceImpl
 *@CreateTime 2024/11/30 17:32
 *@description
 **/
@Service
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategory> implements ArticleCategoryService {
    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;
    /**
     * 添加文章分类
     * @param articleCategory 文章分类
     */
    @Override
    public void addArticleCategory(ArticleCategory articleCategory) {
        articleCategoryMapper.insert(articleCategory);
    }
    /**
     * 根据文章id删除文章分类
     * @param articleId 文章id
     */
    @Override
    public void deleteByArticleId(Integer articleId) {
        LambdaQueryWrapper<ArticleCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleCategory::getArticleId,articleId);
        articleCategoryMapper.delete(queryWrapper);
    }
    /**
     * 根据文章id获取分类id列表
     * @param articleId 文章id
     * @return 分类id列表
     */
    @Override
    public List<Integer> getCategoryIdsByArticleId(Integer articleId) {
        LambdaQueryWrapper<ArticleCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleCategory::getArticleId,articleId);
        List<ArticleCategory> articleCategories = articleCategoryMapper.selectList(queryWrapper);
        return articleCategories.stream().map(ArticleCategory::getCategoryId).toList();
    }
}
