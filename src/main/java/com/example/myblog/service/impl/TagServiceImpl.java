package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.entity.Tag;
import com.example.myblog.mapper.TagMapper;
import com.example.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 *@title TagServiceImpl
 *@CreateTime 2024/11/23 20:34
 *@description TagServiceImpl
 **/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    private TagMapper tagMapper;
    /**
     * 根据标签名称查询标签
     * @param tagName 标签名称
     * @return 标签对象
     */
    @Override
    public Tag getTagByTagName(String tagName) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getTagName, tagName);
        return tagMapper.selectOne(queryWrapper);
    }
}
