package com.example.mall.service;

import com.example.mall.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
public interface IProductService extends IService<Product> {
    /**
     * 根据分类获取商品信息
     * @param categoryId
     * @return
     */
    List<Product> getProductByCategoryId(Integer categoryId);

    /**
     * 获热门商品信息
     * @return
     */
    List<Product> getHotProduct();

}
