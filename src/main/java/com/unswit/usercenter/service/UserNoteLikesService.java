package com.unswit.usercenter.service;

import com.unswit.usercenter.dto.request.ToggleLikeRequestDTO;
import com.unswit.usercenter.dto.response.ToggleLikeResponseDTO;
import com.unswit.usercenter.model.domain.UserNoteLikes;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zy183
* @description 针对表【user_note_likes(用户笔记点赞表)】的数据库操作Service
* @createDate 2025-06-10 16:15:33
*/
public interface UserNoteLikesService extends IService<UserNoteLikes> {

    ToggleLikeResponseDTO toggleLike(ToggleLikeRequestDTO req);
}
