package com.unswit.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unswit.usercenter.dto.CommentDTO;
import com.unswit.usercenter.dto.Result;
import com.unswit.usercenter.dto.UserDTO;
import com.unswit.usercenter.dto.response.BlogWithCommentsDTO;
import com.unswit.usercenter.model.domain.Blog;
import com.unswit.usercenter.model.domain.BlogComments;
import com.unswit.usercenter.service.BlogCommentsService;
import com.unswit.usercenter.service.BlogService;
import com.unswit.usercenter.utils.SystemConstants;
import com.unswit.usercenter.utils.UserHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Resource
    private BlogService blogService;
    @Resource
    private BlogCommentsService blogCommentsService;

    /**
     * 新增blog
     * @param blog
     * @return
     */
    @PostMapping("/add")
    public Result saveBlog(@RequestBody Blog blog) {
        // 获取登录用户
        UserDTO user = UserHolder.getUser();
        blog.setUserId(user.getId());
        // 保存博文
        blogService.save(blog);
        // 返回id
        return Result.ok(blog.getId());
    }

    /**
     * 获取自己发布的blog
     * @param current
     * @return
     */
    @GetMapping("/of/me")
    public Result queryMyBlog(@RequestParam(value = "current", defaultValue = "1") Integer current) {
        // 获取登录用户
        UserDTO user = UserHolder.getUser();
        // 根据用户查询
        Page<Blog> page = blogService.query()
                .eq("user_id", user.getId()).page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 获取当前页数据
        List<Blog> records = page.getRecords();
        return Result.ok(records);
    }


    /**
     * 帖子点赞
     * @param id
     * @return
     */
    @PutMapping("/like/{id}")
    public Result likeBlog(@PathVariable("id") Long id) {
        return blogService.likeBlog(id);
    }


    /**
     * 展示某一篇blog详情界面
     * @param blogId
     * @return
     */
    @GetMapping("/{blogId}")
    public Result getBlogDetail(@PathVariable Long blogId) {
        Blog blog = blogService.getById(blogId);
        if (blog == null) return Result.fail("博客不存在");

        List<Long> blogIds = new ArrayList<>();
        blogIds.add(blogId);
        Map<Long, List<CommentDTO>> commentMap = buildBlogCommentMap(blogIds);

        BlogWithCommentsDTO dto = new BlogWithCommentsDTO();
        dto.setBlog(blog);
        dto.setComments(commentMap.getOrDefault(blogId, new ArrayList<>()));
        return Result.ok(dto);
    }

    /**
     * 展示某一页的所有blog+评论
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    public Result listBlogsWithComments(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "5") int size) {
        Page<Blog> blogPage = blogService.page(
                new Page<>(page, size),
                new LambdaQueryWrapper<Blog>()
                        .orderByDesc(Blog::getCreateTime)
        );

        List<Blog> blogs = blogPage.getRecords();
        if (blogs.isEmpty()) return Result.ok(new ArrayList<>());

        List<Long> blogIds = blogs.stream().map(Blog::getId).collect(Collectors.toList());
        Map<Long, List<CommentDTO>> commentMap = buildBlogCommentMap(blogIds);

        List<BlogWithCommentsDTO> result = blogs.stream().map(blog -> {
            BlogWithCommentsDTO dto = new BlogWithCommentsDTO();
            dto.setBlog(blog);
            dto.setComments(commentMap.getOrDefault(blog.getId(), new ArrayList<>()));
            return dto;
        }).collect(Collectors.toList());

        return Result.ok(result);
    }

    /**
     * 转化comment为commentDTO
     * @param comment
     * @return
     */
    private CommentDTO toDTO(BlogComments comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setUserId(comment.getUserId());
        dto.setContent(comment.getContent());
        dto.setCreateTime(comment.getCreateTime());
        dto.setBlogId(comment.getBlogId());
        if (comment.getChildren() != null && !comment.getChildren().isEmpty()) {
            dto.setChildren(comment.getChildren().stream().map(this::toDTO).collect(Collectors.toList()));
        }
        return dto;
    }

    /**
     * 工具类方法，用于构建 blogId -> 评论树结构
     * @param blogIds
     * @return
     */
    public Map<Long, List<CommentDTO>> buildBlogCommentMap(List<Long> blogIds) {
        // 1. 查询一级评论（parentId = 0）
        List<BlogComments> topLevel = blogCommentsService.list(
                new LambdaQueryWrapper<BlogComments>()
                        .in(BlogComments::getBlogId, blogIds)
                        .eq(BlogComments::getParentId, 0)
                        .orderByDesc(BlogComments::getCreateTime)
        );

        // 2. 查询对应的所有二级评论
        List<Long> parentIds = topLevel.stream()
                .map(BlogComments::getId)
                .collect(Collectors.toList());

        List<BlogComments> replies = parentIds.isEmpty()
                ? new ArrayList<>()
                : blogCommentsService.list(
                new LambdaQueryWrapper<BlogComments>()
                        .in(BlogComments::getParentId, parentIds)
                        .orderByAsc(BlogComments::getCreateTime)
        );

        // 3. 将二级评论按 parentId 分组
        Map<Long, List<BlogComments>> replyMap = replies.stream()
                .collect(Collectors.groupingBy(BlogComments::getParentId));

        // 4. 将二级评论嵌套进一级评论中，并转为 DTO，再按 blogId 分组
        return topLevel.stream().map(parent -> {
            parent.setChildren(replyMap.get(parent.getId()));
            return toDTO(parent);
        }).collect(Collectors.groupingBy(CommentDTO::getBlogId));
    }
}
