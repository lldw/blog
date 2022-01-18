package com.wle.blog.service;

import com.wle.blog.vo.Result;

public interface CommentsService {
    Result findCommentById(Long id);
}
