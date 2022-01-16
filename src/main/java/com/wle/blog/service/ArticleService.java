package com.wle.blog.service;


import com.wle.blog.vo.ArticleBodyVo;
import com.wle.blog.vo.ArticleVo;
import com.wle.blog.vo.CategoryVo;
import com.wle.blog.vo.Result;
import com.wle.blog.vo.params.PageParams;

import java.util.List;


public interface ArticleService {

    List<ArticleVo> listArticlesPage(PageParams pageParams);

    Result hotArticle(int limit);

    Result newArticle(int limit);

    Result listArchives();

    CategoryVo findCategoryById(Long categoryId);

    ArticleBodyVo findArticleBodyById(Long bodyId);

    ArticleVo findArticleById(Long id);
}
