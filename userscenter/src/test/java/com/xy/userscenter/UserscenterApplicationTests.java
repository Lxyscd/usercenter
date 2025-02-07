package com.xy.userscenter;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.xy.userscenter.mapper.UserMapper;
import com.xy.userscenter.model.domain.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@SpringBootTest
class UserscenterApplicationTests {

    @Test
    void testDigest() {
        String newPassword = DigestUtils.md5DigestAsHex(("xyxy"+"mypassword").getBytes());
        System.out.println(newPassword);
    }




    void contextLoads() {

    }

}
