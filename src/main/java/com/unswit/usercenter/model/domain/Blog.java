package com.unswit.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 帖子
 * @TableName blog
 */
@TableName(value ="blog")
@Data
public class Blog {
    /**
     * 帖子id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * id,UUID（无中划线32位）
     */
    private String userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 帖子照片，最多9张，多张以","隔开
     */
    private String images;

    /**
     * 帖子内容
     */
    private String content;

    /**
     * 点赞数量
     */
    private Integer liked;

    /**
     * 评论数量
     */
    private Integer comments;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;
}