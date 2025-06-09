package com.unswit.usercenter.service;

import com.unswit.usercenter.dto.CategoryCourseDTO;
import com.unswit.usercenter.dto.NoteRequestDTO;
import com.unswit.usercenter.model.domain.Note;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;


/**
* @author zhiyao
* @description 针对表【note(笔记)】的数据库操作Service
* @createDate 2025-05-30 14:13:42
*/
public interface NoteService extends IService<Note> {

    Map<String, CategoryCourseDTO> addNote(NoteRequestDTO note, String userId);

//    List<Note> getAllNotes(User user);

    boolean auditNoteAndUpdateUser(long id);
}
