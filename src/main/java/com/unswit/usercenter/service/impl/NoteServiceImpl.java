package com.unswit.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unswit.usercenter.model.domain.Note;
import com.unswit.usercenter.service.NoteService;
import com.unswit.usercenter.mapper.NoteMapper;
import org.springframework.stereotype.Service;

/**
* @author zhiyao
* @description 针对表【note(笔记)】的数据库操作Service实现
* @createDate 2025-05-30 14:13:42
*/
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note>
    implements NoteService{

}




