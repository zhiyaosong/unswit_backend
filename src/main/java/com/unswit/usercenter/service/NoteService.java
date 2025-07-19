package com.unswit.usercenter.service;

import com.unswit.usercenter.dto.note.request.NoteRequestVO;
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

    String addNote(NoteRequestVO note, String userId);

//    List<Note> getAllNotes(User user);

    boolean auditNoteAndUpdateUser(long id);

    Map<Long, Integer> getLikeCounts(List<Long> noteIds);

    Map<Long, Boolean> getUserLikedStatus(String userId, List<Long> noteIds);

    /**
     * 删除指定用户的笔记
     * @param noteId 帖子 ID
     * @param userId 当前登录用户 ID
     * @return 被删除的记录数（0 表示删除失败或无权限）
     */
    int deleteNote(Long noteId, String userId);
}
