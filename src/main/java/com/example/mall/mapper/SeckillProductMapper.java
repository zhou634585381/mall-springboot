package com.example.mall.mapper;

import com.example.mall.entity.SeckillProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
public interface SeckillProductMapper extends BaseMapper<SeckillProduct> {
    /**
     * 减少库存
     * @param seckillId
     */
    void decrStock(Integer seckillId);
}
