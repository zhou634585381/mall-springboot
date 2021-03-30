package com.example.mall.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.mall.entity.Product;
import com.example.mall.service.impl.ProductServiceImpl;
import com.example.mall.utils.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
@RestController
@RequestMapping("/mall/product")
@Api(tags = "商品信息")
public class ProductController {
    @Autowired
    private ResultMessage resultMessage;
    @Autowired
    private ProductServiceImpl productService;

    @ApiOperation(value = "获取某一种类商品信息")
    @GetMapping("/getProductByCategoryId")
    public ResultMessage getProductByCategoryId(Integer categoryId) {
        List<Product> list = productService.getProductByCategoryId(categoryId);
        resultMessage.success("001", list);
        return resultMessage;
    }

    @GetMapping("/getHotProduct")
    @ApiOperation(value = "获取热门商品信息")
    public ResultMessage getHotProduct() {
        List<Product> list = productService.getHotProduct();
        resultMessage.success("001", list);
        return resultMessage;

    }

    @GetMapping("/getProduct")
    @ApiOperation(value = "获取某个商品具体信息")
    public ResultMessage getProduct(Integer productId) {
        Product product = productService.getProductById(productId);
        resultMessage.success("001", product);
        return resultMessage;
    }
    @GetMapping("/getProductByPage")
    @ApiOperation(value = "分页查询商品信息")
    public ResultMessage getProductByPage(Integer pageNum, Integer pageSize,Integer categoryId) {
        IPage<Product> ipage = productService.getProductByPage(pageNum, pageSize, categoryId);
        resultMessage.success("001", ipage);
        return resultMessage;
    }
}
