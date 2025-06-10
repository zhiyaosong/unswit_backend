package com.unswit.usercenter.controller;


import com.unswit.usercenter.common.BaseResponse;
import com.unswit.usercenter.common.ErrorCode;
import com.unswit.usercenter.common.ResultUtils;
import com.unswit.usercenter.dto.CategoryCourseDTO;

import com.unswit.usercenter.dto.request.UserIdRequest;
import com.unswit.usercenter.service.CourseService;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Map;

@RestController
@RequestMapping("/course")
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
