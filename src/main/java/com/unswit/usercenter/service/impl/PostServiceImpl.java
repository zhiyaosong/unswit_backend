package com.unswit.usercenter.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unswit.usercenter.dto.post.response.PostListResponseVO;
import com.unswit.usercenter.dto.post.PostSummaryDTO;
import com.unswit.usercenter.mapper.UserPostLikesMapper;
import com.unswit.usercenter.mapper.UserMapper;

import com.unswit.usercenter.model.domain.Post;
import com.unswit.usercenter.model.domain.User;
import com.unswit.usercenter.model.domain.UserPostLikes;
import com.unswit.usercenter.service.PostService;
import com.unswit.usercenter.mapper.PostMapper;
import com.unswit.usercenter.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
* @author zhiyao
* @description 针对表【post(帖子)】的数据库操作Service实现
* @createDate 2025-06-12 12:02:11
*/
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserPostLikesMapper userPostLikesMapper;

    @Override
    public Long likePost(Long id) {
        //1.获取登陆用户
        String userId = UserHolder.getUser().getId();

        //2.判断当前登陆用户是否已经点赞
        String key = "post:liked"+id;
        Boolean isMember = stringRedisTemplate.opsForSet().isMember(key, userId);
        if(BooleanUtil.isFalse(isMember)){
            //4.未点赞的话点赞数+1
            //4.1 数据库点赞呢 +1
            boolean isSuccess = update().setSql("likeCount= likeCount+1").eq("id", id).update();
            //4.2 保存用户到redis的set集合
            if (isSuccess) {
                stringRedisTemplate.opsForSet().add(key, userId);
            }
        }else{
            //3.如果已经点赞，点赞数-1
            //3.1数据库点赞数-1
            boolean isSuccess = update().setSql("likeCount= likeCount-1").eq("id", id).update();
            //3.2 把用户从set集合中删除
            if (isSuccess) {
                stringRedisTemplate.opsForSet().remove(key, userId);
            }
        }

        return id;

    }

    @Override
    public PostListResponseVO getListPosts(int page, int size) {
        // 1. 分页查询 post 对象
        Page<Post> postPage = this.page(
                new Page<>(page, size),
                new LambdaQueryWrapper<Post>()
                        .orderByDesc(Post::getCreateTime)
        );
        List<Post> posts = postPage.getRecords();

        // 2. 转换为 DTO 列表
        List<PostSummaryDTO> listposts = posts.stream().map(post -> {
            String userId = post.getUserId();
            String userName = Optional.ofNullable(
                            userMapper.selectOne(
                                    new QueryWrapper<User>()
                                            .eq("id", userId)
                                            .select("userName")
                            )
                    ).map(User::getUserName)
                    .orElse("匿名用户");

            return getpostSummaryDTO(post, userName);
        }).collect(Collectors.toList());

        // 3. 封装返回
        PostListResponseVO response = new PostListResponseVO();
        response.setPostSumList(listposts);
        response.setTotal((int) postPage.getTotal());
        return response;
    }

    /** 获取每个笔记的总点赞数 */
    @Override
    public Map<Long, Integer> getLikeCounts(List<Long> postIds) {
        // MyBatis-Plus 内置的 selectBatchIds
        List<Post> posts = postMapper.selectBatchIds(postIds);
        // 转成 Map<id, likeCount>
        return posts.stream()
                .collect(Collectors.toMap(
                        Post::getId,
                        Post::getLikeCount,
                        (a, b) -> a  // 不会重复
                ));
    }

    /** 查询 user_post_likes 表，看哪些记录存在 */
    @Override
    public Map<Long, Boolean> getUserLikedStatus(String userId, List<Long> postIds){

        if (postIds == null || postIds.isEmpty() || userId == null) {
            return Collections.emptyMap();
        }
        // 用 QueryWrapper 限定 userId 和 postIds
        List<UserPostLikes> likedList = userPostLikesMapper.selectList(
                new QueryWrapper<UserPostLikes>()
                        .eq("userId", userId)
                        .in("postId", postIds)
                        .select("postId")  // 只查询 post_id
        );
        Set<Long> likedSet = likedList.stream()
                .map(UserPostLikes::getPostId)
                .collect(Collectors.toSet());

        // 构造返回
        return postIds.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        likedSet::contains
                ));
    }

    private static PostSummaryDTO getpostSummaryDTO(Post post, String userName) {
        PostSummaryDTO dto = new PostSummaryDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setAuthor(userName);
        dto.setLikeCount(post.getLikeCount());
        dto.setCommentCount(post.getCommentCount());
        dto.setUpdateTime(post.getUpdateTime());
        String content = post.getContent();
        dto.setContent(
                content.length() > 50
                        ? content.substring(0, 50) + "…"
                        : content
        );
        return dto;
    }

}

