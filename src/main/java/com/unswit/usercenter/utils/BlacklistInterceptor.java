package com.unswit.usercenter.utils;
import com.unswit.usercenter.service.BlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static jdk.nashorn.internal.objects.Global.print;

@Component
public class BlacklistInterceptor implements HandlerInterceptor {

    @Resource
    private BlacklistService blacklistService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        //System.out.println("进来");
        String userId = UserHolder.getUser().getId();
        //System.out.println("失败");
        String ip     = extractClientIp(req);

        if (userId != null && blacklistService.isBlacklisted("USER", userId)) {
            resp.sendError(HttpStatus.FORBIDDEN.value(), "用户已被拉黑");
            return false;
        }
        if (blacklistService.isBlacklisted("IP", ip)) {
            resp.sendError(HttpStatus.FORBIDDEN.value(), "IP 已被拉黑");
            return false;
        }
        return true;
    }

    private String extractClientIp(HttpServletRequest req) {
        String xf = req.getHeader("X-Forwarded-For");
        if (xf != null && !xf.isEmpty()) {
            return xf.split(",")[0].trim();
        }
        return req.getRemoteAddr();
    }
}
