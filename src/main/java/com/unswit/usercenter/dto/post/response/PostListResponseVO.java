package com.unswit.usercenter.dto.blog.response;

import com.unswit.usercenter.dto.blog.BlogSummaryDTO;
import lombok.Data;

import java.util.List;

@Data
public class BlogListResponseVO {
    private List<BlogSummaryDTO> BlogSumList;
    private int total;
}
