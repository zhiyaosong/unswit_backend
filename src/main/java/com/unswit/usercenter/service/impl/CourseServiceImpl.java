package com.unswit.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unswit.usercenter.dto.CategoryCourseDTO;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    String[][] metas = {
            {"IT",    "信息技术",   "IT 课程"},
            {"AI",    "人工智能",   "AI 课程"},
            {"DS",    "数据科学",   "DS 课程"},
            {"CB",    "计算机基础", "CB 课程"},
            {"EE",    "电子工程",   "EE 课程"},
            {"other","其他",      "其他课程"}
    };

    // 2. 初始化 grouped
    private Map<String, CategoryCourseDTO> initGrouped(){
        Map<String, CategoryCourseDTO> grouped = new LinkedHashMap<>();
        for (String[] m : metas) {
            String key      = m[0];
            String toolTip  = m[1];
            String subTitle = m[2];
            grouped.put(key, CategoryCourseDTO.builder()
                    .categoryTitle(key)
                    .toolTip(toolTip)
                    .subTitle(subTitle)
                    .courseNotes(new ArrayList<>())
                    .build());
        }
        return grouped;
    }

    @Override
    public Map<String, CategoryCourseDTO> getAllCourseNote(String userId) {
        // 查询所有课程
        QueryWrapper<Course> courseWrapper = new QueryWrapper<>();
        List<Course> courses = courseMapper.selectList(courseWrapper);
        User user = userMapper.selectById(userId);
        Integer userRole = user.getUserRole();

        Map<String, CategoryCourseDTO> grouped = initGrouped();

        //遍历每个课程 查询它的所有笔记
        List<CourseNoteDTO> courseNoteDTOS = new ArrayList<>(courses.size());
        for(Course course : courses) {
            Long courseId = course.getId();
            QueryWrapper<Note> notesWrapper = new QueryWrapper<Note>()
                    .eq("courseId", courseId)
                    .eq("isDelete", 0)
                    .eq("isChecked", 1);
            //按照course id查询笔记
            List<Note> notes = noteMapper.selectList(notesWrapper);
            // 对于非会员，非官方笔记返回空链接
            if (userRole==2){
                for (Note note : notes) {
                    if (note.getIsOfficial()==0){
                        note.setLink("nullLink");
                    }
                }
            }
            CourseNoteDTO courseNoteDTO = new CourseNoteDTO();
            // set每一个课程DTO
            courseNoteDTO.setCourseId(courseId);
            courseNoteDTO.setCategory(course.getCategory());
            courseNoteDTO.setTitle(course.getTitle());
            courseNoteDTO.setNoteList(notes);
            courseNoteDTO.setCode(course.getCode());
            courseNoteDTO.setToolTip(course.getToolTip());
            String key;
            switch (courseNoteDTO.getCategory()) {
                case 0: key = "IT";    break;
                case 1: key = "AI";    break;
                case 2: key = "DS";    break;
                case 3: key = "CB";    break;
                case 4: key = "EE";    break;
                default: key = "other";
            }
            // 把 dto 加入对应分组
            grouped.get(key).getCourseNotes().add(courseNoteDTO);
        }
        return grouped;
    }
}




