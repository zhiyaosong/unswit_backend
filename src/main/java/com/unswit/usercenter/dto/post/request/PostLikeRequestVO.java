package com.unswit.usercenter.dto.post.request;

import lombok.Data;

import java.util.List;

@Data
public class PostLikeRequestVO {
    private String userId;
    private List<Long> postIds;
}