package com.xy.userscenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userAccount;

    private String userPassword;

    public String getUserAccount() {
        return userAccount;
    }
    public String getUserPassword() {
        return userPassword;
    }

}
