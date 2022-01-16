package com.wle.blog.vo;

import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {
    private Long id;

    private String title;

    private String summary;

    private int commentCounts;

    private int viewCounts;

    private int weight;
    /**
     * 创建时间
     */
    private String createDate;

    private List<TagVo> tags;

    private String author;

    private ArticleBodyVo body;

    private CategoryVo category;

}
