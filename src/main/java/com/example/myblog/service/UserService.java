package com.example.myblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myblog.entity.User;

/***
 *@title UserService
 *@CreateTime 2024/11/19 22:21
 *@description
 **/
public interface UserService extends IService<User> {
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户
     */
    User selectUserByUsername(String username);
    /**
     * 注册用户
     * @param user 用户
     */
    void registerUser(User user);
    /**
     * 登录用户
     * @param user 用户
     * @return 用户
     */
    User loginUser(User user);
    /**
     * 获取用户列表
     * @param currentPage 当前页
     * @param pageSize 每页数量
     * @return 用户列表
     */
    Page<User> fetchUserList(Integer currentPage, Integer pageSize);
    /**
     * 根据用户id删除用户
     * @param userId 用户id
     */
    void deleteByUserId(Integer userId);
}
