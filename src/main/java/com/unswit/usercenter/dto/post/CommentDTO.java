package com.unswit.usercenter.dto.blog;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class CommentDTO implements Serializable {
    private Long blogId;

    /**
     * 评论 ID
     */
    private Long id;

    /**
     * 作者 ID
     */
    private String authorId;

    /**
     * 作者名称
     */
    private String authorName;

    /**
     * 作者头像
     */
    private String authorAvatar;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private Date createTime;

    /**
     * 子评论（仅在一级评论中返回）
     */
    private List<CommentDTO> children;

}
