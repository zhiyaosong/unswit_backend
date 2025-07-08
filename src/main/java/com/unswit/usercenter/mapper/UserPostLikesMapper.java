package com.unswit.usercenter.mapper;

import com.unswit.usercenter.model.domain.UserPostLikes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author zy183
* @description 针对表【user_post_likes(用户帖子点赞表)】的数据库操作Mapper
* @createDate 2025-07-08 16:18:38
* @Entity com.unswit.usercenter.model.domain.UserPostLikes
*/
public interface UserPostLikesMapper extends BaseMapper<UserPostLikes> {
    UserPostLikes selectById(UserPostLikes key);
    int insert(UserPostLikes entity);
    int deleteById(UserPostLikes key);
}




