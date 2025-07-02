package com.unswit.usercenter.dto.user;

import lombok.Data;

@Data
public class AccountCenterSummaryDTO {
    /**
     * 笔记数
     */
    private Long noteCount;
    /**
     * 帖子量
     */
    private Long blogCount;
    /**
     * 点赞量
     */
    private Long likeCount;
}
