package com.unswit.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 笔记
 * @TableName notes
 */
@TableName(value ="notes")
@Data
public class Notes implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 课程码
     */
    private String code;

    /**
     * 课程名
     */
    private String title;

    /**
     * 笔记作者
     */
    private String author;

    /**
     * 任课教师
     */
    private String lecturer;

    /**
     * 所属课程体系
     */
    private String stream;

    /**
     * 自我描述
     */
    private String selfDescription;

    /**
     * 状态 0 - 正常， 1 - 封号
     */
    private Integer noteStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;
}