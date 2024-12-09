package com.example.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myblog.entity.Comment;
import com.example.myblog.entity.vo.CommentVo;

import java.util.List;

/***
 *@title CommentService
 *@CreateTime 2024/12/7 14:16
 *@description
 **/
public interface CommentService extends IService<Comment> {
    /**
     * 根据文章id查询评论
     * @param articleId 文章id
     * @return 评论列表
     */
    List<CommentVo> listByArticleId(Integer articleId);
    /**
     * 查询所有评论
     * @return 评论列表
     */
    List<CommentVo> listAll();

    /**
     * 根据文章id删除评论
     * @param articleId 文章id
     */
    void deleteByArticleId(Integer articleId);
}
