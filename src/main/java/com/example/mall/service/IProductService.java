package com.example.mall.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.mall.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

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

    /**
     * 根据商品id获取商品信息
     * @param productId
     * @return
     */
    Product getProductById(Integer productId);

    /**
     * 分页获取商品信息
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    PageInfo<Product> getProductByPage(Integer pageNum, Integer pageSize, Integer categoryId);

    /**
     * 搜索查询
     * @param search
     * @return
     */
    List<Product> getProductBySearch(String search);

    List<Product> getProductByCategory(int categoryID,int currentPage,int pageSize);
}
