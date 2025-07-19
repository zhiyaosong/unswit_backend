package com.unswit.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.unswit.usercenter.dto.user.UserStatsDTO;
import com.unswit.usercenter.dto.post.PostSummaryDTO;
import com.unswit.usercenter.dto.note.NoteSummaryDTO;
import com.unswit.usercenter.dto.user.request.UserUpdateInfoRequestVO;
import com.unswit.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

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


    UserStatsDTO getUserStats(String userId);

    /**
     * 分页获取某用户的 NoteSummaryDTO 列表
     * @param userId 用户 ID
     * @param current 页码
     * @param pageSize 每页条数
     * @return 包含 records 和 total 的 IPage 对象
     */
    IPage<NoteSummaryDTO> getMyNotes(String userId, long current, long pageSize);

    IPage<PostSummaryDTO> getMyPost(String userId, long current, long pageSize);

    /** 更新基本信息 */
    User updateBasicInfo(String userId, UserUpdateInfoRequestVO vo, String token);

    /** 修改密码，内部校验旧密码后更新 */
    void changePassword(String userId, String oldPwd, String newPwd);

}
