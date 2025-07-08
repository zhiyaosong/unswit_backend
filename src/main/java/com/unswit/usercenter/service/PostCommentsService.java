package com.unswit.usercenter.service;

import com.unswit.usercenter.dto.post.CommentDTO;
import com.unswit.usercenter.dto.post.response.PostComResponseVO;
import com.unswit.usercenter.model.domain.PostComments;
import com.baomidou.mybatisplus.extension.service.IService;
import com.unswit.usercenter.model.domain.User;

import java.util.Map;

/**
* @author zhiyao
* @description 针对表【blog_comments(post comments)】的数据库操作Service
* @createDate 2025-06-12 12:02:11
*/
public interface PostCommentsService extends IService<PostComments> {

    PostComResponseVO getPostComments(Long postId);

    CommentDTO toDto(PostComments bc, Map<String, User> userMap);
}
