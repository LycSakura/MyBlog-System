package com.example.myblog.utils;

import lombok.Data;

import java.util.Map;

/***
 *@title Result
 *@CreateTime 2024/11/19 15:52
 *@description 通用的返回类
 **/
@Data
public class Result<T> {
    private Integer code;  // 状态码
    private String message;  // 消息
    private T data;  // 数据

    // 成功的响应
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "成功", data);
    }

    // 成功的响应（无数据）
    public static <T> Result<T> success() {
        return new Result<>(200, "成功", null);
    }

    // 失败的响应
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }
    // 失败的响应（有数据）
    public static <T> Result<T> error(Integer code, String message, T data) {
        return new Result<>(code, message, data);
    }

    // 构造函数
    public Result() {
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}