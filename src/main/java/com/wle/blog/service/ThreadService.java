package com.wle.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wle.blog.mapper.ArticlesMapper;
import com.wle.blog.pojo.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ThreadService {
    @Async("taskExecutor")
    public void updateViewCount(ArticlesMapper articleMapper, Article article){
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(article.getViewCounts()+1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getViewCounts, article.getViewCounts());
        queryWrapper.eq(Article::getId, article.getId());
        log.info("更新浏览量");
        articleMapper.update(articleUpdate,queryWrapper) ;
        try {
            //睡眠5秒 证明不会影响主线程的使用
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
