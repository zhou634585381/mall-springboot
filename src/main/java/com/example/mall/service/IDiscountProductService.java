package com.example.mall.service;

import com.example.mall.entity.DiscountProduct;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.entity.DiscountTime;
import com.example.mall.entity.vo.CartVo;
import com.example.mall.entity.vo.DiscountProductVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
public interface IDiscountProductService extends IService<DiscountProduct> {

    /**
     * 根据打折时间获取打折商品信息
     * @param timeId
     * @return
     */
    List<DiscountProductVo> getProduct(Integer timeId);

    /**
     * 添加打折商品
     * @param discountProduct
     */
    void addDiscountProduct(DiscountProduct discountProduct);

    /**
     * 获取时间段
     * @return
     */
    List<DiscountTime> getTime();

    /**
     * 根据id获取打折商品信息
     * @param discountId
     * @return
     */
    DiscountProductVo getDiscount(String discountId);

    /**
     * 开始打折
     * @param discountId
     * @param userId
     */
    void discountProduct(String discountId,Integer userId);

}
