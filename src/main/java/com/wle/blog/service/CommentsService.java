package com.wle.blog.service;

import com.wle.blog.vo.CommentVo;
import com.wle.blog.vo.Result;
import com.wle.blog.vo.params.CommentParams;

import java.util.List;

public interface CommentsService {
    List<CommentVo> findCommentById(Long id);

    Result comment(CommentParams commentParams);
}
