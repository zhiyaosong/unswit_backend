package com.unswit.usercenter.controller;

import com.unswit.usercenter.dto.Result;
import com.unswit.usercenter.dto.post.request.PostCommentRequestVO;
import com.unswit.usercenter.model.domain.PostComments;
import com.unswit.usercenter.service.PostCommentsService;
import com.unswit.usercenter.utils.UserHolder;
import com.unswit.usercenter.utils.responseUtils.BaseResponse;
import com.unswit.usercenter.utils.responseUtils.ResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/comments")
public class PostCommentsController {
    @Resource
    private PostCommentsService postCommentsService;
    /**
     * 发布评论接口
     * @param
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<String> addComment(@RequestBody PostCommentRequestVO dto) {
        String id = UserHolder.getUser().getId();
        PostComments comment = new PostComments();
        comment.setContent(dto.getContent());
        comment.setPostId(dto.getPostId());
        comment.setUserId(id);
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        if (dto.getParentId() != null){
            comment.setParentId(dto.getParentId());
        }
        comment.setStatus(0);

        postCommentsService.save(comment);
        return ResultUtils.success("ok");
    }


    /**
     * 删除评论(级联删除)
     * @param commentId
     * @return
     */
    @DeleteMapping("delete/{commentId}")
    public Result deleteComment(@PathVariable Long commentId) {
        String userId = UserHolder.getUser().getId(); // 获取当前登录用户ID

        // 1. 查询评论是否存在且属于当前用户
        PostComments comment = postCommentsService.getById(commentId);
        if (comment == null) {
            return Result.fail("评论不存在");
        }
        if (!comment.getUserId().equals(userId)) {
            return Result.fail("无权限删除该评论");
        }

        // 2. 删除评论（若是一级评论，会自动级联删除子评论，需数据库支持）
        postCommentsService.removeById(commentId);
        return Result.ok("删除成功");
    }
}
