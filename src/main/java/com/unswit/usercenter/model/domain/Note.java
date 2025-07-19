package com.unswit.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @TableName note
 */
@TableName(value ="note")
@Data
public class Note {
    @TableId
    private Long id;
    // 防止自动映射到user_id
    @TableField(value = "userId")
    private String userId;
    // NoteRequestDTO中的字段:
    private String title;
    private String author;
    private String lecturer;
    private String link;
    private String enrollTime;
    // courseCode => courseId
    private Long courseId;

    private String toolTip;

    private Integer noteStatus; // 默认正常

    private Integer isChecked;  // 默认未检查

    private Date createTime;

    private Date updateTime;

    @TableLogic
    private Integer isDelete;  // 默认0，未删除

    private Integer isOfficial;

    private Integer likeCount;
}