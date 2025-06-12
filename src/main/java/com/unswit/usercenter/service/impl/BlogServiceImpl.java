package com.unswit.usercenter.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unswit.usercenter.dto.Result;
import com.unswit.usercenter.model.domain.Blog;
import com.unswit.usercenter.service.BlogService;
import com.unswit.usercenter.mapper.BlogMapper;
import com.unswit.usercenter.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result likeBolog(Long id) {
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

}




