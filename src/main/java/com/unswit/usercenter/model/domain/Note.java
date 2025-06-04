package com.unswit.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @TableName note
 */
@TableName(value ="note")
@Data
public class Note {
    private Long id;

    private Long courseId;

    private String title;

    private String link;

    private String author;

    private String lecturer;

    private String toolTip;

    private Integer noteStatus;

    private Integer isChecked;

    private Date createTime;

    private Date updateTime;

    private Integer isDelete;

    private Long userId;

    private Integer isOfficial;
}