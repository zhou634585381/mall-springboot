package com.example.mall.service;

import com.example.mall.entity.ProductPicture;
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
public interface IProductPictureService extends IService<ProductPicture> {
    /**
     * 获取商品图片地址
     * @param productId
     * @return
     */
    List<ProductPicture> getProductPictureByProductId(Integer productId);
}
