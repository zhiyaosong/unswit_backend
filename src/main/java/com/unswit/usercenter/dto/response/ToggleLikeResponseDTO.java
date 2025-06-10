package com.unswit.usercenter.dto.response;

import lombok.Data;

@Data
public class ToggleLikeResponseDTO {
    /** 切换后的点赞状态：true=已点赞，false=已取消 */
    private boolean liked;
    /** 当前笔记的总点赞数 */
    private int likes;
}
