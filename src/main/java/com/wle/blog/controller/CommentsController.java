package com.wle.blog.controller;

import com.wle.blog.vo.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comments")
public class CommentsController {
    /**
     * 获取文章评论
     * @param id
     * @return
     */
    @PostMapping("article/{id}")
    public Result commentsByArticleId(@PathVariable("id") Long id) {

        return Result.success(null);
    }
}
