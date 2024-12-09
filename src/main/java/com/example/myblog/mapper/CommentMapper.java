package com.example.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myblog.entity.Comment;
import com.example.myblog.entity.vo.CommentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/***
 *@title CommentMapper
 *@CreateTime 2024/12/7 14:16
 *@description
 **/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 根据文章id查询评论列表
     * @param articleId 文章id
     */
    List<CommentVo> selectArticleVoListByArticleId(Integer articleId);
    /**
     * @return List<CommentVo>
     * @description: 获取所有评论
     */
    List<CommentVo> selectArticleVoList();

}
