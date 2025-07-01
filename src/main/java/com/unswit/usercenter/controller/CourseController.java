package com.unswit.usercenter.controller;


import com.unswit.usercenter.utils.responseUtils.BaseResponse;
import com.unswit.usercenter.utils.responseUtils.ErrorCode;
import com.unswit.usercenter.utils.responseUtils.ResultUtils;
import com.unswit.usercenter.dto.CategoryCourseDTO;

import com.unswit.usercenter.dto.request.UserIdRequest;
import com.unswit.usercenter.service.CourseService;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Map;

@Tag(name = "Course接口", description = "主要返回course-notes列表")
@RestController
@RequestMapping("/course")
@CrossOrigin(origins = {"http://localhost:8000","http://124.220.105.199"},methods = {RequestMethod.POST,RequestMethod.GET}, allowCredentials = "true")
public class CourseController {
    @Resource
    private CourseService courseService;
    /**
     * 查询所有课程，并以JSON数组形式返回
     * 尾部拼接每个课程对应的note列表
     */
    @PostMapping()
    public BaseResponse<Map<String, CategoryCourseDTO>> getAllCourseNote(@RequestBody UserIdRequest req) {
        String userId = req.getUserId();
        System.out.println(userId);
        Map<String, CategoryCourseDTO> allCourseNote = courseService.getAllCourseNote(userId);
        if (allCourseNote.isEmpty()) {
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(allCourseNote);
    }
}
