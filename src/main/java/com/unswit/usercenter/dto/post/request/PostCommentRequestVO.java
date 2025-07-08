package com.unswit.usercenter.dto.blog.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BlogCommentRequestVO implements Serializable {

    /**
     * 博客 ID
     */
    private Long blogId;

    /**
     * 关联的一级评论 ID，如果是一级评论，则值为 0
     */
    private Long parentId;

    /**
     * 回复的内容
     */
//    @NotEmpty(message = "评论内容不能为空")
    private String content;
}
