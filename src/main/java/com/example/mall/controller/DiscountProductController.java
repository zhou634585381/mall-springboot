package com.example.mall.controller;


import com.example.mall.entity.DiscountProduct;
import com.example.mall.entity.DiscountTime;
import com.example.mall.entity.vo.DiscountProductVo;
import com.example.mall.service.impl.DiscountProductServiceImpl;
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
@RequestMapping("/mall/discount")
@Api(tags = "商品打折")
public class DiscountProductController {
    @Autowired
    private ResultMessage resultMessage;
    @Autowired
    private DiscountProductServiceImpl discountProductService;
    @Autowired
    private RedisTemplate redisTemplate;


    @ApiOperation(value = "根据时间id获取对应时间的秒杀商品列表")
    @GetMapping("/getProduct")
    public ResultMessage getProduct(Integer timeId) {
        List<DiscountProductVo> seckillProductVos = discountProductService.getProduct(timeId);
        resultMessage.success("001", seckillProductVos);
        return resultMessage;
    }


    @ApiOperation(value = "添加打折商品")
    @PostMapping("/addDiscountProduct")
    public ResultMessage addDiscountProduct(@RequestBody DiscountProduct discountProduct) {
        discountProductService.addDiscountProduct(discountProduct);
        resultMessage.success("001", "添加成功");
        return resultMessage;
    }


    @ApiOperation(value = "获取时间段")
    @GetMapping("/time")
    public ResultMessage getTime() {
        List<DiscountTime> discountTimes = discountProductService.getTime();
        resultMessage.success("001", discountTimes);
        return resultMessage;
    }


    @ApiOperation(value = "获取打折商品")
    @GetMapping("/getDiscount/{discountId}")
    public ResultMessage getDiscount(@PathVariable Integer discountId) {
        DiscountProductVo discountProductVo = discountProductService.getDiscount(discountId);
        resultMessage.success("001", discountProductVo);
        return resultMessage;
    }

    @ApiOperation(value = "开始打折")
    @PostMapping("/discountProduct")
    public ResultMessage discountProduct(String discountId, @CookieValue("XM_TOKEN") String cookie) {
        // 先判断cookie是否存在，和redis校验
        Integer userId = (Integer) redisTemplate.opsForHash().get(cookie, "userId");
        discountProductService.discountProduct(discountId, userId);
        resultMessage.success("001", "排队中");
        return resultMessage;
    }
}
