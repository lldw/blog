package com.wle.blog.pojo;

import lombok.Data;

/**
 * 分类实体类
 */

@Data
public class Category {
    private Long id;
    private String avatar;
    private String categoryName;
    private String description;
}
