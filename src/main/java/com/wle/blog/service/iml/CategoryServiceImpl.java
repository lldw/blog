package com.wle.blog.service.iml;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wle.blog.mapper.CategoryMapper;
import com.wle.blog.pojo.Category;
import com.wle.blog.service.CategoryService;
import com.wle.blog.vo.CategoryVo;
import com.wle.blog.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Result findAll() {
        List<Category> categoryList = categoryMapper.selectList(new LambdaUpdateWrapper<>());
        return Result.success(copyList(categoryList));
    }

    private List<CategoryVo> copyList(List<Category> categoryList) {
        ArrayList<CategoryVo> categoryVoList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryVoList.add(copy(category));
        }
        return categoryVoList;
    }

    private CategoryVo copy(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }
}
