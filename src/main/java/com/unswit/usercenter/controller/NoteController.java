package com.unswit.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.unswit.usercenter.common.BaseResponse;
import com.unswit.usercenter.common.ErrorCode;
import com.unswit.usercenter.common.ResultUtils;
import com.unswit.usercenter.dto.CategoryCourseDTO;
import com.unswit.usercenter.dto.AddNoteRequestDTO;
import com.unswit.usercenter.dto.NoteRequestDTO;
import com.unswit.usercenter.exception.BusinessException;
import com.unswit.usercenter.mapper.NoteMapper;
import com.unswit.usercenter.model.domain.Note;
import com.unswit.usercenter.service.CourseService;
import com.unswit.usercenter.service.NoteService;
import com.unswit.usercenter.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/note")
public class NoteController {
    @Resource
    private NoteService noteService;

    @Resource
    private CourseService courseService;
    @Autowired
    private NoteMapper noteMapper;

    /**
     * 查询所有笔记，并以JSON数组形式返回
     */

    @PostMapping("add")
    public BaseResponse<Map<String, CategoryCourseDTO>> addNote(@RequestBody AddNoteRequestDTO req) {
        NoteRequestDTO note = req.getNote();

        String userId = req.getUserId();

        Map<String, CategoryCourseDTO> notes = noteService.addNote(note, userId);
        return ResultUtils.success(notes);
    }

    @GetMapping("audit/{id}")
    public BaseResponse<String> auditUser(@PathVariable long id) {
        boolean success = noteService.auditNoteAndUpdateUser(id);
        if (success) {
            return ResultUtils.success("审核并同步用户会员成功");
        } else {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
    }
    @DeleteMapping("delete/{id}")
    public  List<CourseNoteDTO> deleteNote(@PathVariable long id) {
        String userId = UserHolder.getUser().getId();
        int rows = noteMapper.deleteById(id);
        if (rows == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return courseService.getAllCourseNote(userId);
    }

    /**
     * Map<String, Object> updates 是更新的内容
     * @param id
     * @param updates
     * @return List<CourseNoteDTO>
     */
    @PatchMapping("/update/{id}")
    public List<CourseNoteDTO> patchNote(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        String userId = UserHolder.getUser().getId();

        UpdateWrapper<Note> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id)
                .eq("user_id", userId);

        updates.forEach((field, value) -> {
            // 更新除了 id/user_id 的updates的字段
            if (!"id".equals(field) && !"user_id".equals(field)) {
                wrapper.set(field, value);
            }
        });
        int rows = noteMapper.update(null, wrapper);
        if (rows == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return courseService.getAllCourseNote(userId);
    }
}
