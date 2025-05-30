package com.unswit.usercenter.controller;

import com.unswit.usercenter.common.BaseResponse;
import com.unswit.usercenter.common.ErrorCode;
import com.unswit.usercenter.common.ResultUtils;
import com.unswit.usercenter.model.domain.Note;
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
    @GetMapping()
    public BaseResponse<List<Note>> getAllNote() {
        List<Note> note = noteService.list();
        if(note == null){
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(note);
    }
    @PostMapping("add")
    public ErrorCode addNote(@RequestBody Note note) {
        return noteService.addNote(note);
    }

}
