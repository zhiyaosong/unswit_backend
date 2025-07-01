package com.unswit.usercenter.dto.response;

import com.unswit.usercenter.dto.CommentDTO;
import com.unswit.usercenter.model.domain.Blog;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BlogWithCommentsDTO implements Serializable {
    /**
     * 博客内容
     */
    private Blog blog;

    /**
     * 评论列表（包含子评论）
     */
    private List<CommentDTO> comments;
}