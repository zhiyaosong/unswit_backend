package com.unswit.usercenter.dto.note.response;

import lombok.Data;

import java.util.Map;

@Data
public class NoteLikeResponseVO {
    private Map<Long, Integer> likes;
    private Map<Long, Boolean> likedByUser;

}
