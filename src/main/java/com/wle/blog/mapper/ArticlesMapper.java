package com.wle.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wle.blog.dos.Archives;
import com.wle.blog.pojo.Article;

import java.util.List;


public interface ArticlesMapper extends BaseMapper<Article> {
    List<Archives> listArchives();
}
