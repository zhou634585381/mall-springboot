package com.example.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mall.entity.Product;
import com.example.mall.exception.ExceptionEnum;
import com.example.mall.exception.MallException;
import com.example.mall.mapper.ProductMapper;
import com.example.mall.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> getProductByCategoryId(Integer categoryId){
        List<Product> list = null;
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().orderByDesc("product_sales");
        queryWrapper.eq("category_id",categoryId);
        try {
            list = productMapper.selectList(queryWrapper);
            if (ArrayUtils.isEmpty(list.toArray())) {
                throw new MallException(ExceptionEnum.GET_PRODUCT_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MallException(ExceptionEnum.GET_PRODUCT_ERROR);
        }
        return list;
    }
    @Override
    public List<Product> getHotProduct(){
        List<Product> list = null;
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().orderByDesc("product_sales");
        try {
            list = productMapper.selectList(queryWrapper);
            if (ArrayUtils.isEmpty(list.toArray())) {
                throw new MallException(ExceptionEnum.GET_PRODUCT_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MallException(ExceptionEnum.GET_PRODUCT_ERROR);
        }
        return list;

    }

}
