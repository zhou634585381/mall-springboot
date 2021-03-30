package com.example.mall.controller;


import com.example.mall.entity.Product;
import com.example.mall.service.impl.CollectServiceImpl;
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
@RequestMapping("/mall/collect")
@Api(tags = "商品收藏")
public class CollectController {

    @Autowired
    private ResultMessage resultMessage;
    @Autowired
    private CollectServiceImpl collectService;

    @ApiOperation(value = "添加收藏商品")
    @GetMapping("/addCollect")
    public ResultMessage addCollect(Integer userId,Integer productId) {
        collectService.addCollect(userId, productId);
        resultMessage.success("001", "商品收藏成功");
        return resultMessage;
    }

    @ApiOperation(value = "获取用户收藏商品信息")
    @GetMapping("/getCollect/")
    public ResultMessage getCollect(Integer userId) {
        List<Product> collects = collectService.getCollect(userId);
        resultMessage.success("001", collects);
        return resultMessage;
    }

    @ApiOperation(value = "删除收藏商品")
    @DeleteMapping("/deleteCollect")
    public ResultMessage deleteCollect(Integer productId,Integer userId) {
        collectService.deleteCollect(userId, productId);
        resultMessage.success("001", "删除收藏成功");
        return resultMessage;
    }
}
