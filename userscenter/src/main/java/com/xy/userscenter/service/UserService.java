package com.xy.userscenter.service;

import com.xy.userscenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author 25848
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-02-04 09:26:15
*/
public interface UserService extends IService<User> {
    /**
     * USER
     * @param userAccount 账户
     * @param userPassword 密码
     * @param checkPassword 校验
     * @return 新id
     */

    long userregister(String userAccount, String userPassword,String checkPassword,String planetCode);

    /**
     *
     * @param userAccount
     * @param userPassword
     * @return 脱敏后的用户信息
     */
    User dologin(String userAccount, String userPassword, HttpServletRequest request);

    User getSafetyUser(User originUser);

    int userLogout(HttpServletRequest request);
}
