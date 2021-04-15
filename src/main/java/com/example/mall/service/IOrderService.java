package com.example.mall.service;

import com.example.mall.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.entity.vo.CartVo;
import com.example.mall.entity.vo.OrderVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
public interface IOrderService extends IService<Order> {
    /**
     * 添加订单
     * @param cartVoList
     * @param userId
     */
    void addOrder(List<CartVo> cartVoList, Integer userId);

    /**
     * 获取订单信息
     * @param userId
     * @return
     */
    List<List<OrderVo>> getOrder(Integer userId);

    /**
     * 添加折扣商品订单
     * @param discountId
     * @param userId
     */
    void addDiscountOrder(Integer discountId, Integer userId);
}
