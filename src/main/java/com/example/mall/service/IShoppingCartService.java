package com.example.mall.service;

import com.example.mall.entity.ShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.entity.vo.CartVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
public interface IShoppingCartService extends IService<ShoppingCart> {
    /**
     * 获取用户购物车信息
     * @param userId
     * @return
     */
    List<CartVo> getCartByUserId(Integer userId);

    /**
     * 添加购物车商品
     * @param productId
     * @param userId
     * @return
     */
    CartVo addShoppingCart(Integer productId, Integer userId);

    /**
     * 更改购物车某件商品数量
     * @param Id
     * @param userId
     * @param num
     */
    void updateCartNum(Integer Id, Integer userId, Integer num);

    /**
     * 删除购物车商品
     * @param cartId
     * @param userId
     */
    void deleteCart(Integer cartId, Integer userId);
}
