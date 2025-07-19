package com.unswit.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.unswit.usercenter.dto.note.NoteSummaryDTO;
import com.unswit.usercenter.model.domain.Note;
import org.apache.ibatis.annotations.Param;

/**
* @author zhiyao
* @description 针对表【note(笔记)】的数据库操作Mapper
* @createDate 2025-06-02 02:09:55
* @Entity generator.domain.Note
*/
public interface NoteMapper extends BaseMapper<Note> {
    /**
     * 自定义分页查询 NoteSummaryDTO
     * @param page MP 的分页参数
     * @param userId 用户 ID
     */
    IPage<NoteSummaryDTO> selectNoteSummaries(@Param("page") IPage<?> page,
                                              @Param("userId") String userId);
}




