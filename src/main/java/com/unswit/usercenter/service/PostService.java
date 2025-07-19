package com.unswit.usercenter.service;

import com.unswit.usercenter.dto.post.response.PostListResponseVO;
import com.unswit.usercenter.model.domain.Post;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author zhiyao
* @description 针对表【post(帖子)】的数据库操作Service
* @createDate 2025-06-12 12:02:11
*/
public interface PostService extends IService<Post> {

    Long likePost(Long id);

    PostListResponseVO getListPosts(int page, int size, String sortBy, String sortOrder);

    Map<Long, Integer> getLikeCounts(List<Long> postIds);

    Map<Long, Boolean> getUserLikedStatus(String userId, List<Long> postIds);
}
