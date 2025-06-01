package com.unswit.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.unswit.usercenter.common.BaseResponse;
import com.unswit.usercenter.common.ErrorCode;
import com.unswit.usercenter.common.ResultUtils;
import com.unswit.usercenter.model.domain.Note;
import com.unswit.usercenter.model.domain.User;
import com.unswit.usercenter.service.NoteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    public BaseResponse<List<Note>> getAllNote(@RequestBody User user) {
        //拿到用户
        if(user == null) {
            return ResultUtils.error(ErrorCode.NOT_LOGIN);
        }
        //取出用户的会员状态
        Integer isMember = user.getIsMember();
        List<Note> notes;
        if (isMember != null && isMember == 0) {
            // 非会员：只查看官方笔记（isOfficial = 0）
            LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Note::getIsOfficial, 0);
            notes = noteService.list(wrapper);
        } else {
            // 会员：查看全部笔记
            notes = noteService.list();
        }
        // 3. 如果查询结果为空，返回空列表或错误
        if (notes == null || notes.isEmpty()) {
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        // 4. 返回结果
        return ResultUtils.success(notes);
    }
    @PostMapping("add")
    public ErrorCode addNote(@RequestBody Note note) {
        return noteService.addNote(note);
    }

    @PostMapping("audit/{id}")
    public BaseResponse<Boolean> auditUser(@PathVariable long id) {
        boolean success = noteService.auditNoteAndUpdateUser(id);
        if (success) {
            return ResultUtils.success("审核并同步用户会员成功".isEmpty());
        } else {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
    }

}
