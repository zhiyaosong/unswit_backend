package com.unswit.usercenter.repository;

import com.unswit.usercenter.model.domain.Blacklist;
import org.springframework.data.jpa.repository.JpaRepository;    // ← 这个导入必须有
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {
    /**
     * 根据 type 和 target 判断黑名单里是否已存在
     * （Spring Data JPA 会根据方法名自动生成 SQL）
     */
    boolean existsByTypeAndTarget(String type, String target);
}
