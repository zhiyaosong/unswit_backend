package com.unswit.usercenter.service;

import com.unswit.usercenter.model.domain.Blacklist;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zhiyao
* @description 针对表【blacklist】的数据库操作Service
* @createDate 2025-07-21 21:24:32
*/
public interface BlacklistService extends IService<Blacklist> {
    /** 记录一次登录并根据当天不同 IP 数自动拉黑 */
    void recordLogin(String userId, String ip);

    /** 检查某 type-target 是否在黑名单 */
    boolean isBlacklisted(String type, String target);
}
