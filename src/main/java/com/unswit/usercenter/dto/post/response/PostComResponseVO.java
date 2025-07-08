package com.unswit.usercenter.dto.post.response;

import com.unswit.usercenter.dto.post.PostDetailsDTO;
import com.unswit.usercenter.dto.post.CommentDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PostComResponseVO implements Serializable {
    /**
     * 博客内容
     */
    private PostDetailsDTO post;

    /**
     * 评论列表（包含子评论）
     */
    private List<CommentDTO> comments;
}