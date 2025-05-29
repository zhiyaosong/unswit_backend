package com.unswit.usercenter.controller;

import com.unswit.usercenter.common.BaseResponse;
import com.unswit.usercenter.common.ErrorCode;
import com.unswit.usercenter.common.ResultUtils;
import com.unswit.usercenter.model.domain.Notes;
import com.unswit.usercenter.service.NotesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {
    @Resource
    private NotesService notesService;

    /**
     * 查询所有笔记，并以JSON数组形式返回
     * @param
     * @return
     */
    @GetMapping()
    public BaseResponse<List<Notes>> getAllNotes() {
        List<Notes> notes = notesService.list();
        if(notes == null){
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(notes);
    }
}
