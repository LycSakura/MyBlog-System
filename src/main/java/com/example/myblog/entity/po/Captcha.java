package com.example.myblog.entity.po;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/***
 *@title Captcha
 *@CreateTime 2024/11/28 17:40
 *@description 验证码实体类
 **/
@Data
public class Captcha {

    @NotBlank(message = "验证码不能为空")
    @Size(min = 4, max = 4, message = "验证码为4位")
    private String captcha;

    @NotNull(message = "验证码密钥不能为空")
    private String captchaKey;
}
