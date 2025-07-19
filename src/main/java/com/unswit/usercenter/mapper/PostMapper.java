package com.unswit.usercenter.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.unswit.usercenter.dto.post.PostSummaryDTO;
import com.unswit.usercenter.model.domain.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author zy183
* @description 针对表【post(帖子)】的数据库操作Mapper
* @createDate 2025-06-14 17:19:25
* @Entity com.unswit.usercenter.model.domain.request.Blog
*/
public interface PostMapper extends BaseMapper<Post> {
    /**
     * 自定义分页查询 NoteSummaryDTO
     * @param page MP 的分页参数
     * @param userId 用户 ID
     */
    IPage<PostSummaryDTO> selectPostSummaries(@Param("page") IPage<?> page,
                                              @Param("userId") String userId);
}




