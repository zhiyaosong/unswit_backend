package com.unswit.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unswit.usercenter.model.domain.Course;
import com.unswit.usercenter.service.CourseService;
import com.unswit.usercenter.mapper.CourseMapper;
import org.springframework.stereotype.Service;

/**
* @author zhiyao
* @description 针对表【course(课程)】的数据库操作Service实现
* @createDate 2025-05-30 14:13:15
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService{

}




