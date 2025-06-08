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
    private Long userId;
    // NoteRequestDTO中的字段:
    private String title;
    private String author;
    private String lecturer;
    private String link;
    private String enrollTime;
    // courseCode => courseId
    private Long courseId;

    private String toolTip;  //暂时不用

    private Integer noteStatus; // 默认正常

    private Integer isChecked;  // 默认未检查

    private Date createTime;

    private Date updateTime;

    private Integer isDelete;

    private Integer isOfficial;
}