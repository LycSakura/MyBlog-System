package com.example.myblog.filter;

import com.example.myblog.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/***
 *@title JwtRequestFilter
 *@CreateTime 2024/11/29 16:02
 *@description JWT请求过滤器，用于验证JWT
 **/
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    @Lazy
    private UserDetailsService userDetailsService; // 用户详情服务

    @Autowired
    private JwtUtil jwtUtil; // JWT工具类

    /**
     * 处理每个请求，验证JWT
     *
     * @param request  请求
     * @param response 响应
     * @param chain    过滤器链
     * @throws ServletException 如果处理过程中发生Servlet异常
     * @throws IOException      如果处理过程中发生IO异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        try {
            final String authorizationHeader = request.getHeader("Authorization"); // 获取Authorization头
            final String requestedPath = request.getHeader("X-Requested-Path"); // 获取请求路径

            String username = null;
            String jwt = null;

            // 判断如果是 /dashboard 路径，才需要认证
            if (requestedPath != null && requestedPath.startsWith("/dashboard")) {
                if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                    jwt = authorizationHeader.substring(7); // 提取JWT
                    username = jwtUtil.extractUsername(jwt); // 从JWT中提取用户名
                }

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username); // 加载用户详情

                    if (jwtUtil.validateToken(jwt, userDetails.getUsername())) { // 验证JWT
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()); // 创建认证令牌
                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // 设置详细信息
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken); // 设置认证上下文
                    }
                }
            }

            // 对Token过期等异常进行处理
            if (jwt == null || username == null || !jwtUtil.validateToken(jwt, username)) {
                assert requestedPath != null;
                if (requestedPath.startsWith("/dashboard")) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("{\"message\": \"请先登录后访问\"}");
                    return;
                }
            }

            chain.doFilter(request, response); // 继续过滤器链
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"message\": \"Token已过期，请重新登录\"}");
        }
    }

}
