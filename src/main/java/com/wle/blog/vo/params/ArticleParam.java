package com.wle.blog.vo.params;


import com.wle.blog.vo.CategoryVo;
import com.wle.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * 文章参数
 */
@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;

}
