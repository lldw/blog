package com.wle.blog.vo;


import lombok.Data;

import java.util.List;

@Data
public class CommentVo {
    private Long id;
    //用户信息
    private UserVo author;
    //评论内容
    private String content;
    //子评论
    private List<CommentVo> childrens;
    //创建时间
    private String createDate;
    //1是父评论  2 是子评论
    private Integer level;
    //给谁评论
    private UserVo toUser;
}
