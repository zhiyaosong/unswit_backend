package com.unswit.usercenter.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unswit.usercenter.dto.post.PostDetailsDTO;
import com.unswit.usercenter.dto.post.CommentDTO;
import com.unswit.usercenter.dto.post.response.PostComResponseVO;
import com.unswit.usercenter.model.domain.Post;
import com.unswit.usercenter.model.domain.PostComments;
import com.unswit.usercenter.model.domain.User;
import com.unswit.usercenter.service.PostCommentsService;
import com.unswit.usercenter.mapper.PostCommentsMapper;
import com.unswit.usercenter.service.PostService;
import com.unswit.usercenter.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
* @author zhiyao
* @description 针对表【post_comments(post comments)】的数据库操作Service实现
* @createDate 2025-06-12 12:02:11
*/
@Service
public class PostCommentsServiceImpl extends ServiceImpl<PostCommentsMapper, PostComments>
    implements PostCommentsService {

    @Resource
    private PostService postService;                // 用来拿 post 实体

    @Resource
    private UserService userService;                // 用来批量查 User 实体

    /**
     * 获取单个 post 及其评论（仅两级）
     */
    public PostComResponseVO getPostComments(Long postId) {
        // 1. 查询并转换 post -> postDTO
        Post postEntity = postService.getById(postId);
        PostDetailsDTO postDto = BeanUtil.copyProperties(postEntity, PostDetailsDTO.class);

        if (postEntity.getImages() != null && !postEntity.getImages().trim().isEmpty()) {
            List<String> images = Arrays.stream(postEntity.getImages().split(" "))
                    .map(String::trim)
                    .collect(Collectors.toList());
            postDto.setImages(images);
        } else {
            postDto.setImages(Collections.emptyList());
        }

        // 2. 查询一级评论（parentId = 0）
        List<PostComments> topLevel = this.list(
                new LambdaQueryWrapper<PostComments>()
                        .eq(PostComments::getPostId, postId)
                        .isNull(PostComments::getParentId)
//                        .orderByDesc(postComments::getCreateTime)
        );

        // 3. 查询二级评论
        List<Long> parentIds = topLevel.stream()
                .map(PostComments::getId)
                .collect(Collectors.toList());

        List<PostComments> replies = parentIds.isEmpty()
                ? Collections.emptyList()
                : this.list(
                new LambdaQueryWrapper<PostComments>()
                        .in(PostComments::getParentId, parentIds)
                        .orderByAsc(PostComments::getCreateTime)
        );

        // 4. 批量获取所有评论的用户信息
        Set<String> userIds = Stream.concat(
                        topLevel.stream().map(PostComments::getUserId),
                        replies.stream().map(PostComments::getUserId)
                )
                .collect(Collectors.toSet());
        List<User> users = userIds.isEmpty()
                ? Collections.emptyList()
                : userService.listByIds(userIds);
        Map<String, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        // 5. 将二级评论按父 ID 分组
        Map<Long, List<PostComments>> replyMap = replies.stream()
                .collect(Collectors.groupingBy(PostComments::getParentId));

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
        PostComResponseVO result = new PostComResponseVO();
        result.setPost(postDto);
        result.setComments(commentDtos);
        return result;
    }

    /**
     * 通用的 postComments -> CommentDTO 转换方法
     */
    public CommentDTO toDto(PostComments bc, Map<String, User> userMap) {
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




