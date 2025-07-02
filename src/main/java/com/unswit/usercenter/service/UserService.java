package com.unswit.usercenter.service;

import com.unswit.usercenter.dto.user.AccountCenterSummaryDTO;
import com.unswit.usercenter.dto.blog.BlogSummaryDTO;
import com.unswit.usercenter.dto.note.NoteSummaryDTO;
import com.unswit.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户服务
 */
public interface UserService extends IService<User> {


    User getUserByToken(String token);

    String userRegister(String userName, String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @return 脱敏后的用户信息
     */
    String userLogin(String userAccount, String userPassword);
    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request, String token);


    AccountCenterSummaryDTO getAccountCenterSummary(String userId);

    List<NoteSummaryDTO> getNoteSummary(String userId);

    List<BlogSummaryDTO> getBlogSummary(String userId);

}
