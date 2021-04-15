package com.example.mall.controller;


import com.example.mall.entity.DiscountProduct;
import com.example.mall.entity.DiscountTime;
import com.example.mall.entity.vo.DiscountProductVo;
import com.example.mall.service.impl.DiscountProductServiceImpl;
import com.example.mall.utils.ResultMessage;
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
public class DiscountProductController {
    @Autowired
    private ResultMessage resultMessage;
    @Autowired
    private DiscountProductServiceImpl discountProductService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据时间id获取对应时间的秒杀商品列表
     * @param timeId
     * @return
     */
    @GetMapping("/getProduct")
    public ResultMessage getProduct(Integer timeId) {
        List<DiscountProductVo> seckillProductVos = discountProductService.getProduct(timeId);
        resultMessage.success("001", seckillProductVos);
        return resultMessage;
    }

    /**
     * 添加打折商品
     * @param discountProduct
     * @return
     */
    @PostMapping("/addDiscountProduct")
    public ResultMessage addDiscountProduct(@RequestBody DiscountProduct discountProduct) {
        discountProductService.addDiscountProduct(discountProduct);
        resultMessage.success("001", "添加成功");
        return resultMessage;
    }

    /**
     * 获取时间段
     * @return
     */
    @GetMapping("/time")
    public ResultMessage getTime() {
        List<DiscountTime> discountTimes = discountProductService.getTime();
        resultMessage.success("001", discountTimes);
        return resultMessage;
    }

    /**
     * 获取打折商品
     * @param discountId
     * @return
     */
    @GetMapping("/getDiscount")
    public ResultMessage getDiscount(Integer discountId) {
        DiscountProductVo discountProductVo = discountProductService.getDiscount(discountId);
        resultMessage.success("001", discountProductVo);
        return resultMessage;
    }
    /**
     * 开始打折
     * @param discountId
     * @return
     */
    @PostMapping("/discountProduct")
    public ResultMessage discountProduct(String discountId, @CookieValue("XM_TOKEN") String cookie) {
        // 先判断cookie是否存在，和redis校验
        Integer userId = (Integer) redisTemplate.opsForHash().get(cookie, "userId");
        discountProductService.discountProduct(discountId, userId);
        resultMessage.success("001", "排队中");
        return resultMessage;
    }
}
