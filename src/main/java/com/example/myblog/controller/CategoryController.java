package com.example.myblog.controller;

import com.example.myblog.entity.Category;
import com.example.myblog.entity.vo.CategoryVo;
import com.example.myblog.service.CategoryService;
import com.example.myblog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/***
 *@title CategoryController
 *@CreateTime 2024/11/23 20:32
 *@description CategoryController
 **/
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result<List<CategoryVo>> getCategoryListWithCount() {
        List<CategoryVo> categoryVoList = categoryService.getCategoryListWithCount();
        return Result.success(categoryVoList);
    }

    @GetMapping("/{id}")
    public Result<List<Category>> getCategoryListByUserId(@PathVariable Long id) {
        List<Category> categoryList = categoryService.getCategoryListByUserId(id);
        return Result.success(categoryList);
    }
}
