package com.unswit.usercenter.dto.note.request;

import lombok.Data;

@Data
public class AddNoteRequestVO {
    NoteRequestVO note;
    private String userId; // 用户ID
}
