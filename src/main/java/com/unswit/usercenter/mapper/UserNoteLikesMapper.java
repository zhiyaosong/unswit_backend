package com.unswit.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.unswit.usercenter.model.domain.UserNoteLikes;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zy183
* @description 针对表【user_note_likes(用户笔记点赞表)】的数据库操作Mapper
* @createDate 2025-06-10 16:15:33
* @Entity com.unswit.usercenter.model.domain.UserNoteLikes
*/
@Mapper
public interface UserNoteLikesMapper extends BaseMapper<UserNoteLikes> {
    UserNoteLikes selectById(UserNoteLikes key);
    int insert(UserNoteLikes entity);
    int deleteById(UserNoteLikes key);
}




