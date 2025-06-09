package com.unswit.usercenter.controller;

import com.unswit.usercenter.common.BaseResponse;
import com.unswit.usercenter.common.ErrorCode;
import com.unswit.usercenter.common.ResultUtils;
import com.unswit.usercenter.dto.CourseNoteDTO;
import com.unswit.usercenter.dto.AddNoteRequestDTO;
import com.unswit.usercenter.dto.NoteRequestDTO;
import com.unswit.usercenter.service.NoteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {
    @Resource
    private NoteService noteService;

    /**
     * 查询所有笔记，并以JSON数组形式返回
     */

    @PostMapping("add")
    public BaseResponse<List<CourseNoteDTO>> addNote(@RequestBody AddNoteRequestDTO req) {
        NoteRequestDTO note = req.getNote();

        String userId = req.getUserId();

        List<CourseNoteDTO> notes = noteService.addNote(note, userId);
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

}
