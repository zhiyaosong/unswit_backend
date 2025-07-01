package com.unswit.usercenter.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unswit.usercenter.dto.Result;
import com.unswit.usercenter.dto.response.BlogSummaryDTO;
import com.unswit.usercenter.mapper.UserMapper;
import com.unswit.usercenter.model.domain.Blog;
import com.unswit.usercenter.model.domain.User;
import com.unswit.usercenter.service.BlogService;
import com.unswit.usercenter.mapper.BlogMapper;
import com.unswit.usercenter.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.unswit.usercenter.utils.RedisConstants.BLOG_LIKED_KEY;

/**
* @author zhiyao
* @description 针对表【blog(帖子)】的数据库操作Service实现
* @createDate 2025-06-12 12:02:11
*/
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
    implements BlogService{
    @Resource
    private BlogService blogService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result likeBlog(Long id) {
        //1.获取登陆用户
        String userId = UserHolder.getUser().getId();

        //2.判断当前登陆用户是否已经点赞
        String key = "blog:liked"+id;
        Boolean isMember = stringRedisTemplate.opsForSet().isMember(key, userId);
        if(BooleanUtil.isFalse(isMember)){
            //4.未点赞的话点赞数+1
            //4.1 数据库点赞呢 +1
            boolean isSuccess = update().setSql("liked= liked+1").eq("id", id).update();
            //4.2 保存用户到redis的set集合
            if (isSuccess) {
                stringRedisTemplate.opsForSet().add(key, userId);
            }
        }else{
            //3.如果已经点赞，点赞数-1
            //3.1数据库点赞数-1
            boolean isSuccess = update().setSql("liked= liked-1").eq("id", id).update();
            //3.2 把用户从set集合中删除
            if (isSuccess) {
                stringRedisTemplate.opsForSet().remove(key, userId);
            }
        }

        return Result.ok();

    }

    @Override
    public List<BlogSummaryDTO> getListBlogs(int page, int size) {
        //返回一个Page<Blog>对象 里面包含当前页的结果和分页信息
        Page<Blog> blogPage = blogService.page(
                new Page<>(page, size),
                new LambdaQueryWrapper<Blog>()
                        .orderByDesc(Blog::getCreateTime)
        );
        List<Blog> blogs = blogPage.getRecords();

        List<BlogSummaryDTO> listblogs = blogs.stream().map(blog -> {
            String userId = blog.getUserId();
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("id", userId)
                    .select("userName");
            List<Object> result = userMapper.selectObjs(wrapper);
            String userName = result.isEmpty() ? "匿名用户" : (String) result.get(0);


            BlogSummaryDTO dto = new BlogSummaryDTO();
            dto.setId(blog.getId());
            dto.setTitle(blog.getTitle());
            dto.setUserName(userName);
            dto.setUpdateTime(blog.getUpdateTime());
            String content = blog.getContent();
            dto.setContent(content.length() > 50 ? content.substring(0, 50) + "..." : content);
            return dto;
        }).collect(Collectors.toList());

        return listblogs;
    }

}




