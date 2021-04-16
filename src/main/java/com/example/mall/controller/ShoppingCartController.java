package com.example.mall.controller;


import com.example.mall.entity.vo.CartVo;
import com.example.mall.service.impl.ShoppingCartServiceImpl;
import com.example.mall.utils.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "购物车")
public class ShoppingCartController {
    @Autowired
    private ResultMessage resultMessage;
    @Autowired
    private ShoppingCartServiceImpl shoppingCartService;


    @GetMapping("/getCartByUserId/{userId}")
    @ApiOperation(value = "获取用户购物车信息")
    public ResultMessage getCartByUserId(@PathVariable Integer userId) {
        List<CartVo> carts = shoppingCartService.getCartByUserId(userId);
        resultMessage.success("001", carts);
        return resultMessage;
    }

    @GetMapping("/addShoppingCart/{productId}/{userId}")
    @ApiOperation(value = "添加购物车")
    public ResultMessage addShoppingCart(@PathVariable Integer productId,@PathVariable Integer userId) {
        CartVo cartVo = shoppingCartService.addShoppingCart(productId, userId);
        if (cartVo != null) {
            resultMessage.success("001", "添加购物车成功", cartVo);
        }else {
            resultMessage.success("002", "该商品已经在购物车，数量+1");
        }
        return resultMessage;
    }

    @GetMapping("/updateCartNum/{productId}/{userId}/{num}")
    @ApiOperation(value = "修改购买商品数量")
    public ResultMessage updateCartNum(@PathVariable Integer productId,@PathVariable Integer userId,@PathVariable Integer num) {
        shoppingCartService.updateCartNum(productId, userId, num);
        resultMessage.success("001", "更新成功");
        return resultMessage;
    }

    @DeleteMapping("/deleteCart/{cartId}/{userId}")
    @ApiOperation(value = "删除购物车")
    public ResultMessage deleteCart(@PathVariable Integer cartId,@PathVariable Integer userId) {
        shoppingCartService.deleteCart(cartId,userId);
        resultMessage.success("001", "删除成功");
        return resultMessage;
    }
}
