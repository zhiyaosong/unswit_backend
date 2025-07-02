package com.unswit.usercenter.dto.user.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体

 */
@Data
public class UserRegisterRequestVO implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userName;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

}

