package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.entity.Article;
import com.example.myblog.entity.User;
import com.example.myblog.mapper.UserMapper;
import com.example.myblog.service.ArticleService;
import com.example.myblog.service.UserService;
import com.example.myblog.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/***
 *@title UserServiceImpl
 *@CreateTime 2024/11/19 22:21
 *@description
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordUtils passwordUtils;
    @Autowired
    private ArticleService articleService;

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    @Override
    public User selectUserByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        return userMapper.selectOne(queryWrapper);
    }

    /**
     * 注册用户
     *
     * @param user 用户
     */
    @Override
    public void registerUser(User user) {
        // 设置默认角色ID
        if (user.getRoleId() == null) {
            user.setRoleId(2);
        }
        // 对用户密码进行加密
        String encodedPassword = passwordUtils.encodePassword(user.getPassword());
        user.setPassword(encodedPassword);
        userMapper.insert(user);
    }

    /**
     * 登录用户
     *
     * @param user 用户
     * @return 用户
     */
    @Override
    public User loginUser(User user) {
        // 根据用户名查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        User existingUser = userMapper.selectOne(queryWrapper);

        if (existingUser != null && passwordUtils.matchesPassword(user.getPassword(), existingUser.getPassword())) {
            // 密码匹配，验证通过
            return existingUser;
        }

        // 验证失败
        return null;
    }

    /**
     * 获取用户列表
     *
     * @param currentPage 当前页
     * @param pageSize    每页大小
     * @return 用户列表
     */
    @Override
    public Page<User> fetchUserList(Integer currentPage, Integer pageSize) {
        return userMapper.selectPage(new Page<>(currentPage, pageSize), null);
    }
    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    @Override
    @Transactional
    public void deleteByUserId(Integer userId) {
        // 查询出该用户下的所有文章
        Page<Article> articleList = articleService.getArticleListByUserId(userId, 1, 10000);
        // 删除该用户下的所有文章
        for (Article article : articleList.getRecords()) {
            articleService.deleteByArticleId(article.getId());
        }
        // 删除该用户
        userMapper.deleteById(userId);
    }


    /**
     * 根据用户名加载用户详情
     *
     * @param username 用户名
     * @return 用户详情
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), Collections.emptyList());
    }
}
