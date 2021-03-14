package com.example.mall.service;

import com.example.mall.entity.Collect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.entity.Product;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
public interface ICollectService extends IService<Collect> {
    /**
     * 添加收藏
     * @param userId
     * @param productId
     */
    void addCollect(Integer userId, Integer productId);

    /**
     * 查询收藏商品列表
     * @param userId
     * @return
     */
    List<Product> getCollect(Integer userId);

    /**
     * 删除收藏
     * @param userId
     * @param productId
     */
    void deleteCollect(Integer userId, Integer productId);
}
