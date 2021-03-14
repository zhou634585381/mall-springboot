package com.example.mall.controller;


import com.example.mall.entity.Carousel;
import com.example.mall.service.impl.CarouselServiceImpl;
import com.example.mall.utils.ResultMessage;
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
public class CarouselController {
    @Autowired
    private ResultMessage resultMessage;
    @Autowired
    private CarouselServiceImpl carouselService;

    @GetMapping("/getAllList")
    public ResultMessage carousels() {
        List<Carousel> carousels = carouselService.getCarouselList();
        resultMessage.success("001", carousels);
        return resultMessage;
    }
}
