package com.unswit.usercenter.dto;

import com.unswit.usercenter.model.domain.Note;
import lombok.Data;

import java.util.List;
@Data
public class CourseNoteDTO {
    private Long courseId;

    private String code;

    private String title;

    private Integer category;

    private String toolTip;

    private List<Note> noteList;


}
