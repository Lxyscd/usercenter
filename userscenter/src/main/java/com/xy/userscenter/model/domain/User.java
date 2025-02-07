package com.xy.userscenter.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.Data;

/**
 * 用户
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User {
    /**
     * 
     */
    @TableId(value="id",type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String avatarUrl;

    /**
     * 
     */
    private Integer gender;

    /**
     * 
     */
    private String userPassword;

    /**
     * 
     */
    private String phone;

    /**
     * 
     */
    private String email;

    /**
     * 
     */
    private Integer userStatus;

    //private  Integer userRole;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    @TableLogic
    private Integer isDelete = 0;

    private Integer userRole;

    /**
     * 编号
     */
    private String planetCode;

    /**
     * 
     */
    private String userAccount;
    public void setUsername(String username) {
        this.username = username;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    public void setGender(Integer gender) {
        this.gender = gender;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {

        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }
    public Integer getGender() {
        return gender;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }
    public Integer getUserStatus() {
        return userStatus;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public String getUserAccount() {
        return userAccount;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserRole() {
        return userRole;
    }
    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public void setPlanetCode(String planetCode) {
        this.planetCode = planetCode;
    }
    public String getPlanetCode() {
        return planetCode;
    }
}