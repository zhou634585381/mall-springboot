package com.example.mall.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.example.mall.entity.Carousel;
import com.example.mall.exception.ExceptionEnum;
import com.example.mall.exception.MallException;
import com.example.mall.mapper.CarouselMapper;
import com.example.mall.service.ICarouselService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements ICarouselService {
    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<Carousel> getCarouselList() {
        List<Carousel> list = null;
        try {
            list = carouselMapper.selectAll();
            if (ArrayUtils.isEmpty(list.toArray())) {
                throw new MallException(ExceptionEnum.GET_CAROUSEL_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MallException(ExceptionEnum.GET_CAROUSEL_ERROR);
        }
        return list;
    }

}
