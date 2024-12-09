package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.entity.*;
import com.example.myblog.entity.dto.ArticleDto;
import com.example.myblog.entity.vo.ArticleVo;
import com.example.myblog.mapper.ArticleMapper;
import com.example.myblog.mapper.ArticleTagMapper;
import com.example.myblog.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/***
 *@title ArticleServiceImpl
 *@CreateTime 2024/11/23 14:59
 *@description ArticleServiceImpl
 **/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    @Lazy
    private UserService userService;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleCategoryService articleCategoryService;
    @Autowired
    private ArticleTagService articleTagService;

    /**
     * @return Page<Article>
     * 根据分页参数获取文章列表
     * @Param currentPage 当前页码
     * @Param pageSize 每页显示的记录数
     * @Param keyword 关键词
     */
    @Override
    public Page<Article> getArticleList(int currentPage, int pageSize, String keyword) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.like("title", keyword);
        }
        Page<Article> page = new Page<>(currentPage, pageSize);
        return articleMapper.selectPage(page, queryWrapper);
    }

    /**
     * @return List<Article>
     * 根据标签名获取文章列表
     * @Param tagName 标签名
     */
    @Override
    public List<Article> getArticleListByTagName(String tagName) {
        return articleMapper.selectArticleListByTagName(tagName);
    }

    /**
     * @return List<Article>
     * 根据分类名获取文章列表
     * @Param categoryName 分类名
     */
    @Override
    public List<Article> getArticleListByCategoryName(String categoryName) {
        return articleMapper.selectArticleListByCategoryName(categoryName);
    }

    /**
     * 根据用户id获取文章列表
     * @Param id 用户id
     * @return Page<Article>
     */
    @Override
    public Page<Article> getArticleListByUserId(Integer id, int currentPage, int pageSize) {
        //如果是管理员，则返回所有文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (id != 1) {
            queryWrapper.eq(Article::getUserId, id);
        }
        Page<Article> page = new Page<>(currentPage, pageSize);
        return articleMapper.selectPage(page, queryWrapper);
    }
    /**
     * @return ArticleVo
     * 根据文章id获取文章详情
     * @Param articleId 文章id
     */
    @Override
    public ArticleVo getArticleByArticleId(Integer articleId) {
        Article article = articleMapper.selectById(articleId);
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        // 设置分类名
        List<Integer> categoryIds = articleCategoryService.getCategoryIdsByArticleId(articleId);
        List<String> categoryNames = new ArrayList<>();
        for (Integer categoryId : categoryIds){
            Category category = categoryService.getById(categoryId);
            categoryNames.add(category.getCategoryName());
        }
        articleVo.setCategoryNames(categoryNames);
        // 设置标签名
        List<Integer> tagIds = articleTagService.getTagIdsByArticleId(articleId);
        List<String> tagNames = new ArrayList<>();
        for (Integer tagId : tagIds){
            Tag tag = tagService.getById(tagId);
            tagNames.add(tag.getTagName());
        }
        articleVo.setTagNames(tagNames);
        // 设置作者名
        User user = userService.getById(article.getUserId());
        articleVo.setAuthor(user.getUsername());
        return articleVo;
    }


    /**
     * @Param articleDto 文章传输对象
     * 添加文章
     */
    @Override
    @Transactional
    public void addArticle(ArticleDto articleDto) {
        Article article = new Article();
        BeanUtils.copyProperties(articleDto, article);
        List<String> categoryNames = articleDto.getCategoryNames();
        List<String> tagNames = articleDto.getTagNames();

        // 保存文章
        articleMapper.insert(article);

        // 处理分类
        for (String categoryName : categoryNames) {
            Category category = categoryService.getCategoryByCategoryName(categoryName);
            if (category == null) {
                // 如果分类不存在，则创建新的分类
                category = new Category();
                category.setCategoryName(categoryName);
                categoryService.save(category);
                // 获取新的分类的id
                category = categoryService.getCategoryByCategoryName(categoryName);
            }
            // 更新文章和分类映射
            ArticleCategory articleCategory = new ArticleCategory();
            articleCategory.setArticleId(article.getId());
            articleCategory.setCategoryId(category.getId());
            articleCategoryService.addArticleCategory(articleCategory);
        }

        // 处理标签
        for (String tagName : tagNames) {
            Tag tag = tagService.getTagByTagName(tagName);
            if (tag == null) {
                // 如果标签不存在，则创建新的标签
                tag = new Tag();
                tag.setTagName(tagName);
                tagService.save(tag);
                // 获取新的标签的id
                tag = tagService.getTagByTagName(tagName);
            }
            // 更新文章和标签映射
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(article.getId());
            articleTag.setTagId(tag.getId());
            articleTagService.addArticleTag(articleTag);
        }
    }

    /**
     * @Param articleId 文章id
     * 删除文章
     */
    @Override
    @Transactional
    public void deleteByArticleId(Integer articleId) {
        //删除文章和分类映射
        articleCategoryService.deleteByArticleId(articleId);
        //删除文章和标签映射
        articleTagService.deleteByArticleId(articleId);
        //删除该文章下的所有评论
        commentService.deleteByArticleId(articleId);
        //删除文章
        articleMapper.deleteById(articleId);
    }
    /**
     * @Param article 文章对象
     * 更新文章
     */
    @Override
    public void updateArticle(Article article) {
        articleMapper.updateById(article);
    }
}
