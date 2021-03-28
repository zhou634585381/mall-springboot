package com.example.mall.mapper;

import com.example.mall.entity.DiscountProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
@Component
public interface DiscountProductMapper extends BaseMapper<DiscountProduct> {
    /**
     * 减少库存
     * @param discountId
     */
    void decrStock(Integer discountId);
}
