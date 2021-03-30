package com.example.mall.controller;


import com.example.mall.entity.vo.CartVo;
import com.example.mall.service.impl.ShoppingCartServiceImpl;
import com.example.mall.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
@RestController
@RequestMapping("/mall/shopping-cart")
public class ShoppingCartController {
    @Autowired
    private ResultMessage resultMessage;
    @Autowired
    private ShoppingCartServiceImpl shoppingCartService;


    @GetMapping("/getCartByUserId")
    public ResultMessage getCartByUserId(Integer userId) {
        List<CartVo> carts = shoppingCartService.getCartByUserId(userId);
        resultMessage.success("001", carts);
        return resultMessage;
    }

    @GetMapping("/addShoppingCart")
    public ResultMessage addShoppingCart(Integer productId,Integer userId) {
        CartVo cartVo = shoppingCartService.addShoppingCart(productId, userId);
        if (cartVo != null) {
            resultMessage.success("001", "添加购物车成功", cartVo);
        }else {
            resultMessage.success("002", "该商品已经在购物车，数量+1");
        }
        return resultMessage;
    }

    @PutMapping("/updateCartNum")
    public ResultMessage updateCartNum(Integer productId, Integer userId, Integer num) {
        shoppingCartService.updateCartNum(productId, userId, num);
        resultMessage.success("001", "更新成功");
        return resultMessage;
    }

    @DeleteMapping("/deleteCart")
    public ResultMessage deleteCart(Integer Id) {
        shoppingCartService.deleteCart(Id);
        resultMessage.success("001", "删除成功");
        return resultMessage;
    }
}
