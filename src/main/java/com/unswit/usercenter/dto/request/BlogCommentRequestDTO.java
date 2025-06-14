package com.unswit.usercenter.dto.request;

import lombok.Data;

import java.util.Date;
@Data
public class BlogCommentRequestDTO {
    private String userId;
    private Long blog_id;

    /**
     * 关联的1级评论id，如果是一级评论，则值为0
     */
    private Long parent_id;

    /**
     * 回复的内容
     */
    private String content;

}
