package com.unswit.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unswit.usercenter.common.ErrorCode;
import com.unswit.usercenter.dto.CourseNoteDTO;
import com.unswit.usercenter.dto.NoteRequestDTO;
import com.unswit.usercenter.exception.BusinessException;
import com.unswit.usercenter.mapper.CourseMapper;
import com.unswit.usercenter.mapper.UserMapper;
import com.unswit.usercenter.model.domain.Course;
import com.unswit.usercenter.model.domain.Note;
import com.unswit.usercenter.model.domain.User;
import com.unswit.usercenter.service.CourseService;
import com.unswit.usercenter.service.NoteService;
import com.unswit.usercenter.mapper.NoteMapper;
import com.unswit.usercenter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
* @author zhiyao
* @description 针对表【note(笔记)】的数据库操作Service实现
* @createDate 2025-05-30 14:13:42
*/
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note>
    implements NoteService{

    @Resource
    private UserService userService;

    @Resource
    private NoteMapper noteMapper;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private CourseService courseService;
    @Autowired
    private UserMapper userMapper;


    @Override
    public List<CourseNoteDTO> addNote(NoteRequestDTO noteDTO, String userId) {

        if (noteDTO == null) {
            // 这里你可以选择抛异常，也可以返回一个空列表
            System.out.println("note为空");
            return Collections.emptyList();
        }
        // 设置note.
        //   title; // 笔记名称
        //   author; // 笔记作者名称
        //   code; // 课程代码
        //   lecturer;  // leaturer名字
        //   link;  // 笔记链接
        //   useId
        Note note = new Note();
        // 这里用的是Spring的BeanUtils
        BeanUtils.copyProperties(noteDTO, note);
        note.setUserId(userId);

        User user = userService.getById(userId);
        // 设置note.author
        if(note.getAuthor() == null){
            note.setAuthor(user.getUserName());
        }
        // 设置note.enrollTime
        String enrollTime = noteDTO.getEnrollYear()+noteDTO.getEnrollTerm();
        note.setEnrollTime(enrollTime);
        // 设置note.CourseId
        // 根据 code 查 courseId，如果不存在就抛异常
        Long courseId = Optional.ofNullable(
                        courseMapper.selectOne(
                                Wrappers.<Course>lambdaQuery()
                                        .select(Course::getId)
                                        .eq(Course::getCode, noteDTO.getCode())
                        )
                )
                .map(Course::getId)
                .orElseThrow(() -> new BusinessException(ErrorCode.SYSTEM_ERROR, ("课程不存在: " + noteDTO.getCode())));
        note.setCourseId(courseId);
        // 设置note.isOfficial和isChecked(官方默认被检查)

        int userRole = user.getUserRole();
        // 0管理员，1会员，2非会员
        if (userRole==0){
            // 0未审核 1审核通过
            note.setIsChecked(1);
            // 0非官方 1官方
            note.setIsOfficial(1);
        }
        try{
            save(note);
        }catch (BusinessException e){
            return Collections.emptyList();
        }
        System.out.println("保存note成功");
        return courseService.getAllCourseNote(userId);
    }

    @Transactional
    @Override
    public boolean auditNoteAndUpdateUser(long id) {
        // 1. 先加载这条笔记，确认它当前是否未审核
        Note note = this.getById(id);
        if (note == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "该笔记不存在");
        }
        // 2.更新笔记为已经审核状态
        note.setIsChecked(1);
        boolean noteUpdated = this.updateById(note);
        if (!noteUpdated) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"笔记审核失败");
        }
        // 3. 审核通过后，同步升级该笔记所属用户的 Role
        String userId = note.getUserId();
        // 先查询用户当前状态，如果不是会员再更新
        User user = userService.getById(userId);
        if (user != null && (user.getUserRole() == 2)) {
            User updateUser = new User();
            updateUser.setId(userId);
            updateUser.setUserRole(1);
            userService.updateById(updateUser);
        }
        // 如果用户已经是会员，就不重复执行 update 语句

        return true;

    }
}




