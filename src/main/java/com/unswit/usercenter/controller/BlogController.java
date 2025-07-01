package com.unswit.usercenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unswit.usercenter.utils.responseUtils.BaseResponse;
import com.unswit.usercenter.utils.responseUtils.ResultUtils;
import com.unswit.usercenter.dto.Result;
import com.unswit.usercenter.dto.UserDTO;
import com.unswit.usercenter.model.domain.Blog;
import com.unswit.usercenter.service.BlogService;
import com.unswit.usercenter.utils.SystemConstants;
import com.unswit.usercenter.utils.UserHolder;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Tag(name = "Blog接口", description = "Blog增删改查接口")
@RestController
@RequestMapping("/blog")
public class BlogController {
    @Resource
    private BlogService blogService;

    /**
     * 新增blog
     * @param blog
     * @return
     */
    @PostMapping
    public BaseResponse<Long> saveBlog(@RequestBody Blog blog) {
        // 获取登录用户
        UserDTO user = UserHolder.getUser();
        blog.setUserId(user.getId());
        // 保存博文
        blogService.save(blog);
        // 返回id
        return ResultUtils.success(blog.getId());
    }

    /**
     * 获取自己发布的blog
     * @param current
     * @return
     */
    @GetMapping("/of/me")
    public BaseResponse<List<Blog>> queryMyBlog(@RequestParam(value = "current", defaultValue = "1") Integer current) {
        // 获取登录用户
        UserDTO user = UserHolder.getUser();
        // 根据用户查询
        Page<Blog> page = blogService.query()
                .eq("user_id", user.getId()).page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 获取当前页数据
        List<Blog> records = page.getRecords();
        return ResultUtils.success(records);
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


}
