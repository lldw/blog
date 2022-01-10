package com.wle.blog.controller;

import com.wle.blog.service.TagService;
import com.wle.blog.vo.Result;
import com.wle.blog.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/hot")
    public Result listHotTags() {
        int limit = 6;
        List<TagVo> hots = tagService.hots(limit);
        return Result.success(hots);
    }
}
