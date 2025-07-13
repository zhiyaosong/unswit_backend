package com.unswit.usercenter.utils;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是否需要拦截（ThreadLocal中是否有用户）
        System.out.println("进入登录拦截器");
        if(UserHolder.getUser()==null){
            System.out.println("userHolder is null");
            response.setStatus(401);
            return false;
        }
        //有用户 则放行
        System.out.println(UserHolder.getUser());
        return true;
    }
}
