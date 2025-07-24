package com.unswit.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unswit.usercenter.model.domain.Blacklist;
import com.unswit.usercenter.repository.BlacklistRepository;
import com.unswit.usercenter.service.BlacklistService;
import com.unswit.usercenter.mapper.BlacklistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

/**
* @author zhiyao
* @description 针对表【blacklist】的数据库操作Service实现
* @createDate 2025-07-21 21:24:32
*/
@Service
public class BlacklistServiceImpl extends ServiceImpl<BlacklistMapper, Blacklist>
    implements BlacklistService{
    private static final String LOGIN_IPS_KEY_FMT = "USER:LOGINS:%s";
    private static final String BL_SET_USER       = "BLACKLIST:USER";
    private static final String BL_SET_IP         = "BLACKLIST:IP";
    private static final Duration DAY_TTL        = Duration.ofHours(24);

    @Autowired
    private StringRedisTemplate redis;
    @Autowired
    private BlacklistRepository repo;

    @Override
    @Transactional
    public void recordLogin(String userId, String ip) {
        // 1) 将今天的 ip 加入 Set，并设置 24h 过期
        String ipKey = String.format(LOGIN_IPS_KEY_FMT, userId);
        BoundSetOperations<String, String> ips = redis.boundSetOps(ipKey);
        ips.add(ip);
        ips.expire(DAY_TTL);

        // 2) 检查不同 IP 数量
        long count = ips.size();
        if (count > 3) {
            // 3) 持久化 & 同步 Redis 黑名单
            blackListIfNeeded("USER", userId, "超3个IP登录");
            blackListIfNeeded("IP",   ip,     "用户超限登录时的IP");
        }
    }
    private void blackListIfNeeded(String type, String target, String reason) {
        // 3.1 MySQL
        if (!repo.existsByTypeAndTarget(type, target)) {
            Blacklist ent = new Blacklist();
            ent.setType(type);
            ent.setTarget(target);
            ent.setReason(reason);
            repo.save(ent);
        }
        // 3.2 Redis Set
        String setKey = "USER".equals(type) ? BL_SET_USER : BL_SET_IP;
        redis.opsForSet().add(setKey, target);
    }

    @Override
    public boolean isBlacklisted(String type, String target) {
        // 1) 先查 Redis
        String setKey = "USER".equals(type) ? BL_SET_USER : BL_SET_IP;
        Boolean inRedis = redis.opsForSet().isMember(setKey, target);
        if (Boolean.TRUE.equals(inRedis)) {
            return true;
        }
        // 2) 回落查 MySQL
        return repo.existsByTypeAndTarget(type, target);
    }
}




