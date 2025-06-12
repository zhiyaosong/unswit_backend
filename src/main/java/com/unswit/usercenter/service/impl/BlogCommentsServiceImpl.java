package com.unswit.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unswit.usercenter.model.domain.BlogComments;
import com.unswit.usercenter.service.BlogCommentsService;
import com.unswit.usercenter.mapper.BlogCommentsMapper;
import org.springframework.stereotype.Service;

/**
* @author zhiyao
* @description 针对表【blog_comments(blog comments)】的数据库操作Service实现
* @createDate 2025-06-12 12:02:11
*/
@Service
public class BlogCommentsServiceImpl extends ServiceImpl<BlogCommentsMapper, BlogComments>
    implements BlogCommentsService{

}




