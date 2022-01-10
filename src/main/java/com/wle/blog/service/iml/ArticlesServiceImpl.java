package com.wle.blog.service.iml;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wle.blog.dos.Archives;
import com.wle.blog.mapper.ArticlesMapper;
import com.wle.blog.pojo.Article;
import com.wle.blog.service.ArticleService;
import com.wle.blog.service.SysUserService;
import com.wle.blog.vo.ArticleVo;

import com.wle.blog.vo.Result;
import com.wle.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticlesServiceImpl implements ArticleService {
    @Autowired
    private ArticlesMapper articlesMapper;
    @Autowired
    private TagServiceImpl tagService;
    @Autowired
    private SysUserService userService;


    public ArticleVo copy(Article articles,boolean isTag ,boolean isAuthor ) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(articles, articleVo);
        articleVo.setCreateDate(new DateTime(articles.getCreateDate()).toString("yyyy-MM-dd"));

        if (isTag){
            Long id = articles.getId();
            articleVo.setTags(tagService.listTagById(id));
        };
        if (isAuthor) {
            Long authorId = articles.getAuthorId();
            articleVo.setAuthor(userService.getSysuserById(authorId).getNickname());
        };
        return articleVo;
    }

    private List<ArticleVo> copyList(List<Article> records,boolean isTag ,boolean isAuthor ) {
        ArrayList<ArticleVo> articleVos = new ArrayList<>();
        for (Article record : records) {
            articleVos.add(copy(record,isTag , isAuthor));
        }
        return articleVos;
    }

    /**
     * 分页查询
     * @param pageParams
     * @return
     */
    @Override
    public List<ArticleVo> listArticlesPage(PageParams pageParams) {

        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageParms());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
        Page<Article> articlesPage = articlesMapper.selectPage(page, queryWrapper);
        List<Article> records = articlesPage.getRecords();
        List<ArticleVo> articleVoList = copyList(records,true,true);
        return articleVoList;

    }

    /**
     * 查询最热文章
     * @param limit
     * @return
     */
    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articlesMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    /**
     * 查询最新文章
     * @param limit
     * @return
     */
    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articlesMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archives = articlesMapper.listArchives();
        return Result.success(archives);
    }


}
