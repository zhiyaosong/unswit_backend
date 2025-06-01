package com.unswit.usercenter.service.impl;

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
import java.math.BigInteger;

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
    @Override
    public ErrorCode addNote(Note note) {
        if(note!=null){
            save(note);
            return ErrorCode.SUCCESS;
        }
        return ErrorCode.NULL_ERROR;
    }
    @Transactional
    @Override
    public boolean auditNoteAndUpdateUser(long id) {
        // 1. 先加载这条笔记，确认它当前是否未审核
        Note note = this.getById(id);
        if (note == null) {
            throw new BusinessException("该笔记不存在");
        }
        if (note.getIsCheck() != null && note.getIsCheck() == 1) {
            throw new BusinessException("该笔记已审核，无需再次操作");
        }
        // 2.更新笔记为已经审核状态
        note.setIsCheck(1);
        boolean noteUpdated = this.updateById(note);
        if (!noteUpdated) {
            throw new BusinessException("笔记审核失败");
        }
        // 3. 审核通过后，同步升级该笔记所属用户的 isMembership
        Long userId = note.getUserId();
        // 先查询用户当前状态，如果不是会员再更新
        User user = userService.getById(userId);
        if (user != null && (user.getIsMember() == null || user.getIsMember() == 0)) {
            User updateUser = new User();
            updateUser.setId(userId);
            updateUser.setIsMember(1);
            userService.updateById(updateUser);
        }
        // 如果用户已经是会员，就不重复执行 update 语句

        return true;

    }
}




