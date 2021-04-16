package com.example.mall.controller;


import com.example.mall.entity.vo.CartVo;
import com.example.mall.entity.vo.OrderVo;
import com.example.mall.service.impl.OrderServiceImpl;
import com.example.mall.utils.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
@RequestMapping("/mall/order")
@Api(tags = "订单")
public class OrderController {
    @Autowired
    private ResultMessage resultMessage;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("/addOrder")
    @ApiOperation(value = "添加订单")
    public ResultMessage addOrder(@RequestBody List<CartVo> cartVoList, @CookieValue("XM_TOKEN") String cookie) {
        // 先判断cookie是否存在，和redis校验
        Integer userId = (Integer) redisTemplate.opsForHash().get(cookie, "userId");
        orderService.addOrder(cartVoList, userId);
        resultMessage.success("001", "下单成功");
        return resultMessage;
    }
    @GetMapping("/getOrder")
    @ApiOperation(value = "获取订单信息")
    public ResultMessage getOrder(@CookieValue("XM_TOKEN") String cookie) {
        // 先判断cookie是否存在，和redis校验
        Integer userId = (Integer) redisTemplate.opsForHash().get(cookie, "userId");
        List<List<OrderVo>> orders = orderService.getOrder(userId);
        resultMessage.success("001", orders);
        return resultMessage;
    }
}
