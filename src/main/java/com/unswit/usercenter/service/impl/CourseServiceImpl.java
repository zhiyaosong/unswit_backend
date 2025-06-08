package com.unswit.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unswit.usercenter.dto.CourseNoteDTO;
import com.unswit.usercenter.mapper.CourseMapper;
import com.unswit.usercenter.mapper.NoteMapper;
import com.unswit.usercenter.mapper.UserMapper;
import com.unswit.usercenter.model.domain.Course;
import com.unswit.usercenter.model.domain.Note;
import com.unswit.usercenter.model.domain.User;
import com.unswit.usercenter.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author zhiyao
* @description 针对表【course(课程)】的数据库操作Service实现
* @createDate 2025-05-30 14:13:15
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService{

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private NoteMapper noteMapper;

    @Override
    public List<CourseNoteDTO> getAllCourseNote(Long userId) {
        // 查询所有课程
        QueryWrapper<Course> courseWrapper = new QueryWrapper<>();
        List<Course> courses = courseMapper.selectList(courseWrapper);
        User user = userMapper.selectById(userId);
        Integer userRole = user.getUserRole();
        //遍历每个课程 查询它的所有笔记
        List<CourseNoteDTO> courseNoteDTOS = new ArrayList<>(courses.size());
        for(Course course : courses) {
            Long courseId = course.getId();
            QueryWrapper<Note> notesWrapper = new QueryWrapper<Note>()
                    .eq("courseId", courseId)
                    .eq("isDelete", 0);
            if (userRole == 2) {
                // 2非会员只返回官方笔记（isOfficial:1）
                notesWrapper.eq("isOfficial", 1);
            }
            //按照course id查询笔记
            List<Note> notes = noteMapper.selectList(notesWrapper);
            CourseNoteDTO courseNoteDTO = new CourseNoteDTO();
            // set每一个课程DTO
            courseNoteDTO.setCourseId(courseId);
            courseNoteDTO.setCategory(course.getCategory());
            courseNoteDTO.setTitle(course.getTitle());
            courseNoteDTO.setNoteList(notes);
            courseNoteDTO.setCode(course.getCode());
            courseNoteDTO.setToolTip(course.getToolTip());
            courseNoteDTOS.add(courseNoteDTO);
        }
        return courseNoteDTOS;
    }
}




