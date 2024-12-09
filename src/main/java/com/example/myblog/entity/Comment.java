package com.example.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

/***
 *@title Comment
 *@CreateTime 2024/11/19 15:45
 *@description 评论表
 **/
@Data
@TableName("comments")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotNull(message = "文章id不能为空")
    private Integer articleId;
    @NotNull(message = "用户id不能为空")
    private Integer userId;
    @NotNull(message = "评论内容不能为空")
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;
}
