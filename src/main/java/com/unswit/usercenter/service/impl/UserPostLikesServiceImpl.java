package com.unswit.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unswit.usercenter.dto.post.response.ToggleLikePostResponseVO;
import com.unswit.usercenter.dto.post.request.ToggleLikePostRequestVO;
import com.unswit.usercenter.dto.user.UserSimpleDTO;
import com.unswit.usercenter.mapper.PostMapper;
import com.unswit.usercenter.model.domain.UserPostLikes;
import com.unswit.usercenter.service.UserPostLikesService;
import com.unswit.usercenter.mapper.UserPostLikesMapper;
import com.unswit.usercenter.utils.UserHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author zy183
* @description 针对表【user_post_likes(用户帖子点赞表)】的数据库操作Service实现
* @createDate 2025-07-08 16:18:38
*/
@Service
public class UserPostLikesServiceImpl extends ServiceImpl<UserPostLikesMapper, UserPostLikes>
    implements UserPostLikesService{

    @Resource
    private UserPostLikesMapper userPostLikesMapper;

    @Resource
    private PostMapper postMapper;

    @Override
    public ToggleLikePostResponseVO toggleLike(ToggleLikePostRequestVO req) {
        UserSimpleDTO userSimpleDTO = UserHolder.getUser();
        String userId = userSimpleDTO.getId();
        Long postId = req.getPostId();

        // 1. 检查是否已存在点赞记录
        UserPostLikes key = new UserPostLikes();
        key.setUserId(userId);
        key.setPostId(postId);

        UserPostLikes existing = userPostLikesMapper.selectById(key);
        boolean nowLiked;

        if (existing != null) {
            // 已点赞 -> 删除记录（触发器会自动减 likeCount）
            userPostLikesMapper.deleteById(key);
            nowLiked = false;
        } else {
            // 未点赞 -> 新增记录（触发器会自动加 likeCount）
            userPostLikesMapper.insert(key);
            nowLiked = true;
        }

        // 2. 读取当前 post.likeCount
        int currentCount = postMapper.selectById(postId).getLikeCount();

        // 3. 构造返回 DTO
        ToggleLikePostResponseVO resp = new ToggleLikePostResponseVO();
        resp.setLiked(nowLiked);
        resp.setLikes(currentCount);
        return resp;
    }
}




