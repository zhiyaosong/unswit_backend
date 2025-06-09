package com.unswit.usercenter.service;

import com.unswit.usercenter.common.BaseResponse;
import com.unswit.usercenter.common.ErrorCode;
import com.unswit.usercenter.dto.CourseNoteDTO;
import com.unswit.usercenter.dto.NoteRequestDTO;
import com.unswit.usercenter.model.domain.Note;
import com.baomidou.mybatisplus.extension.service.IService;
import com.unswit.usercenter.model.domain.User;

import java.util.List;


/**
* @author zhiyao
* @description 针对表【note(笔记)】的数据库操作Service
* @createDate 2025-05-30 14:13:42
*/
public interface NoteService extends IService<Note> {

    List<CourseNoteDTO> addNote(NoteRequestDTO note, String userId);

//    List<Note> getAllNotes(User user);

    boolean auditNoteAndUpdateUser(long id);
}
