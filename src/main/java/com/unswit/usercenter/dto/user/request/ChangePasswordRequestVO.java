package com.unswit.usercenter.dto.user.request;

import lombok.Data;

@Data
public class ChangePasswordRequestVO {
    private String oldPassword;
    private String newPassword;
}
