package com.example.myblog.controller;

import com.example.myblog.entity.Comment;
import com.example.myblog.entity.vo.CommentVo;
import com.example.myblog.service.CommentService;
import com.example.myblog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 *@title CommentController
 *@CreateTime 2024/12/7 14:15
 *@description
 **/
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public Result<List<CommentVo>> getCommentList(@RequestParam(required = false) Integer articleId) {
        List<CommentVo> list = null;
        if (articleId != null) {
            // 获取指定文章的评论
            list = commentService.listByArticleId(articleId);
        } else {
            // 获取所有评论
            list = commentService.listAll();
        }
        return Result.success(list);
    }

    @PostMapping
    public Result<String> addComment(@RequestBody @Validated Comment comment) {
        commentService.save(comment);
        return Result.success("评论成功");
    }
    @DeleteMapping("/{articleId}")
    public Result<String> deleteComment(@PathVariable String articleId) {
        commentService.removeById(articleId);
        return Result.success("删除成功");
    }

}
