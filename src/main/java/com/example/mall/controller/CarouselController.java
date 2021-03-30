package com.example.mall.controller;


import com.example.mall.entity.Carousel;
import com.example.mall.service.impl.CarouselServiceImpl;
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
@RequestMapping("/mall/carousel")
@Api(tags = "轮播图")
public class CarouselController {
    @Autowired
    private ResultMessage resultMessage;
    @Autowired
    private CarouselServiceImpl carouselService;

    @ApiOperation(value = "获取轮播图")
    @GetMapping("/getAllList")
    public ResultMessage carousels() {
        List<Carousel> carousels = carouselService.getCarouselList();
        resultMessage.success("001", carousels);
        return resultMessage;
    }
}
