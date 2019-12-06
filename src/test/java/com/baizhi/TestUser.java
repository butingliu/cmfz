package com.baizhi;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestUser {
    @Autowired
    private UserService userService;

    @Test
    public void qq() {
        User user = userService.queryUserByPhoneAndPwd("13512345678", "123456");
        System.out.println(user);
    }
}
