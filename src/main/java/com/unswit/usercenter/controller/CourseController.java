package com.unswit.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.unswit.usercenter.common.BaseResponse;
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
    @Resource
    private NoteService noteService;
    /**
     * 查询所有课程，并以JSON数组形式返回
     * 尾部拼接每个课程对应的note列表
     * @param
     * @return
     */
    @GetMapping()
    public BaseResponse<List<CourseNoteDTO>> getAllCourseNote() {
        // 查询所有课程
        List<Course> courses = courseService.list();
        //遍历每个课程 查询它的所有笔记
        List<CourseNoteDTO> courseNoteDTOS = new ArrayList<>(courses.size());
        for(Course course : courses) {
            Long courseId = course.getId();
            //按照courseid查询笔记
            List<Note> notes = noteService.list(
                    new QueryWrapper<Note>().eq("courseId", courseId)
            );
            CourseNoteDTO courseNoteDTO = new CourseNoteDTO();
            courseNoteDTO.setCourseId(courseId);
            courseNoteDTO.setCategory(course.getCategory());
            courseNoteDTO.setTitle(course.getTitle());
            courseNoteDTO.setNoteList(notes);
            courseNoteDTO.setCode(course.getCode());
            courseNoteDTO.setRunTime(course.getRunTime());
            courseNoteDTO.setToolTip(course.getToolTip());
            courseNoteDTOS.add(courseNoteDTO);
        }
        return ResultUtils.success(courseNoteDTOS);
    }
}
