package com.unswit.usercenter.service;

import com.unswit.usercenter.dto.post.response.ToggleLikePostResponseVO;
import com.unswit.usercenter.dto.post.request.ToggleLikePostRequestVO;
import com.unswit.usercenter.model.domain.UserPostLikes;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zy183
* @description 针对表【user_post_likes(用户帖子点赞表)】的数据库操作Service
* @createDate 2025-07-08 16:18:38
*/
public interface UserPostLikesService extends IService<UserPostLikes> {
    ToggleLikePostResponseVO toggleLike(ToggleLikePostRequestVO req);
}
