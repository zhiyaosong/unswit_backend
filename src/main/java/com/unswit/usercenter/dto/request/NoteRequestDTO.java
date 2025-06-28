package com.unswit.usercenter.dto.request;

import lombok.Data;

@Data
public class NoteRequestDTO {
    private String title; // 笔记名称

    private String author; // 笔记作者名称

    private String toolTip;

    private String code; // 课程代码

    private String lecturer;  // leaturer名字

    private String enrollTerm; // 注册课程学期

    private String enrollYear; // 注册课程年份

    private String link;  // 笔记链接
}
