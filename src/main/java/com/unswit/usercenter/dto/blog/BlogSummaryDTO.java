package com.unswit.usercenter.dto.blog;

import lombok.Data;

import java.util.Date;
@Data
public class BlogSummaryDTO {
    /**
     * 发布blog的用户名
     */
    private String author;
    /**
     * blog id
     */
    private Long id;
    /**
     * blog title
     */
    private String title;
    /**
     * 简介摘要(50个字符+'...')
     */
    private String content;
    /**
     * 更新时间
     */
    private Date updateTime;
}
