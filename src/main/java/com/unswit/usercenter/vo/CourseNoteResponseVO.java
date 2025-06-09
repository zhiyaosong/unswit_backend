package com.unswit.usercenter.vo;

import com.unswit.usercenter.dto.CategoryCourseDTO;
import lombok.Data;

import java.util.List;

@Data
public class CourseNoteResponseVO {
    private List<CategoryCourseDTO> categoryList;
}
