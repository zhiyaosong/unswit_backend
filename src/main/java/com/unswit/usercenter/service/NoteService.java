package com.unswit.usercenter.service;

import com.unswit.usercenter.common.ErrorCode;
import com.unswit.usercenter.model.domain.Note;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zhiyao
* @description 针对表【note(笔记)】的数据库操作Service
* @createDate 2025-05-30 14:13:42
*/
public interface NoteService extends IService<Note> {

    ErrorCode addNote(Note note);

    boolean auditNoteAndUpdateUser(long id);
}
