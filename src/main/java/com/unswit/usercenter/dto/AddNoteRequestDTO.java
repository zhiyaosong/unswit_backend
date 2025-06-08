package com.unswit.usercenter.dto;

import lombok.Data;

@Data
public class AddNoteRequestDTO {
    NoteRequestDTO note;
    private Long userId; // 用户ID
}
