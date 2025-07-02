package com.unswit.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.unswit.usercenter.dto.note.CategoryCourseDTO;
import com.unswit.usercenter.utils.responseUtils.BaseResponse;
import com.unswit.usercenter.utils.responseUtils.ErrorCode;
import com.unswit.usercenter.utils.responseUtils.ResultUtils;
import com.unswit.usercenter.dto.note.request.AddNoteRequestVO;
import com.unswit.usercenter.dto.note.request.NoteLikeRequestVO;
import com.unswit.usercenter.dto.note.request.NoteRequestVO;
import com.unswit.usercenter.dto.note.request.ToggleLikeRequestVO;
import com.unswit.usercenter.dto.note.response.NoteLikeResponseVO;
import com.unswit.usercenter.dto.note.ToggleLikeResponseDTO;
import com.unswit.usercenter.exception.BusinessException;
import com.unswit.usercenter.mapper.NoteMapper;
import com.unswit.usercenter.model.domain.Note;
import com.unswit.usercenter.service.CourseService;
import com.unswit.usercenter.service.NoteService;
import com.unswit.usercenter.service.UserNoteLikesService;
import com.unswit.usercenter.utils.UserHolder;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Tag(name = "note接口", description = "notes增删改接口（查在course接口里）")
@RestController
@RequestMapping("/note")
@CrossOrigin(origins = {"http://localhost:8000","http://124.220.105.199"},methods = {RequestMethod.POST,RequestMethod.GET}, allowCredentials = "true")
public class NoteController {
    @Resource
    private NoteService noteService;

    @Resource
    private CourseService courseService;

    @Resource
    private UserNoteLikesService userNoteLikesService;

    @Autowired
    private NoteMapper noteMapper;


    /**
     * 添加笔记接口
     * @param req
     * @return
     */
    @PostMapping("add")
    public BaseResponse<Map<String, CategoryCourseDTO>> addNote(@RequestBody AddNoteRequestVO req) {
        NoteRequestVO note = req.getNote();

        String userId = req.getUserId();

        Map<String, CategoryCourseDTO> notes = noteService.addNote(note, userId);
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
    @DeleteMapping("delete/{id}")
    public  Map<String, CategoryCourseDTO> deleteNote(@PathVariable long id) {
        String userId = UserHolder.getUser().getId();
        int rows = noteMapper.deleteById(id);
        if (rows == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return courseService.getAllCourseNote(userId);
    }

    /**
     * Map<String, Object> updates 是更新的内容
     * @param id
     * @param updates
     * @return List<CourseNoteDTO>
     */
    @PatchMapping("/update/{id}")
    public Map<String, CategoryCourseDTO> patchNote(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        String userId = UserHolder.getUser().getId();

        UpdateWrapper<Note> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id)
                .eq("user_id", userId);

        updates.forEach((field, value) -> {
            // 更新除了 id/user_id 的updates的字段
            if (!"id".equals(field) && !"user_id".equals(field)) {
                wrapper.set(field, value);
            }
        });
        int rows = noteMapper.update(null, wrapper);
        if (rows == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return courseService.getAllCourseNote(userId);
    }

    /**
     * BaseResponse<String> likeNote 获取笔记点赞信息
     * @param req
     * @return NoteLikesResponseDTO
     */
    @PostMapping("likes")
    public BaseResponse<NoteLikeResponseVO> getLikes(@RequestBody NoteLikeRequestVO req) {

        String userId = req.getUserId();
        List<Long> noteIds = req.getNoteIds();
        if (userId == null || noteIds == null || noteIds.isEmpty()) {
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        // 1. 查询总点赞数
        Map<Long, Integer> likes = noteService.getLikeCounts(noteIds);
        // 2. 查询当前用户是否已点赞
        Map<Long, Boolean> likedByUser = noteService.getUserLikedStatus(userId, noteIds);

        // 3. 组装返回
        NoteLikeResponseVO resp = new NoteLikeResponseVO();
        resp.setLikes(likes);
        resp.setLikedByUser(likedByUser);

        return ResultUtils.success(resp);
    }

    /**
     * 切换点赞（如果之前已点赞则取消，否则就点赞）。
     * POST /api/note/like?userId=xxx
     * Body: { "noteId": 123 }
     */
    @PostMapping("/like")
    public BaseResponse<ToggleLikeResponseDTO> toggleLike(
            @RequestBody ToggleLikeRequestVO req
    ) {
        ToggleLikeResponseDTO resp = userNoteLikesService.toggleLike(req);
        return ResultUtils.success(resp);
    }
}

