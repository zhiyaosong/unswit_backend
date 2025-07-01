package com.unswit.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unswit.usercenter.dto.Result;
import com.unswit.usercenter.dto.request.BlogCommentRequestDTO;
import com.unswit.usercenter.model.domain.BlogComments;
import com.unswit.usercenter.model.domain.User;
import com.unswit.usercenter.service.BlogCommentsService;
import com.unswit.usercenter.utils.UserHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
public class BlogCommentsController {
    @Resource
    private BlogCommentsService blogCommentsService;
    /**
     * 发布评论接口
     * @param
     * @return
     */
    @PostMapping("/add")
    public Result addComment(@RequestBody BlogCommentRequestDTO dto) {
        String id = UserHolder.getUser().getId();
        BlogComments comment = new BlogComments();
        comment.setContent(dto.getContent());
        comment.setBlogId(dto.getBlogId());
        comment.setUserId(id);
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        comment.setParentId(dto.getParentId());
        comment.setStatus(0);

        blogCommentsService.save(comment);
        return Result.ok();
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
        BlogComments comment = blogCommentsService.getById(commentId);
        if (comment == null) {
            return Result.fail("评论不存在");
        }
        if (!comment.getUserId().equals(userId)) {
            return Result.fail("无权限删除该评论");
        }

        // 2. 删除评论（若是一级评论，会自动级联删除子评论，需数据库支持）
        blogCommentsService.removeById(commentId);
        return Result.ok("删除成功");
    }
}
