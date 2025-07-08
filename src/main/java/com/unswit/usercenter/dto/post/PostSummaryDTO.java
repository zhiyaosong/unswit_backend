package com.unswit.usercenter.dto.post;

import lombok.Data;

import java.util.Date;
@Data
public class PostSummaryDTO {
    /**
     * 发布blog的用户名
     */
    private String author;
    /**
     * post id
     */
    private Long id;
    /**
     * post title
     */
    private String title;
    /**
     * 简介摘要(50个字符+'...')
     */
    private String content;
    /**
     * 点赞数量
     */
    private Integer likeCount;
    /**
     * 评论数量
     */
    private Integer commentCount;
    /**
     * 更新时间
     */
    private Date updateTime;
}
