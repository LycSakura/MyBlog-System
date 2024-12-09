package com.example.myblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myblog.entity.User;
//import com.example.myblog.service.UserService;
import com.example.myblog.service.UserService;
import com.example.myblog.utils.JwtUtil;
import com.example.myblog.utils.Result;
import com.example.myblog.validation.LoginGroup;
import com.example.myblog.validation.RegistrationGroup;
import com.example.myblog.validation.UpdateGroup;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 *@title UserController
 *@CreateTime 2024/11/19 15:50
 *@description
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    public UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public Result<String> register(@RequestBody @Validated({RegistrationGroup.class}) User user) {
        userService.registerUser(user);
        return Result.success("注册成功");
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody @Validated(LoginGroup.class) User user) {
        User authenticatedUser = userService.loginUser(user);
        if (authenticatedUser != null) {
            String token = jwtUtil.generateToken(authenticatedUser.getUsername());
            return Result.success(token);
        } else {
            return Result.error(400, "用户名或密码错误");
        }
    }

    @GetMapping("/userInfo")
    public Result<User> getUserInfo(HttpServletRequest request) {
        // 尝试从 Authorization 头部获取 JWT Token
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            String username = jwtUtil.extractUsername(jwt);
            if (username != null) {
                // 如果通过 JWT 提取了用户名，手动加载用户信息
                User user = new User();
                user.setUsername(username);
                User userInfo = userService.selectUserByUsername(user.getUsername());
                BeanUtils.copyProperties(userInfo, user);
                user.setPassword(null);
                return Result.success(user);
            }
        }

        // 如果没有 JWT 或用户名无效，返回未认证错误
        return Result.error(401, "未认证用户");
    }

    @GetMapping("/userList")
    public Result<Page<User>> getUserList(@RequestParam(defaultValue = "1") Integer currentPage,
                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<User> page = userService.fetchUserList(currentPage, pageSize);
        return Result.success(page);
    }

    @PutMapping
    public Result<String> updateUser(@RequestBody @Validated({UpdateGroup.class}) User user) {
        userService.updateById(user);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable Integer id) {
        userService.deleteByUserId(id);
        return Result.success("删除成功");
    }
}
