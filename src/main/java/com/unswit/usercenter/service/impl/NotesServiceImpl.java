package com.unswit.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unswit.usercenter.model.domain.Notes;
import com.unswit.usercenter.service.NotesService;
import com.unswit.usercenter.mapper.NotesMapper;
import org.springframework.stereotype.Service;

/**
* @author zy183
* @description 针对表【notes(笔记)】的数据库操作Service实现
* @createDate 2025-05-29 18:17:23
*/
@Service
public class NotesServiceImpl extends ServiceImpl<NotesMapper, Notes>
    implements NotesService{

}




