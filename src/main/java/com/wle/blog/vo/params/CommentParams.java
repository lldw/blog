package com.wle.blog.vo.params;

import lombok.Data;

@Data
public class CommentParams {

    private Integer articleId;

    private String content;

    private Long parent;

    private Long toUserId;
}

