package com.example.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myblog.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/***
 *@title UserMapper
 *@CreateTime 2024/11/19 15:19
 *@description userMapper
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
