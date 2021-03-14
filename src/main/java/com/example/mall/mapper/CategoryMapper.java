package com.example.mall.mapper;

import com.example.mall.entity.Carousel;
import com.example.mall.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 查询类别
     *
     * @return
     */
    List<Category> selectAll();
}
