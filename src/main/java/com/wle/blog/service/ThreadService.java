package com.wle.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wle.blog.mapper.ArticlesMapper;
import com.wle.blog.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticlesMapper articlesMapper, Article article) {
        Article updateArticle = new Article();
        updateArticle.setViewCounts(article.getViewCounts() + 1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getViewCounts, article.getViewCounts());
        queryWrapper.eq(Article::getId, article.getId());
        articlesMapper.update(updateArticle, queryWrapper);
    }
}
