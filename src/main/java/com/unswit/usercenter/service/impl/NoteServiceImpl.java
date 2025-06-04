package com.unswit.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unswit.usercenter.common.ErrorCode;
import com.unswit.usercenter.exception.BusinessException;
import com.unswit.usercenter.model.domain.Note;
import com.unswit.usercenter.model.domain.User;
import com.unswit.usercenter.service.NoteService;
import com.unswit.usercenter.mapper.NoteMapper;
import com.unswit.usercenter.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
* @author zhiyao
* @description 针对表【note(笔记)】的数据库操作Service实现
* @createDate 2025-05-30 14:13:42
*/
@Transactional
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note>
    implements NoteService{

    @Resource
    private UserService userService;

    @Resource
    private NoteMapper noteMapper;

    @Override
    public List<Note> getAllNotes(User user) {
        //取出用户的会员状态
        Integer userRole = user.getUserRole();
        List<Note> notes;
        QueryWrapper<Note> wrapper = new QueryWrapper<>();
        if (userRole == 2) {
            // 非会员：只查看官方笔记（isOfficial = 0)
            wrapper.eq("isOfficial", 0);
            notes = noteMapper.selectList(wrapper);
        } else {
            // 会员：查看全部笔记
            notes = noteMapper.selectList(wrapper);
        }
        return notes;
    }

    @Override
    public List<Note> addNote(Note note, User user) {
        if (note == null || user == null) {
            // 这里你可以选择抛异常，也可以返回一个空列表
            return Collections.emptyList();
        }
        note.setUserId(user.getId());
        save(note);
        return getAllNotes(user);
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
        Long userId = note.getUserId();
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




