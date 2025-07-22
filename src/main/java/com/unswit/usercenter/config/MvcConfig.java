package com.unswit.usercenter.config;

import com.unswit.usercenter.utils.BlacklistInterceptor;
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

    @Resource
    private BlacklistInterceptor blacklistInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 决定哪个网页（url）需要判断：ThreadLocal里的用户信息是否存在
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        // 这里放行的路径没有context-path:/api前缀
                        "/user/login",
                        "/user/register",
                        "/welcome"
                        ).order(2);
        //  黑名单校验（放行登录、注册、欢迎页面）
        registry.addInterceptor(blacklistInterceptor)
                .excludePathPatterns(
                        "/user/login",
                        "/user/register",
                        "/welcome"
                        ).order(1);

        // 优先级最高的拦截器：每次请求刷新token时间，而不仅是登录时才更新token时间
        // 不放行所有
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate)).order(0);
    }
}
