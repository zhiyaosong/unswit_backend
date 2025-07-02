package com.unswit.usercenter.dto.note.request;

import lombok.Data;

@Data
public class ToggleLikeRequestVO {
    private String userId;
    private Long noteId;
}
