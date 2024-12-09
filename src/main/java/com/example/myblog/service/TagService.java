package com.example.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myblog.entity.Tag;

/***
 *@title TagService
 *@CreateTime 2024/11/23 20:34
 *@description TagService
 **/
public interface TagService extends IService<Tag> {
    /**
     * 根据标签名获取标签
     * @param tagName 标签名
     * @return tag
     */
    Tag getTagByTagName(String tagName);
}
