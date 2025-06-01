package com.unswit.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User {
    private Long id;

    private String username;

    private String userAccount;

    private String avatarUrl;

    private Integer gender;

    private String userPassword;

    private String admissionTime;

    private String email;

    private String selfDescription;

    private Integer userStatus;

    private Date createTime;

    private Date updateTime;

    private Integer isDelete;

    private Integer userRole;

    private String phone;

    private Integer isMember;
}