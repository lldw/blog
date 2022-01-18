package com.wle.blog.pojo;

import lombok.Data;

@Data
public class Comments {
    private Long id;
    private String content;
    private Long createDate;
    private Integer articleId;
    private Long authorId;
    private Long parentId;
    private Long toUid;
    private String level;
}
