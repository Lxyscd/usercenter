package com.xy.userscenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.userscenter.common.ErrorCode;
import com.xy.userscenter.exception.BussinessException;
import com.xy.userscenter.model.domain.User;
import com.xy.userscenter.service.UserService;
import com.xy.userscenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xy.userscenter.contant.UserConstant.USER_LOGIN_STATE;

/**
* @author 25848
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-02-04 09:26:15
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;
    /**
     * 用户登录态
     */
    private static final String SALT = "XYXY";

    @Override
    public long userregister(String userAccount, String userPassword, String checkPassword,String planetCode)  {
        //1.校验
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)){
            throw new BussinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if(userAccount.length()<4){
            throw new BussinessException(ErrorCode.PARAMS_ERROR,"账户长度太短");
        }
        if(checkPassword.length()<8||checkPassword.length()<8){
            throw new BussinessException(ErrorCode.PARAMS_ERROR,"密码长度小于8");
        }
        if(planetCode.length()>5){
            throw new BussinessException(ErrorCode.PARAMS_ERROR,"编号过长");
        }
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if(matcher.find()){
            return -1;
        }
        if(!checkPassword.equals(userPassword)){
            return -1;
        }
        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);

        long count = userMapper.selectCount(queryWrapper);
        if(count>0){
            throw new BussinessException(ErrorCode.PARAMS_ERROR,"账号重复");
        }

        //编号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode",planetCode);
        count = userMapper.selectCount(queryWrapper);
        if(count>0){
            return -1;
        }
        //加密

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());

        //插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        boolean saveResult = this.save(user);
        if(!saveResult){
            return -1;
        }
        return user.getId();
    }

    @Override
    public User dologin(String userAccount, String userPassword,HttpServletRequest request) {
        //1.校验
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        if(userAccount.length()<3){
            return null;
        }
        if(userPassword.length()<8){
            return null;
        }

        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if(matcher.find()){
            return null;
        }


        //加密

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());

        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        //用户不存在
        if(user == null){
            //log.info("user login failed,useraccount can not match password");
            return null;
        }

        User safetyUser = getSafetyUser(user);
        //记录用户的登陆状态用http
        request.getSession().setAttribute(USER_LOGIN_STATE, user);

        return safetyUser;
    }
    @Override
    public User getSafetyUser(User originUser){
        if(originUser == null){
            return null;
        }

        //用户脱敏
        User safetyUser = new User();
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setId(originUser.getId());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        safetyUser.setCreateTime(originUser.getCreateTime());
        return safetyUser;
    }

    /**
     * 用户注销
     * @param request
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }
}




