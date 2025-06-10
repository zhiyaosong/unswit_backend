package com.unswit.usercenter.dto.response;

import lombok.Data;

import java.util.Map;

@Data
public class NoteLikeResponseDTO {
    private Map<Long, Integer> likes;
    private Map<Long, Boolean> likedByUser;

}
