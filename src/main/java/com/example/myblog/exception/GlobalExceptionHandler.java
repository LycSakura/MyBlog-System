package com.example.myblog.exception;

import com.example.myblog.utils.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/***
 *@title GlobalException
 *@CreateTime 2024/11/19 15:55
 *@description 全局异常处理器
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * @param ex 异常对象
     * @return Result<String>
     * @description: 处理 SQLIntegrityConstraintViolationException 异常
     */

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        // 日志记录异常信息
        ex.printStackTrace();

        // 判断具体的错误信息，返回友好的错误提示
        String errorMessage = ex.getMessage();
        if (errorMessage.contains("Duplicate entry")) {
            if (errorMessage.contains("username")) {
                return Result.error(400, "该用户名已被占用，请更换用户名");
            } else if (errorMessage.contains("email")) {
                return Result.error(400, "该邮箱已被占用，请更换邮箱");
            } else if (errorMessage.contains("articles.title")) {
                return Result.error(400, "该标题已被占用，请更换标题");
            }
        }

        // 如果无法确定具体错误，返回通用的错误信息
        return Result.error(500, "服务器内部错误");
    }

    /**
     * @param ex 异常对象
     * @return Result<Map < String>>
     * @description: 处理参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return Result.error(400, "参数校验失败", errors);
    }
}

