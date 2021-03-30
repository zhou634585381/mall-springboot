package com.example.mall.controller;


import com.example.mall.entity.Category;
import com.example.mall.service.impl.CarouselServiceImpl;
import com.example.mall.service.impl.CategoryServiceImpl;
import com.example.mall.utils.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
@RestController
@RequestMapping("/mall/category")
@Api(tags = "商品分类")
public class CategoryController {
    @Autowired
    private ResultMessage resultMessage;
    @Autowired
    private CategoryServiceImpl categoryService;

    @ApiOperation(value = "获取商品种类")
    @GetMapping("/getAllList")
    public ResultMessage category() {
        List<Category> categories = categoryService.getCategoryAll();
        resultMessage.success("001", categories);
        return resultMessage;
    }
}
