package com.unswit.usercenter.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 一个笔记卡片
 */
@Data
public class NoteSummaryDTO {
    /**
     * 课程id
     */
    private Long id;
    /**
     * 笔记标题
     */
    private String title;
    /**
     * 简介摘要-
     */
    private String toolTip;
    /**
     * 更新时间
     */
    private Date updateTime;

}
