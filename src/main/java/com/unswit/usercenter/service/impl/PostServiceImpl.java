package com.unswit.usercenter.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unswit.usercenter.dto.post.response.PostListResponseVO;
import com.unswit.usercenter.dto.post.PostSummaryDTO;
import com.unswit.usercenter.exception.BusinessException;
import com.unswit.usercenter.mapper.UserPostLikesMapper;
import com.unswit.usercenter.mapper.UserMapper;

import com.unswit.usercenter.model.domain.Post;
import com.unswit.usercenter.model.domain.User;
import com.unswit.usercenter.model.domain.UserPostLikes;
import com.unswit.usercenter.service.PostService;
import com.unswit.usercenter.mapper.PostMapper;
import com.unswit.usercenter.utils.UserHolder;
import com.unswit.usercenter.utils.responseUtils.ErrorCode;
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

    private static final Map<String, SFunction<Post, ?>> SORT_COLUMN_MAP = new HashMap<>();
    static {
        SORT_COLUMN_MAP.put("createTime", Post::getCreateTime);
        SORT_COLUMN_MAP.put("likeCount", Post::getLikeCount);
        SORT_COLUMN_MAP.put("commentCount", Post::getCommentCount);
//        SORT_COLUMN_MAP.put("viewCount", Post::getViewCount);
    }

    @Override
    public PostListResponseVO getListPosts(int page, int size, String sortBy, String sortOrder) {
        // 安全处理分页参数
        page = Math.max(page, 1);
        size = Math.min(Math.max(size, 1), 100); // 防滥用
        // 归一化排序
        String finalSortOrder = normalizeSortOrder(sortOrder);

        // 默认排序字段
        if (sortBy == null || !SORT_COLUMN_MAP.containsKey(sortBy)) {
            sortBy = "createTime"; // 默认按创建时间
        }

        // 取出对应列的引用
        SFunction<Post, ?> column = (SFunction<Post, ?>) SORT_COLUMN_MAP.get(sortBy);

        // 构造分页对象
        Page<Post> mpPage = new Page<>(page, size);

        // 构造 Wrapper
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();

        // 根据方向添加排序
        if ("asc".equals(finalSortOrder)) {
            wrapper.orderByAsc(column);
        } else {
            wrapper.orderByDesc(column);
        }

        Page<Post> postPage = this.page(mpPage, wrapper);
        List<Post> posts = postPage.getRecords();

        // ===== 批量查用户，避免 N+1 =====
        Set<String> userIds = posts.stream()
                .map(Post::getUserId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<String, User> userMap;
        if (!userIds.isEmpty()) {
            List<User> users = userMapper.selectList(
                    new QueryWrapper<User>()
                            .in("id", userIds)
                            .select("id", "userName", "avatarUrl")
            );
            userMap = users.stream().collect(Collectors.toMap(User::getId, u -> u));
        } else {
            userMap = new HashMap<>();
        }

        List<PostSummaryDTO> listposts = posts.stream()
                .map(post -> {
                    User u = userMap.get(post.getUserId());
                    String userName = (u != null && u.getUserName() != null) ? u.getUserName() : "编程侠";
                    String avatar = (u != null && u.getAvatarUrl() != null) ? u.getAvatarUrl() : "/img/a.png";
                    return getpostSummaryDTO(post, userName, avatar);
                })
                .collect(Collectors.toList());


        // 3. 封装返回
        PostListResponseVO response = new PostListResponseVO();
        response.setPostSumList(listposts);
        response.setTotal((int) postPage.getTotal());
        return response;
    }

    private String normalizeSortOrder(String sortOrder) {
        if (sortOrder == null) return "desc";
        sortOrder = sortOrder.trim().toLowerCase();
        return ("asc".equals(sortOrder) ? "asc" : "desc"); // 默认 desc
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

    @Override
    public int deletePost(Long postId, String userId) {
        // 1. 校验帖子是否存在
        Post post = this.getById(postId);
        if (post == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        // 2. 校验当前用户是否为帖子作者
        if (!post.getUserId().equals(userId)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        System.out.println("postId:"+postId);
        // 3. 执行删除
        boolean removed = this.removeById(postId);

        return removed ? 1 : 0;
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

    private static PostSummaryDTO getpostSummaryDTO(Post post, String userName, String userAvatar) {
        PostSummaryDTO dto = new PostSummaryDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setAuthor(userName);
        dto.setAuthorAvatar(userAvatar);
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

