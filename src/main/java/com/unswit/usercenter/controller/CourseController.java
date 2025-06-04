package com.unswit.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.unswit.usercenter.common.BaseResponse;
import com.unswit.usercenter.common.ErrorCode;
import com.unswit.usercenter.common.ResultUtils;
import com.unswit.usercenter.dto.CourseNoteDTO;
import com.unswit.usercenter.model.domain.Course;
import com.unswit.usercenter.model.domain.Note;
import com.unswit.usercenter.service.CourseService;
import com.unswit.usercenter.service.NoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    private CourseService courseService;
    /**
     * 查询所有课程，并以JSON数组形式返回
     * 尾部拼接每个课程对应的note列表
     */
    @GetMapping()
    public BaseResponse<List<CourseNoteDTO>> getAllCourseNote() {
        List<CourseNoteDTO> allCourseNote = courseService.getAllCourseNote();
        if (allCourseNote.isEmpty()) {
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(allCourseNote);
    }
}
