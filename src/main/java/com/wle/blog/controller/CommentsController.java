package com.wle.blog.controller;

import com.wle.blog.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comments")
public class CommentsController {
    @PostMapping("article/{id}")
    public Result commentsByArticleId(Long id) {

    }
}
