package com.unswit.usercenter.dto.user.request;

import lombok.Data;

/**
 * 用户注册请求体

 */
@Data
public class UserUpdateInfoRequestVO {
    String userAccount;
    String userName;
    String email;
    String phoneCN;
    String phoneAU;
}
