package com.wle.blog.service.iml;

import com.wle.blog.mapper.TagMapper;
import com.wle.blog.pojo.Tag;
import com.wle.blog.service.TagService;
import com.wle.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    public TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }

    public List<TagVo> copyList(List<Tag> tags) {
        ArrayList<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tags) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public List<TagVo> listTagById(Long articleId) {
        List<Tag> tags = tagMapper.getTagById(articleId);
        List<TagVo> tagVos = copyList(tags);
        return tagVos;
    }

    @Override
    public List<TagVo> hots(int limit) {
        /**
         * 1、标签下所拥有文章最多
         * 2、查询tag_id分组
         */
        List<Long> tagIds = tagMapper.findHotTagIds(limit);
        if (CollectionUtils.isEmpty(tagIds)) {
            return Collections.emptyList();
        }
        List<Tag> tagList = tagMapper.findTagByTagsId(tagIds);
        return copyList(tagList);
    }
}
