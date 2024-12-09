package com.example.myblog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/***
 *@title PasswordUtils
 *@CreateTime 2024/11/28 17:53
 *@description PasswordUtils
 **/
@Component
public class PasswordUtils {
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 对密码进行加密
     *
     * @param rawPassword 明文密码
     * @return 加密后的密码
     */
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * 验证密码
     *
     * @param rawPassword 明文密码
     * @param encodedPassword 加密后的密码
     * @return 密码是否匹配
     */
    public boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String plainPasswordAdmin = "admin123";
        String plainPasswordSuccess = "success123";
        String plainPasswordFailure = "failure123";

        String encodedPasswordAdmin = encoder.encode(plainPasswordAdmin);
        String encodedPasswordSuccess = encoder.encode(plainPasswordSuccess);
        String encodedPasswordFailure = encoder.encode(plainPasswordFailure);

        System.out.println("Encoded Password Admin: " + encodedPasswordAdmin);
        System.out.println("Encoded Password Success: " + encodedPasswordSuccess);
        System.out.println("Encoded Password Failure: " + encodedPasswordFailure);
    }
}
