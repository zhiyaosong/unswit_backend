package com.unswit.usercenter.dto.post.request;

import lombok.Data;

@Data
public class ToggleLikePostRequestVO {
    private String userId;
    private Long postId;
}
