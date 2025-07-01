package com.unswit.usercenter.service;

import com.unswit.usercenter.dto.Result;
import com.unswit.usercenter.dto.response.BlogSummaryDTO;
import com.unswit.usercenter.model.domain.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zhiyao
* @description 针对表【blog(帖子)】的数据库操作Service
* @createDate 2025-06-12 12:02:11
*/
public interface BlogService extends IService<Blog> {

    Result likeBlog(Long id);

    List<BlogSummaryDTO> getListBlogs(int page, int size);

}
