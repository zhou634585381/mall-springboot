package com.example.mall.controller;


import com.example.mall.service.impl.CollectServiceImpl;
import com.example.mall.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
@RestController
@RequestMapping("/mall/collect")
public class CollectController {

    @Autowired
    private ResultMessage resultMessage;
    @Autowired
    private CollectServiceImpl collectService;
    /**
     * 将商品收藏
     * @param userId
     * @param productId
     * @return
     */
    @GetMapping("/addCollect")
    public ResultMessage addCollect(Integer userId,Integer productId) {
        collectService.addCollect(userId, productId);
        resultMessage.success("001", "商品收藏成功");
        return resultMessage;
    }
}
