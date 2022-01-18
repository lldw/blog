package com.wle.blog.service.iml;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wle.blog.dos.Archives;
import com.wle.blog.mapper.ArticleBodyMapper;
import com.wle.blog.mapper.ArticlesMapper;
import com.wle.blog.mapper.CategoryMapper;
import com.wle.blog.pojo.Article;
import com.wle.blog.pojo.ArticleBody;
import com.wle.blog.pojo.Category;
import com.wle.blog.service.ArticleService;
import com.wle.blog.service.SysUserService;
import com.wle.blog.service.ThreadService;
import com.wle.blog.vo.ArticleBodyVo;
import com.wle.blog.vo.ArticleVo;

import com.wle.blog.vo.CategoryVo;
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
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleBodyMapper bodyMapper;


    public ArticleVo copy(Article articles, boolean isTag, boolean isAuthor) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(articles, articleVo);
        articleVo.setCreateDate(new DateTime(articles.getCreateDate()).toString("yyyy-MM-dd"));

        if (isTag) {
            Long id = articles.getId();
            articleVo.setTags(tagService.listTagById(id));
        }
        ;
        if (isAuthor) {
            Long authorId = articles.getAuthorId();
            articleVo.setAuthor(userService.getSysuserById(authorId).getNickname());
        }
        ;
        return articleVo;
    }

    public ArticleVo copy(Article articles, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(articles, articleVo);
        articleVo.setCreateDate(new DateTime(articles.getCreateDate()).toString("yyyy-MM-dd"));

        if (isTag) {
            Long id = articles.getId();
            articleVo.setTags(tagService.listTagById(id));
        }
        ;
        if (isAuthor) {
            Long authorId = articles.getAuthorId();
            articleVo.setAuthor(userService.getSysuserById(authorId).getNickname());
        }
        ;
        if (isBody) {
            Long bodyId = articles.getBodyId();
            articleVo.setArticleBody(findArticleBodyById(bodyId));
        }
        if (isCategory) {
            Long categoryId = articles.getCategoryId();
            articleVo.setCategory(findCategoryById(categoryId));
        }
        return articleVo;
    }


    private CategoryVo findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }

    /**
     * 查询文章内容
     *
     * @param bodyId
     * @return
     */
    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody content = bodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(content.getContent());
        return articleBodyVo;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        ArrayList<ArticleVo> articleVos = new ArrayList<>();
        for (Article record : records) {
            articleVos.add(copy(record, isTag, isAuthor));
        }
        return articleVos;
    }

    /**
     * 分页查询
     *
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
        List<ArticleVo> articleVoList = copyList(records, true, true);
        return articleVoList;

    }

    /**
     * 查询最热文章
     *
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
        return Result.success(copyList(articles, false, false));
    }

    /**
     * 查询最新文章
     *
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
        return Result.success(copyList(articles, false, false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archives = articlesMapper.listArchives();
        return Result.success(archives);
    }

    /**
     * 文章详情
     *
     * @param articleId
     * @return
     */

    @Autowired
    private ThreadService threadService;

    @Override
    public Result findArticleById(Long articleId) {
        Article article = articlesMapper.selectById(articleId);
        threadService.updateArticleViewCount(articlesMapper, article);
        return Result.success(copy(article, true, true, true, true));
    }

}
