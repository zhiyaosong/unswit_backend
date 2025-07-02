package com.unswit.usercenter.dto.blog;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BlogDetailsDTO {
    private Long id;

    /**
     * userId
     */
    private String userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 帖子照片，最多9张，多张以","隔开
     */
    private List<String> images;

    /**
     * 帖子内容
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
     * 创建时间
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;
}
