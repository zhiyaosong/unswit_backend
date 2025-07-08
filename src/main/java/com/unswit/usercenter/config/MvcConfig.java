package com.unswit.usercenter.config;

import com.unswit.usercenter.utils.LoginInterceptor;
import com.unswit.usercenter.utils.RefreshTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 决定哪个网页（url）需要判断：ThreadLocal里的用户信息是否存在
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        // 这里放行的路径没有context-path:/api前缀
                        "/user/login",
                        "/user/register",
                        "/welcome"
                        ).order(1);
        // 优先级最高的拦截器：每次请求刷新token时间，而不仅是登录时才更新token时间
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate)).order(0);
    }
}
