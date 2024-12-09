package com.example.myblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myblog.entity.Article;
import com.example.myblog.entity.dto.ArticleDto;
import com.example.myblog.entity.vo.ArticleVo;
import com.example.myblog.service.ArticleService;
import com.example.myblog.utils.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/***
 *@title ArticleController
 *@CreateTime 2024/11/23 14:59
 *@description ArticleController
 **/
@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping
    public Result<Page<Article>> getArticleList(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String keyword) {
        Page<Article> page = articleService.getArticleList(currentPage, pageSize, keyword);
        return Result.success(page);
    }

    @GetMapping("/tag/{tagName}")
    public Result<List<Article>> getArticleListByTagName(@PathVariable String tagName) {
        List<Article> articleList = articleService.getArticleListByTagName(tagName);
        return Result.success(articleList);
    }

    @GetMapping("/category/{categoryName}")
    public Result<List<Article>> getArticleListByCategoryName(@PathVariable String categoryName) {
        List<Article> articleList = articleService.getArticleListByCategoryName(categoryName);
        return Result.success(articleList);
    }

    @GetMapping("/{id}")
    public Result<Page<Article>> getArticleListByUserId(@PathVariable Integer id,
                                                        @RequestParam(defaultValue = "1") int currentPage,
                                                        @RequestParam(defaultValue = "10") int pageSize) {
        Page<Article> page = articleService.getArticleListByUserId(id, currentPage, pageSize);
        return Result.success(page);
    }

    @PostMapping("/add")
    public Result<String> addArticle(@RequestBody ArticleDto articleDto) {
        articleService.addArticle(articleDto);
        return Result.success("添加成功");
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteArticle(@PathVariable Integer id) {
        articleService.deleteByArticleId(id);
        return Result.success("删除成功");
    }

    @GetMapping("/detail/{id}")
    public Result<ArticleVo> getArticleByArticleId(@PathVariable Integer id) {
        ArticleVo articleVo = articleService.getArticleByArticleId(id);
        return Result.success(articleVo);
    }

    @PutMapping
    public Result<String> updateArticle(@RequestBody Article article) {
        articleService.updateArticle(article);
        return Result.success("更新成功");
    }
}
