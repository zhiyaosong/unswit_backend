package com.unswit.usercenter.dto.post.response;

import lombok.Data;

import java.util.Map;

@Data
public class PostLikeResponseVO {

    private Map<Long, Integer> likes;

    private Map<Long, Boolean> likedByUser;

}
