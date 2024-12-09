package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.entity.Comment;
import com.example.myblog.entity.vo.CommentVo;
import com.example.myblog.mapper.CommentMapper;
import com.example.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 *@title CommentServiceImpl
 *@CreateTime 2024/12/7 14:17
 *@description
 **/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    /**
     * 根据文章id获取评论列表
     *
     * @param articleId 文章id
     * @return 评论列表
     */
    @Override
    public List<CommentVo> listByArticleId(Integer articleId) {
        return commentMapper.selectArticleVoListByArticleId(articleId);
    }

    /**
     * 获取所有评论
     *
     * @return 所有评论
     */
    @Override
    public List<CommentVo> listAll() {
        return commentMapper.selectArticleVoList();
    }

    /**
     * 删除评论
     *
     * @param articleId 文章id
     */
    @Override
    public void deleteByArticleId(Integer articleId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, articleId);
        commentMapper.delete(queryWrapper);
    }
}
