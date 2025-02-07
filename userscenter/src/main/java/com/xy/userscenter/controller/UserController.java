package com.xy.userscenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xy.userscenter.common.BaseResponse;
import com.xy.userscenter.common.ErrorCode;
import com.xy.userscenter.common.ResultUtils;
import com.xy.userscenter.exception.BussinessException;
import com.xy.userscenter.model.domain.User;
import com.xy.userscenter.model.domain.request.UserLoginRequest;
import com.xy.userscenter.model.domain.request.UserRegisterRequest;
import com.xy.userscenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.xy.userscenter.contant.UserConstant.ADMIN_ROLE;
import static com.xy.userscenter.contant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if(userRegisterRequest==null){
            //return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        long result =userService.userregister(userAccount, userPassword, checkPassword,planetCode);
        //return new BaseResponse<>(0,result,"ok");
        return ResultUtils.success(result);
    }
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if(userLoginRequest==null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        User user =userService.dologin(userAccount, userPassword,request);
        //return new BaseResponse<>(0,user,"ok");
        return ResultUtils.success(user);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if(request==null){
            return null;
        }
        int result =userService.userLogout(request);
        return ResultUtils.success(result);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj =request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if(currentUser==null){
            return null;
        }
        long userId =currentUser.getId();
        //todo 校验userrole
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username,HttpServletRequest request) {
        if(!isAdmin(request)){
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNoneBlank(username)){
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> List=userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(List);
    }
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id,HttpServletRequest request) {
        if(!isAdmin(request)){
            return null;
        }
        if(id<=0){
            return null;
        }
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }
    private boolean isAdmin(HttpServletRequest request) {
        //管理员进行查询
        Object userObj =request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        if(user==null||user.getUserRole()!=ADMIN_ROLE){
            return false;
        }
        return true;
    }
}
