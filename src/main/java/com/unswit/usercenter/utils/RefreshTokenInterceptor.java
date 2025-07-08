package com.unswit.usercenter.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;

import com.unswit.usercenter.dto.user.UserSimpleDTO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RefreshTokenInterceptor implements HandlerInterceptor {
    private final StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = "";
        // String token = request.getHeader("authorization");
        // 获取Cookie中的token
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies){
                if (cookie.getName().equals("access_token")){
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (StrUtil.isBlank(token)) {
            System.out.println("token is null");
            return true;
        }
        // 基于token获得redis中的用户
        String key = RedisConstants.LOGIN_USER_KEY+token;
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(key);

        if(userMap.isEmpty()){
            return true;
        }
        //将查询到的Hash数据转为UserDTO对象
        UserSimpleDTO userSimpleDTO = BeanUtil.fillBeanWithMap(userMap, new UserSimpleDTO(), false);
        //如果存在，保存用户到ThreadLocal中
        UserHolder.saveUser(userSimpleDTO);
        System.out.println("保存用户到ThreadLocal中");
        // 刷新token有效期
        stringRedisTemplate.expire(key, RedisConstants.LOGIN_CODE_TTL, TimeUnit.MINUTES);
        //放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);

        //移除用户
        System.out.println("移除用户从ThreadLocal中");
        UserHolder.removeUser();
    }
}
