package com.unswit.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unswit.usercenter.dto.request.ToggleLikeRequestDTO;
import com.unswit.usercenter.dto.response.ToggleLikeResponseDTO;
import com.unswit.usercenter.mapper.NoteMapper;
import com.unswit.usercenter.model.domain.UserNoteLikes;
import com.unswit.usercenter.service.UserNoteLikesService;
import com.unswit.usercenter.mapper.UserNoteLikesMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;

/**
* @author zy183
* @description 针对表【user_note_likes(用户笔记点赞表)】的数据库操作Service实现
* @createDate 2025-06-10 16:15:33
*/
@Service
public class UserNoteLikesServiceImpl extends ServiceImpl<UserNoteLikesMapper, UserNoteLikes>
    implements UserNoteLikesService{

    @Resource
    private UserNoteLikesMapper userNoteLikesMapper;

    @Resource
    private NoteMapper noteMapper;

    @Override
    @Transactional
    public ToggleLikeResponseDTO toggleLike(ToggleLikeRequestDTO req){
        String userId = req.getUserId();
        Long noteId = req.getNoteId();

        // 1. 检查是否已存在点赞记录
        UserNoteLikes key = new UserNoteLikes();
        key.setUserId(userId);
        key.setNoteId(noteId);

        UserNoteLikes existing = userNoteLikesMapper.selectById(key);
        boolean nowLiked;

        if (existing != null) {
            // 已点赞 -> 删除记录（触发器会自动减 likeCount）
            userNoteLikesMapper.deleteById(key);
            nowLiked = false;
        } else {
            // 未点赞 -> 新增记录（触发器会自动加 likeCount）
            userNoteLikesMapper.insert(key);
            nowLiked = true;
        }

        // 2. 读取当前 note.likeCount
        int currentCount = noteMapper.selectById(noteId).getLikeCount();

        // 3. 构造返回 DTO
        ToggleLikeResponseDTO resp = new ToggleLikeResponseDTO();
        resp.setLiked(nowLiked);
        resp.setLikes(currentCount);
        return resp;
    }
}




