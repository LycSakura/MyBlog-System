package com.example.myblog;

import com.example.myblog.entity.User;
import com.example.myblog.mapper.UserMapper;
import com.example.myblog.service.UserService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class MyBlogApplicationTests {

    @Autowired
    private UserService userService;


    @Test
    void testSelectUserByUsername() {
        User testUser = userService.selectUserByUsername("test_user");
        System.out.println(testUser);
    }


}
