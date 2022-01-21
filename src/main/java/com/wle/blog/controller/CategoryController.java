package com.wle.blog.controller;


import com.wle.blog.service.CategoryService;
import com.wle.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取文章分类
     *
     * @return
     */
    @GetMapping
    public Result listCategory() {
        return categoryService.findAll();
    }
}
