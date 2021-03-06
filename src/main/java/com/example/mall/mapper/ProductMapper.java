package com.example.mall.mapper;

import com.example.mall.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
@Component
public interface ProductMapper extends BaseMapper<Product> {
    /**
     * 查询所有商品id
     * @return
     */
    List<Integer> selectIds();

    /**
     * 查询所有
     * @return
     */
    List<Product> selectAll();

    /**
     * 条件查询
     * @param search
     * @return
     */
    List<Product> getProductBySearch(String search);

    List<Product> getProductByCategory(int categoryID, int currentPage, int pageSize);
}
