package com.example.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.myblog.validation.LoginGroup;
import com.example.myblog.validation.RegistrationGroup;
import com.example.myblog.validation.UpdateGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.util.Set;

/***
 *@title User
 *@CreateTime 2024/11/19 15:20
 *@description 用户表
 **/
@Data
@TableName("users")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotBlank(message = "用户名不能为空", groups = {RegistrationGroup.class, LoginGroup.class})
    @Size(min = 3, max = 20, message = "用户名长度在 3 到 20 个字符", groups = {RegistrationGroup.class, LoginGroup.class})
    private String username;

    @NotBlank(message = "密码不能为空", groups = {RegistrationGroup.class, LoginGroup.class})
    @Size(min = 6, max = 20, message = "密码长度在 6 到 20 个字符", groups = {RegistrationGroup.class, LoginGroup.class})
    private String password;

    @NotBlank(message = "邮箱不能为空", groups = {RegistrationGroup.class, UpdateGroup.class})
    @Email(message = "请输入正确的邮箱地址", groups = {RegistrationGroup.class, UpdateGroup.class})
    private String email;

    private String photo;
    private Integer roleId;

}
