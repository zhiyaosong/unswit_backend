package com.unswit.usercenter.dto.user;

import lombok.Data;

@Data
public class UserStatsDTO {
    /**
     * 笔记数
     */
    private Long noteCount;
    /**
     * 帖子量
     */
    private Long postCount;
    /**
     * 笔记点赞量
     */
    private Long noteLikeCount;
    /**
     * 帖子点赞量
     */
    private Long postLikeCount;
}
