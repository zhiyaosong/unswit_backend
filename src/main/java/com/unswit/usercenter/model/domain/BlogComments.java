package com.unswit.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * blog comments
 * @TableName blog_comments
 */
@TableName(value = "blog_comments")
@Data
public class BlogComments {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * id,UUID（无中划线32位）
     */
    private String userId;

    /**
     * blog_id
     */
    @TableField("blogId")
    private Long blogId;

    /**
     * 关联的1级评论id，如果是一级评论，则值为null
     */
    @TableField("parentId")
    private Long parentId;

    /**
     * 回复的内容
     */
    private String content;

    /**
     * 状态，0：正常，1：被举报，2：禁止查看
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    private Date updateTime;

    // 用于嵌套二级评论（非数据库字段）
    @TableField(exist = false)
    private List<BlogComments> children;

}
