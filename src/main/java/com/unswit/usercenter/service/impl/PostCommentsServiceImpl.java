package com.unswit.usercenter.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unswit.usercenter.dto.blog.BlogDetailsDTO;
import com.unswit.usercenter.dto.blog.CommentDTO;
import com.unswit.usercenter.dto.blog.response.BlogComResponseVO;
import com.unswit.usercenter.model.domain.Blog;
import com.unswit.usercenter.model.domain.BlogComments;
import com.unswit.usercenter.model.domain.User;
import com.unswit.usercenter.service.BlogCommentsService;
import com.unswit.usercenter.mapper.BlogCommentsMapper;
import com.unswit.usercenter.service.BlogService;
import com.unswit.usercenter.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
* @author zhiyao
* @description 针对表【blog_comments(blog comments)】的数据库操作Service实现
* @createDate 2025-06-12 12:02:11
*/
@Service
public class BlogCommentsServiceImpl extends ServiceImpl<BlogCommentsMapper, BlogComments>
    implements BlogCommentsService{

    @Resource
    private BlogService blogService;                // 用来拿 Blog 实体

    @Resource
    private UserService userService;                // 用来批量查 User 实体

    /**
     * 获取单个 blog 及其评论（仅两级）
     */
    public BlogComResponseVO getBlogComments(Long blogId) {
        // 1. 查询并转换 Blog -> BlogDTO
        Blog blogEntity = blogService.getById(blogId);
        BlogDetailsDTO blogDto = BeanUtil.copyProperties(blogEntity, BlogDetailsDTO.class);

        if (blogEntity.getImages() != null && !blogEntity.getImages().trim().isEmpty()) {
            List<String> images = Arrays.stream(blogEntity.getImages().split(" "))
                    .map(String::trim)
                    .collect(Collectors.toList());
            blogDto.setImages(images);
        } else {
            blogDto.setImages(Collections.emptyList());
        }

        // 2. 查询一级评论（parentId = 0）
        List<BlogComments> topLevel = this.list(
                new LambdaQueryWrapper<BlogComments>()
                        .eq(BlogComments::getBlogId, blogId)
                        .isNull(BlogComments::getParentId)
//                        .orderByDesc(BlogComments::getCreateTime)
        );

        // 3. 查询二级评论
        List<Long> parentIds = topLevel.stream()
                .map(BlogComments::getId)
                .collect(Collectors.toList());

        List<BlogComments> replies = parentIds.isEmpty()
                ? Collections.emptyList()
                : this.list(
                new LambdaQueryWrapper<BlogComments>()
                        .in(BlogComments::getParentId, parentIds)
                        .orderByAsc(BlogComments::getCreateTime)
        );

        // 4. 批量获取所有评论的用户信息
        Set<String> userIds = Stream.concat(
                        topLevel.stream().map(BlogComments::getUserId),
                        replies.stream().map(BlogComments::getUserId)
                )
                .collect(Collectors.toSet());
        List<User> users = userIds.isEmpty()
                ? Collections.emptyList()
                : userService.listByIds(userIds);
        Map<String, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        // 5. 将二级评论按父 ID 分组
        Map<Long, List<BlogComments>> replyMap = replies.stream()
                .collect(Collectors.groupingBy(BlogComments::getParentId));

        // 6. 把 Comment 转成 DTO 并嵌套
        List<CommentDTO> commentDtos = topLevel.stream().map(parent -> {
            CommentDTO parentDto = toDto(parent, userMap);

            List<CommentDTO> children = replyMap.getOrDefault(parent.getId(), Collections.emptyList())
                    .stream()
                    .map(child -> toDto(child, userMap))
                    .collect(Collectors.toList());
            parentDto.setChildren(children);

            return parentDto;
        }).collect(Collectors.toList());

        // 7. 组装最终返回值
        BlogComResponseVO result = new BlogComResponseVO();
        result.setBlog(blogDto);
        result.setComments(commentDtos);
        return result;
    }

    /**
     * 通用的 BlogComments -> CommentDTO 转换方法
     */
    public CommentDTO toDto(BlogComments bc, Map<String, User> userMap) {
        CommentDTO dto = BeanUtil.copyProperties(bc, CommentDTO.class);
        // BeanUtil 无法自动映射 userId -> authorId
        dto.setAuthorId(bc.getUserId());
        // 填充用户信息
        User u = userMap.get(bc.getUserId());
        if (u != null) {
            dto.setAuthorName(u.getUserName());
            dto.setAuthorAvatar(u.getAvatarUrl());
        }
        // 注意：BeanUtil 会把 createTime 也复制过去
        return dto;
    }
}




