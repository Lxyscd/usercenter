package com.xy.userscenter.service;
import java.util.Date;

import com.xy.userscenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();

        user.setUsername("1");
        user.setAvatarUrl("1");
        user.setGender(0);
        user.setUserPassword("1");
        user.setPhone("1");
        user.setEmail("1");
        user.setId(0L);
        user.setUserAccount("123");



        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void userregister() {
        String userAccount = "lxy";
        String userPassword = "";
        String checkPassword = "123456";
        String planetCode = "1";
        long result = userService.userregister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "xy";
        result = userService.userregister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "lxy";
        userPassword = "123456";
        result = userService.userregister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "l xy";
        userPassword = "12345678";
        result = userService.userregister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1, result);

        checkPassword = "123456789";
        result = userService.userregister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "123";
        checkPassword = "12345678";
        Assertions.assertEquals(-1, result);
        userAccount = "lxy";
        result = userService.userregister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1, result);

    }
}