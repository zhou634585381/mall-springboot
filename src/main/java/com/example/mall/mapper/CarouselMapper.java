package com.example.mall.mapper;

import com.example.mall.entity.Carousel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
@Component
public interface CarouselMapper extends BaseMapper<Carousel> {
    /**
     * 查询轮播图
     *
     * @return
     */
    List<Carousel> selectAll();
}
