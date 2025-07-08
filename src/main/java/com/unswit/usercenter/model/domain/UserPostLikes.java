package com.unswit.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 用户帖子点赞表
 * @TableName user_post_likes
 */
@TableName(value ="user_post_likes")
@Data
public class UserPostLikes {
    /**
     * 
     */
    @TableId(value = "userId")
    private String userId;

    /**
     * 
     */
    @TableField("postId")
    private Long postId;

    /**
     * 
     */
    private Date likedAt;
}