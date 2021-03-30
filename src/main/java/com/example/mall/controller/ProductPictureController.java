package com.example.mall.controller;


import com.example.mall.entity.ProductPicture;
import com.example.mall.service.impl.ProductPictureServiceImpl;
import com.example.mall.utils.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/mall/product-picture")
@Api(tags = "商品图片")
public class ProductPictureController {
    @Autowired
    ProductPictureServiceImpl productPictureService;
    @Autowired
    private ResultMessage resultMessage;

    @ApiOperation(value = "获取商品图片")
    @GetMapping("/productPicture")
    public ResultMessage productPicture(Integer productId) {
        List<ProductPicture> products = productPictureService.getProductPictureByProductId(productId);
        resultMessage.success("001", products);
        return resultMessage;
    }
}
