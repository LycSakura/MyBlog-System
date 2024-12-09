package com.example.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myblog.entity.Category;
import com.example.myblog.entity.vo.CategoryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/***
 *@title CategoryMapper
 *@CreateTime 2024/11/23 20:37
 *@description CategoryMapper
 **/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 查询分类列表，并统计每个分类下的文章数量
     * @return List<CategoryVo>
     */
    List<CategoryVo> selectCategoryListWithCount();
    /**
     * 根据用户id查询分类列表
     * @param id 用户id
     * @return Category
     */
    List<Category> selectCategoryListByUserId(Long id);
}
