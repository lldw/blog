package com.wle.blog.service;



import com.wle.blog.vo.TagVo;


import java.util.List;


public interface TagService {

    List<TagVo> listTagById(Long ArticlesId);

    List<TagVo> hots(int limit);
}
