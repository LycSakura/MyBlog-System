package com.example.myblog.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/***
 *@title JwtUtil
 *@CreateTime 2024/11/29 16:01
 *@description JWT工具类，用于生成和解析JWT
 **/
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret; // JWT密钥

    @Value("${jwt.expiration}")
    private long expiration; // JWT过期时间

    /**
     * 生成JWT
     * @param username 用户名
     * @return 生成的JWT字符串
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /**
     * 创建JWT
     * @param claims JWT中的声明
     * @param subject JWT的主题（通常是用户名）
     * @return 生成的JWT字符串
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // 设置声明
                .setSubject(subject) // 设置主题
                .setIssuedAt(new Date(System.currentTimeMillis())) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 设置过期时间
                .signWith(SignatureAlgorithm.HS512, secret) // 使用HMAC SHA-512算法签名
                .compact(); // 生成JWT字符串
    }

    /**
     * 验证JWT是否有效
     * @param token JWT字符串
     * @param username 用户名
     * @return 是否有效
     */
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    /**
     * 从JWT中提取用户名
     * @param token JWT字符串
     * @return 提取的用户名
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 从JWT中提取过期时间
     * @param token JWT字符串
     * @return 过期时间
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 从JWT中提取指定的声明
     * @param token JWT字符串
     * @param claimsResolver 提取声明的函数
     * @param <T> 声明的类型
     * @return 提取的声明
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 解析JWT并获取所有声明
     * @param token JWT字符串
     * @return 所有声明
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * 检查JWT是否已过期
     * @param token JWT字符串
     * @return 是否已过期
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}