package com.unswit.usercenter.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryCourseDTO {
    private String categoryTitle;
    private String toolTip;
    private String subTitle;
    private List<CourseNoteDTO> courseNotes;
}
