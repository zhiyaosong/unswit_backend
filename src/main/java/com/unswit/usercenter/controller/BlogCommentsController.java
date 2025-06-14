package com.unswit.usercenter.controller;

import com.unswit.usercenter.dto.Result;
import com.unswit.usercenter.dto.request.BlogCommentRequestDTO;
import com.unswit.usercenter.model.domain.BlogComments;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog-comments")
public class BlogCommentsController {
    @PostMapping
    public Result addComment(@RequestBody BlogCommentRequestDTO blogCommentRequestDTO) {

        return null;
    }

}
