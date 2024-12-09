package com.example.myblog.entity.vo;

import com.example.myblog.entity.Comment;
import lombok.Data;

/***
 *@title CommentVo
 *@CreateTime 2024/12/7 16:07
 *@description
 **/
@Data
public class CommentVo extends Comment {
    private String username;
    private String photo;
}
