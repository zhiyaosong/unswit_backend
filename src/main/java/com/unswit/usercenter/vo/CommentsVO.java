package com.unswit.usercenter.vo;

import java.io.Serializable;

public class CommentsVO implements Serializable {
    /**
     * comment id
     */
    private Long id;

    /**
     * id,UUID（无中划线32位）
     */
    private String userId;

    /**
     * blog_id
     */
    private Long blog_id;

    /**
     * 关联的1级评论id，如果是一级评论，则值为0
     */
    private Long parent_id;

    /**
     * 回复的内容
     */
    private String content;

    /**
     * 状态，0：正常，1：被举报，2：禁止查看
     */
    private Integer status;

}
