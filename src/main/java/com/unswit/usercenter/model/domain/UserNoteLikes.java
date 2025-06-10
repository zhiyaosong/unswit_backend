package com.unswit.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 用户笔记点赞表
 * @TableName user_note_likes
 */
@TableName(value ="user_note_likes")
@Data
public class UserNoteLikes {
    /**
     *
     */
    @TableId(value = "userId")
    private String userId;

    /**
     *
     */
    @TableField("noteId")
    private Long noteId;

    /**
     *
     */
    private Date likedAt;
}