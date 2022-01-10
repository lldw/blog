package com.wle.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wle.blog.pojo.Tag;


import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章id查询标签
     * @param articleId
     * @return
     */
    List<Tag> getTagById(Long articleId);


    /**
     *查询最热的标签
     * @param limit
     * @return
     */
    List<Long> findHotTagIds(int limit);

    List<Tag> findTagByTagsId(List<Long> tagIds);
}
