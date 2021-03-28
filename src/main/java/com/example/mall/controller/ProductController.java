package com.example.mall.controller;


import com.example.mall.entity.Product;
import com.example.mall.service.impl.ProductServiceImpl;
import com.example.mall.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/mall/product")
public class ProductController {
    @Autowired
    private ResultMessage resultMessage;
    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/getProductByCategoryId")
    public ResultMessage getProductByCategoryId(Integer categoryId) {
        List<Product> list = productService.getProductByCategoryId(categoryId);
        resultMessage.success("001", list);
        return resultMessage;
    }

}
