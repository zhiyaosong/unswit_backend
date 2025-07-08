package com.unswit.usercenter.dto.post.response;

import com.unswit.usercenter.dto.post.PostSummaryDTO;
import lombok.Data;

import java.util.List;

@Data
public class PostListResponseVO {
    private List<PostSummaryDTO> PostSumList;
    private int total;
}
