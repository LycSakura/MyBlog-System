package com.example.myblog.controller;

import com.example.myblog.entity.Tag;
import com.example.myblog.service.TagService;
import com.example.myblog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/***
 *@title TagController
 *@CreateTime 2024/11/23 20:31
 *@description TagController
 **/
@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping
    public Result<List<Tag>> getTagList() {
        List<Tag> tagList = tagService.list();
        return Result.success(tagList);
    }
}
