package com.unswit.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName course
 */
@TableName(value ="course")
@Data
public class Course {
    @TableId
    private Long id;

    private String code;

    private String title;

    private Integer category;

    private String toolTip;

    private Date createTime;

    private Date updateTime;

    private Integer isDelete;

}