package com.unswit.usercenter.service;

import com.unswit.usercenter.dto.CategoryCourseDTO;
import com.unswit.usercenter.model.domain.Course;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author zhiyao
* @description 针对表【course(课程)】的数据库操作Service
* @createDate 2025-05-30 14:13:15
*/
public interface CourseService extends IService<Course> {


    Map<String, CategoryCourseDTO> getAllCourseNote(String userId);
}
