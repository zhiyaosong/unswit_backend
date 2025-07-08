package com.unswit.usercenter.dto.note.request;

import lombok.Data;

@Data
public class ToggleLikeNoteRequestVO {
    private String userId;
    private Long noteId;
}
