package com.unswit.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.unswit.usercenter.utils.responseUtils.BaseResponse;
import com.unswit.usercenter.utils.responseUtils.ErrorCode;
import com.unswit.usercenter.utils.responseUtils.ResultUtils;
import com.unswit.usercenter.dto.Result;
import com.unswit.usercenter.dto.user.AccountCenterSummaryDTO;
import com.unswit.usercenter.dto.note.NoteSummaryDTO;
import com.unswit.usercenter.exception.BusinessException;
import com.unswit.usercenter.model.domain.User;
import com.unswit.usercenter.dto.user.request.UserLoginRequestVO;
import com.unswit.usercenter.dto.user.request.UserRegisterRequestVO;
import com.unswit.usercenter.service.UserService;
import com.unswit.usercenter.utils.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.unswit.usercenter.contant.UserConstant.ADMIN_ROLE;
import static com.unswit.usercenter.contant.UserConstant.USER_LOGIN_STATE;


/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:8000","http://124.220.105.199"},methods = {RequestMethod.POST,RequestMethod.GET}, allowCredentials = "true")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public BaseResponse<List<User>> getUserList(HttpServletRequest request) {
        System.out.println("收到查询");
        // 仅管理员可查询用户列表
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "缺少管理员权限");
        }
        // 查询所有用户
        List<User> userList = userService.list();
        // 将用户信息处理为安全信息（去除敏感数据）
        List<User> safetyList = userList.stream()
                .map(user -> userService.getSafetyUser(user))
                .collect(Collectors.toList());
        return ResultUtils.success(safetyList);
    }

    /**
     * 用户注册
     *
     * @param userRegisterRequestVO
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<String> userRegister(@RequestBody UserRegisterRequestVO userRegisterRequestVO) {
        // 校验
        if (userRegisterRequestVO == null) {
            System.out.println("请求为空");
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userName = userRegisterRequestVO.getUserName();
        String userAccount = userRegisterRequestVO.getUserAccount();
        String userPassword = userRegisterRequestVO.getUserPassword();
        String checkPassword = userRegisterRequestVO.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            System.out.println("字段为空");
            return null;
        }
        /**
         * error:
         *      {-2: "账户不是由字母、数字和下划线组成",
         *       -1: "密码和校验密码不相同"，
         *       -3: "数据存入失败"}
         */
        String result = userService.userRegister(userName, userAccount, userPassword, checkPassword);

        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequestVO
     * @return token
     */
    @PostMapping("/login")
    public BaseResponse<String> userLogin(
            @RequestBody UserLoginRequestVO userLoginRequestVO,
            HttpServletResponse response) {
        //实现登陆功能
        //1.判空 返回错误
        if (userLoginRequestVO == null) {
            System.out.println("请求为空");
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequestVO.getUserAccount();
        String userPassword = userLoginRequestVO.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }

        String token = userService.userLogin(userAccount, userPassword);
        if (Objects.equals(token, "用户为空") || Objects.equals(token, "账户不符合要求") || token == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }

        // 2. 把 token 写进 HttpOnly Cookie
        Cookie cookie = new Cookie("access_token", token);
        cookie.setHttpOnly(true);
//        cookie.setSecure(true);                         // 生产环境强制 HTTPS
        cookie.setPath("/");                             // 整个域名下都带该 Cookie
        cookie.setMaxAge((int) TimeUnit.MINUTES.toSeconds(30)); // 30 分钟过期

        response.addCookie(cookie);
        System.out.println("已加入cookie");
        return ResultUtils.success("ok");
    }

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(
            HttpServletRequest request,
            HttpServletResponse response,
            @CookieValue("access_token") String token) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = 0;
        if (token != null) {
            result = userService.userLogout(request, token);
        }
        // 同时让前端“清除”该 Cookie
        Cookie cookie = new Cookie("access_token", "");
        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 立即过期
        response.addCookie(cookie);

        return ResultUtils.success(result);
    }

    /**
     * 获取当前用户
     *
     * @param request
     * @return
     */
    @GetMapping("/current")
    public BaseResponse<User> getUserByToken(
            HttpServletRequest request,
            @CookieValue(name = "access_token", required = false) String token
            ) {
//        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
//        User currentUser = (User) userObj;
        System.out.println("进入control");
        if (token == null || token.isEmpty()) {
            throw new BusinessException("未携带登录 token",50002,"未登录，无法查看论坛等内容");
        }

        User safetyUser = userService.getUserByToken(token);

        return ResultUtils.success(safetyUser);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "缺少管理员权限");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 拿到用户中心上方的简要数据框
     * @param
     * @return
     */
    @GetMapping("acount/center/summary")
    public Result getUserSummary() {
        String userId = UserHolder.getUser().getId();
        if(userId==null){
            return Result.fail("用户未登陆！");
        }
        AccountCenterSummaryDTO summary = userService.getAccountCenterSummary(userId);
        return Result.ok(summary);
    }

    /**
     * 笔记卡片列表
     * @return
     */
    @GetMapping("account/center/mynote")
    public Result getMyNote() {
        String userId = UserHolder.getUser().getId();
        if (userId==null) {
            return Result.fail("用户未登陆");
        }
        List<NoteSummaryDTO> noteSummary = userService.getNoteSummary(userId);

        return Result.ok(noteSummary);
    }

    @GetMapping("account/center/myblog")
    public Result getMyBlog() {
        String userId = UserHolder.getUser().getId();
        if (userId==null) {
            return Result.fail("用户未登陆");
        }
        userService.getBlogSummary(userId);
        return Result.ok();
    }


    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }

}
