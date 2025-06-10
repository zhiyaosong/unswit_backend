package com.unswit.usercenter.dto.request;

import lombok.Data;

@Data
public class AddNoteRequestDTO {
    NoteRequestDTO note;
    private String userId; // 用户ID
}
