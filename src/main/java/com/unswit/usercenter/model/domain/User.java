package com.unswit.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体
 */
@TableName(value = "user")
@Data
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
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

    @TableLogic
    private Integer isDelete;

    private Integer userRole;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
