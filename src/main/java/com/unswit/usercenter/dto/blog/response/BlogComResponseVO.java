package com.unswit.usercenter.dto.blog.response;

import com.unswit.usercenter.dto.blog.BlogDetailsDTO;
import com.unswit.usercenter.dto.blog.CommentDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BlogComResponseVO implements Serializable {
    /**
     * 博客内容
     */
    private BlogDetailsDTO blog;

    /**
     * 评论列表（包含子评论）
     */
    private List<CommentDTO> comments;
}