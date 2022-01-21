package com.wle.blog.controller;

import com.wle.blog.service.CommentsService;
import com.wle.blog.vo.CommentVo;
import com.wle.blog.vo.Result;
import com.wle.blog.vo.params.CommentParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static net.sf.jsqlparser.parser.feature.Feature.comment;

@RestController
@RequestMapping("comments")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;
    /**
     * 获取文章评论
     * @param id
     * @return
     */
    @PostMapping("article/{id}")
    public Result commentsByArticleId(@PathVariable("id") Long id) {
        List<CommentVo> commentVos = commentsService.findCommentById(id);
        return Result.success(commentVos);
    }
  @PostMapping("/comments/create/change")
    public Result comment(@RequestBody CommentParams commentParams){
       return commentsService.comment(commentParams);
    }
}
