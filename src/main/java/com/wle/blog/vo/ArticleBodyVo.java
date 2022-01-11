package com.wle.blog.vo;


import lombok.Data;

@Data
public class ArticleBodyVo {
    private Long id;
    private String content;
    private String contentHtml;
    private String articleId;
}
