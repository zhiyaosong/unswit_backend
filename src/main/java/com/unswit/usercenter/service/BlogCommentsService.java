package com.unswit.usercenter.service;

import com.unswit.usercenter.dto.blog.CommentDTO;
import com.unswit.usercenter.dto.blog.response.BlogComResponseVO;
import com.unswit.usercenter.model.domain.BlogComments;
import com.baomidou.mybatisplus.extension.service.IService;
import com.unswit.usercenter.model.domain.User;
import com.unswit.usercenter.utils.responseUtils.BaseResponse;

import java.util.List;
import java.util.Map;

/**
* @author zhiyao
* @description 针对表【blog_comments(blog comments)】的数据库操作Service
* @createDate 2025-06-12 12:02:11
*/
public interface BlogCommentsService extends IService<BlogComments> {

    BlogComResponseVO getBlogComments(Long blogId);

    CommentDTO toDto(BlogComments bc, Map<String, User> userMap);
}
