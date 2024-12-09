package com.example.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myblog.entity.Category;
import com.example.myblog.entity.vo.CategoryVo;

import java.util.List;

/***
 *@title CategoryService
 *@CreateTime 2024/11/23 20:36
 *@description CategoryService
 **/
public interface CategoryService extends IService<Category> {
    /**
     * @return List<CategoryVo>
     * @description: 查询分类列表，并返回分类名称和文章数量
     */
    List<CategoryVo> getCategoryListWithCount();
    /**
     * @param id 用户id
     * @return Category
     * @description: 查询分类列表，根据用户id
     */
    List<Category> getCategoryListByUserId(Long id);
    /**
     * @param categoryName 分类名称
     * @return Category
     * @description: 根据分类名称查询分类
     */
    Category getCategoryByCategoryName(String categoryName);

}
