package com.example.mall.service;

import com.example.mall.entity.Carousel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
public interface ICarouselService extends IService<Carousel> {
    /**
     * 获取轮播图
     *
     * @return
     */
    List<Carousel> getCarouselList();

}
