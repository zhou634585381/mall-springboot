package com.example.mall.mapper;

import com.example.mall.entity.DiscountProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mall.entity.vo.DiscountProductVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
public interface DiscountProductMapper extends BaseMapper<DiscountProduct> {
    /**
     * 减少库存
     * @param discountId
     */
    void decrStock(Integer discountId);

    /**
     * 根据时间段查询商品信息
     * @param timeId
     * @param time
     * @return
     */
    List<DiscountProductVo> getDiscountProductVos(Integer timeId, Long time);

    /**
     * 根据打折商品id查询商品信息
     * @param discountId
     * @return
     */
    DiscountProductVo getDiscount(String discountId);

    @Delete("delete from discount_product")
    void deleteAll();

    Integer getId(Integer productId);
}
