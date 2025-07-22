package com.unswit.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.unswit.usercenter.dto.post.PostSummaryDTO;
import com.unswit.usercenter.dto.user.UserSimpleDTO;
import com.unswit.usercenter.dto.user.request.ChangePasswordRequestVO;
import com.unswit.usercenter.dto.user.request.UserUpdateInfoRequestVO;
import com.unswit.usercenter.service.BlacklistService;
import com.unswit.usercenter.utils.RedisConstants;
import com.unswit.usercenter.utils.responseUtils.BaseResponse;
import com.unswit.usercenter.utils.responseUtils.ErrorCode;
import com.unswit.usercenter.utils.responseUtils.ResultUtils;
import com.unswit.usercenter.dto.PagedResult;
import com.unswit.usercenter.dto.user.UserStatsDTO;
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
import java.util.stream.Collectors;

import static com.unswit.usercenter.contant.UserConstant.ADMIN_ROLE;
import static com.unswit.usercenter.contant.UserConstant.USER_LOGIN_STATE;


/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:8000","https://unswit.com"},methods = {RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT}, allowCredentials = "true")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private BlacklistService blacklistService;

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
            HttpServletResponse response,HttpServletRequest request) {
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
        if (token == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }

        // 2. 把 token 写进 HttpOnly Cookie
        Cookie cookie = new Cookie("access_token", token);
        cookie.setHttpOnly(true);
//        cookie.setSecure(true);                         // 生产环境强制 HTTPS
        cookie.setPath("/");                             // 整个域名下都带该 Cookie
        cookie.setMaxAge(RedisConstants.LOGIN_USER_TTL.intValue() * 60);

        response.addCookie(cookie);
        // 1. 拿userid
        String userId = UserHolder.getUser().getId();
        System.out.println("userId"+userId);
        // 2. 提取客户端 IP
        String ip = extractClientIp(request);
        // 3. 记录登录并自动触发可能的拉黑
        blacklistService.recordLogin(userId, ip);
        return ResultUtils.success("ok");
    }
    private String extractClientIp(HttpServletRequest req) {
        // 1. 尝试从 “X-Forwarded-For” 请求头中读取
        String xf = req.getHeader("X-Forwarded-For");

        // 2. 如果该头存在且不为空，则该值通常是一个以逗号分隔的 IP 列表：
        //    客户端 IP, 第一级代理 IP, 第二级代理 IP, …
        //    我们取第一个（列表中的第一个元素），也就是最原始的客户端 IP
        if (xf != null && !xf.isEmpty()) {
            return xf.split(",")[0].trim();
        }

        // 3. 如果 “X-Forwarded-For” 头不存在或为空，则回退到 Servlet 提供的
        //    req.getRemoteAddr()，它返回的是与服务器直接建立连接的那一端 IP，
        //    在无代理或单层代理时即为真实客户端 IP。
        return req.getRemoteAddr();
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
    @GetMapping("/stats")
    public BaseResponse<UserStatsDTO> getUserSummary() {
        String userId = UserHolder.getUser().getId();
        if(userId==null){
            return ResultUtils.error(ErrorCode.NOT_LOGIN);
        }
        UserStatsDTO summary = userService.getUserStats(userId);
        return ResultUtils.success(summary);
    }

    /**
     * 笔记卡片列表
     * @return
     */
    @GetMapping("/notes")
    public BaseResponse<PagedResult<NoteSummaryDTO>> getMyNotes(
            @RequestParam(value = "current", defaultValue = "1") long current,
            @RequestParam(value = "pageSize", defaultValue = "10") long pageSize) {
        String userId = UserHolder.getUser().getId();
        if (userId == null) {
            return ResultUtils.error(ErrorCode.NOT_LOGIN);
        }
        // 调用 Service 获取 MyBatis-Plus 分页对象
        IPage<NoteSummaryDTO> page = userService.getMyNotes(userId, current, pageSize);
        PagedResult<NoteSummaryDTO> result = new PagedResult<>();
        result.setData(page.getRecords());
        result.setTotal(page.getTotal());
        return ResultUtils.success(result);
    }

    @GetMapping("/posts")
    public BaseResponse<PagedResult<PostSummaryDTO>> getMyPost(
            @RequestParam(value = "current", defaultValue = "1") long current,
            @RequestParam(value = "pageSize", defaultValue = "10") long pageSize) {
        String userId = UserHolder.getUser().getId();
        if (userId==null) {
            return ResultUtils.error(ErrorCode.NOT_LOGIN);
        }
        IPage<PostSummaryDTO> page = userService.getMyPost(userId, current, pageSize );
        PagedResult<PostSummaryDTO> result = new PagedResult<>();
        result.setData(page.getRecords());
        result.setTotal(page.getTotal());
        return ResultUtils.success(result);
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

    /** 更新基本信息 */
    @PutMapping("/basic")
    public BaseResponse<User> updateBasic(
            @RequestBody UserUpdateInfoRequestVO vo,
            @CookieValue(name = "access_token", required = false) String token
    ) {
        String userId = UserHolder.getUser().getId();
        User updated = userService.updateBasicInfo(userId, vo, token);
        return ResultUtils.success(updated);
    }

    /** 修改密码 */
    @PostMapping("/password")
    public BaseResponse<String> changePassword(
            @RequestBody ChangePasswordRequestVO vo
    ) {
        UserSimpleDTO user = UserHolder.getUser();
        userService.changePassword(user.getId(), vo.getOldPassword(), vo.getNewPassword());
        return ResultUtils.success("ok");
    }

}
