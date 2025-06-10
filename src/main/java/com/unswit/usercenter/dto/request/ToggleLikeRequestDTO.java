package com.unswit.usercenter.dto.request;

import lombok.Data;

@Data
public class ToggleLikeRequestDTO {
    private String userId;
    private Long noteId;
}
