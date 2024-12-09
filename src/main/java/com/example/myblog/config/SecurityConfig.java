package com.example.myblog.config;

import com.example.myblog.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * 安全配置类，用于配置Spring Security相关的设置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * 配置Spring Security的安全过滤链
     *
     * @param http HttpSecurity对象，用于配置安全策略
     * @return 配置好的SecurityFilterChain对象
     * @throws Exception 如果配置过程中发生异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 配置CORS（跨域资源共享）
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 禁用CSRF保护，适用于API接口（因为我们是无状态的 REST API）
                .csrf(csrf -> csrf.disable())
                // 配置请求授权规则
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        // 放行用户相关接口
                        .requestMatchers("/user/**","/captcha/**","/oss/**").permitAll()
                        // 放行文章、分类、标签、评论接口
                        .requestMatchers("/articles/**", "/category/**", "/tags/**", "/comment/**").permitAll()
                        // 其他请求需要认证
                        .anyRequest().authenticated()
                )
                // 添加JwtRequestFilter到过滤器链中
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                // 配置登出功能，允许所有用户访问登出接口
                .logout(logout -> logout.permitAll());
        return http.build();
    }

    /**
     * 配置CORS（跨域资源共享）
     *
     * @return CorsConfigurationSource对象，用于配置CORS策略
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 允许的源地址
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        // 允许的HTTP方法
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // 允许的请求头
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // 允许凭证（cookies等）传递
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 注册CORS配置
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * 配置密码编码器
     *
     * @return PasswordEncoder对象，用于对用户密码进行加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
