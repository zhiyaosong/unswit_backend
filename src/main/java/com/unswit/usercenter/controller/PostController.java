package com.unswit.usercenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unswit.usercenter.dto.post.request.PostLikeRequestVO;
import com.unswit.usercenter.dto.post.request.ToggleLikePostRequestVO;
import com.unswit.usercenter.dto.post.response.PostComResponseVO;
import com.unswit.usercenter.dto.post.response.PostLikeResponseVO;
import com.unswit.usercenter.dto.post.response.PostListResponseVO;
import com.unswit.usercenter.dto.post.response.ToggleLikePostResponseVO;
import com.unswit.usercenter.service.UserPostLikesService;
import com.unswit.usercenter.utils.responseUtils.BaseResponse;
import com.unswit.usercenter.utils.responseUtils.ErrorCode;
import com.unswit.usercenter.utils.responseUtils.ResultUtils;
import com.unswit.usercenter.dto.user.UserSimpleDTO;
import com.unswit.usercenter.model.domain.Post;
import com.unswit.usercenter.service.PostCommentsService;
import com.unswit.usercenter.service.PostService;
import com.unswit.usercenter.utils.SystemConstants;
import com.unswit.usercenter.utils.UserHolder;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Tag(name = "Post接口", description = "Post增删改查接口")
@RestController
@RequestMapping("/posts")
public class PostController {
    @Resource
    private PostService postService;
    @Resource
    private PostCommentsService postCommentsService;
    @Resource
    private UserPostLikesService userPostLikesService;

    /**
     * 新增post
     * @param post
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> savePost(@RequestBody Post post) {
        // 获取登录用户
        UserSimpleDTO user = UserHolder.getUser();
        post.setUserId(user.getId());
        // 保存博文
        postService.save(post);
        // 返回id
        return ResultUtils.success(post.getId());
    }

    /**
     * 获取自己发布的post
     * @param current
     * @return
     */
    @GetMapping("/of/me")
    public BaseResponse<List<Post>> queryMyPost(@RequestParam(value = "current", defaultValue = "1") Integer current) {
        // 获取登录用户
        UserSimpleDTO user = UserHolder.getUser();
        // 根据用户查询
        Page<Post> page = postService.query()
                .eq("user_id", user.getId()).page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 获取当前页数据
        List<Post> records = page.getRecords();
        return ResultUtils.success(records);
    }


    /**
     * 帖子点赞
     * @param id
     * @return
     */
    @PutMapping("/like/{id}")
    public BaseResponse<Long> likePost(@PathVariable("id") Long id) {
        Long postId = postService.likePost(id);
        return ResultUtils.success(postId);
    }

    /**
     * BaseResponse<String> likePost 获取帖子点赞信息
     * @param req
     * @return postLikesResponseDTO
     */
    @PostMapping("likes")
    public BaseResponse<PostLikeResponseVO> getLikes(@RequestBody PostLikeRequestVO req) {

        String userId = req.getUserId();
        List<Long> postIds = req.getPostIds();
        if (userId == null || postIds == null || postIds.isEmpty()) {
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        // 1. 查询总点赞数
        Map<Long, Integer> likes = postService.getLikeCounts(postIds);
        // 2. 查询当前用户是否已点赞
        Map<Long, Boolean> likedByUser = postService.getUserLikedStatus(userId, postIds);

        // 3. 组装返回
        PostLikeResponseVO resp = new PostLikeResponseVO();
        resp.setLikes(likes);
        resp.setLikedByUser(likedByUser);

        return ResultUtils.success(resp);
    }

    /**
     * 切换点赞（如果之前已点赞则取消，否则就点赞）。
     * POST /api/note/like?userId=xxx
     * Body: { "noteId": 123 }
     */
    @PostMapping("/like")
    public BaseResponse<ToggleLikePostResponseVO> toggleLike(
            @RequestBody ToggleLikePostRequestVO req
    ) {
        ToggleLikePostResponseVO resp = userPostLikesService.toggleLike(req);
        return ResultUtils.success(resp);
    }


    /**
     * 展示某一篇post详情界面
     * @param postId
     * @return
     */
    // TODO: 增加错误处理
    @GetMapping("/{postId}")
    public BaseResponse<PostComResponseVO> getPost(@PathVariable Long postId) {

        return ResultUtils.success(postCommentsService.getPostComments(postId));
    }

    /**
     * 展示一个页单位所有post（无comment）
     * 只返回post
     * 将代码逻辑写在service里面
     * @param page
     * @param size
     * @return
     */
    @GetMapping({ "", "/" })
    public BaseResponse<PostListResponseVO> listposts(@RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "5") int size,
                                                      @RequestParam(required = false) String sortBy,
                                                      @RequestParam(required = false) String sortOrder) {
        PostListResponseVO listPosts = postService.getListPosts(page, size, sortBy, sortOrder);

        return ResultUtils.success(listPosts);
    }

}
