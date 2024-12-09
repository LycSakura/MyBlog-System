package com.example.myblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myblog.entity.Article;
import com.example.myblog.entity.dto.ArticleDto;
import com.example.myblog.entity.vo.ArticleVo;
import com.example.myblog.service.impl.ArticleServiceImpl;

import java.util.List;

/***
 *@title ArticleService
 *@CreateTime 2024/11/23 14:58
 *@description ArticleService
 **/
public interface ArticleService extends IService<Article> {
    /**
     * 获取文章列表
     *
     * @param currentPage 当前页码
     * @param pageSize    每页数量
     * @param keyword     关键词
     * @return 文章列表
     */
    Page<Article> getArticleList(int currentPage, int pageSize, String keyword);

    /**
     * 根据标签名获取文章列表
     *
     * @param tagName 标签名
     * @return 文章列表
     */
    List<Article> getArticleListByTagName(String tagName);

    /**
     * @param categoryName 分类名
     * @return List<Article>
     * @description: 根据分类名获取文章列表
     */
    List<Article> getArticleListByCategoryName(String categoryName);
    /**
     * @param id 用户id
     * @param currentPage 当前页码
     * @Param pageSize 每页数量
     * @return Page<Article>
     * @description: 根据用户id获取文章列表
     */
    Page<Article> getArticleListByUserId(Integer id, int currentPage, int pageSize);

    /**
     * @param articleId 文章id
     * @return ArticleVo
     * @description: 获取文章详情
     */
    ArticleVo getArticleByArticleId(Integer articleId);
    /**
     * @param articleDto 文章对象
     * @description: 添加文章
     */
    void addArticle(ArticleDto articleDto);
    /**
     * @param articleId 文章id
     * @description: 删除文章
     */
    void deleteByArticleId(Integer articleId);
    /**
     * @param article 文章对象
     * @description: 更新文章
     */
    void updateArticle(Article article);
}
