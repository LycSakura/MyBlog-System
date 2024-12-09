package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.entity.Category;
import com.example.myblog.entity.vo.CategoryVo;
import com.example.myblog.mapper.CategoryMapper;
import com.example.myblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 *@title CategoryServiceImpl
 *@CreateTime 2024/11/23 20:36
 *@description CategoryServiceImpl
 **/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * @return List<CategoryVo>
     * @description: 查询分类列表
     */
    @Override
    public List<CategoryVo> getCategoryListWithCount() {
        return categoryMapper.selectCategoryListWithCount();
    }

    /**
     * @param id 用户id
     * @return Category
     * @description: 根据用户id查询分类列表
     */
    @Override
    public List<Category> getCategoryListByUserId(Long id) {
        return categoryMapper.selectCategoryListByUserId(id);
    }

    /**
     * @param categoryName 分类名称
     * @return Category
     * @description: 根据分类名称查询分类
     */
    @Override
    public Category getCategoryByCategoryName(String categoryName) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getCategoryName, categoryName);
        return categoryMapper.selectOne(queryWrapper);
    }
}
