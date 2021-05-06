package com.example.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.example.mall.entity.Product;
import com.example.mall.exception.ExceptionEnum;
import com.example.mall.exception.MallException;
import com.example.mall.mapper.ProductMapper;
import com.example.mall.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

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

    private final static Integer SELECT_ALL = 0;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
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
    @Transactional(rollbackFor = RuntimeException.class)
    public List<Product> getHotProduct(){
        List<Product> list = null;
        PageHelper.startPage(0, 8);
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
    @Override
    public void addProduct(Product product){
        int flag = productMapper.insert(product);
        if(flag <= 0){
            throw new MallException(ExceptionEnum.ADD_PRODUCT_ERROR);
        }

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Product getProductById(Integer productId){
        Product product = null;
        try {
            product = productMapper.selectById(productId);
            if (product == null) {
                throw new MallException(ExceptionEnum.GET_PRODUCT_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MallException(ExceptionEnum.GET_PRODUCT_ERROR);
        }
        return product;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public PageInfo<Product> getProductByPage(Integer pageNum, Integer pageSize,Integer categoryId){
//        Page<Product> productPage = new Page<>(pageNum,pageSize);
//        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
//        queryWrapper.select().orderByDesc("product_sales");
//        if (!SELECT_ALL.equals(categoryId)) {
//            queryWrapper.eq("category_id", categoryId);
//        }
//        IPage<Product> iPage = productMapper.selectPage(productPage,queryWrapper);
//        return iPage;
        List<Product> list = null;
        PageHelper.startPage(pageNum,pageSize);
        // 为0，代表分页查询所有商品
        if (categoryId.equals(SELECT_ALL)) {
            list = productMapper.selectAll();
        }else {
            // 分类分页查询商品
            Product product = new Product();
            product.setCategoryId(categoryId);
            QueryWrapper<Product>queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("category_id",categoryId);
            list = productMapper.selectList(queryWrapper);
        }
        PageInfo<Product> pageInfo = new PageInfo<Product>(list);
        return pageInfo;
    }
    @Override
    public List<Product> getProductBySearch(String search){
        return productMapper.getProductBySearch(search);
    }
    @Override
    public List<Product> getProductByCategory(int categoryID,int currentPage,int pageSize) {
        return productMapper.getProductByCategory(categoryID,currentPage,pageSize);
    }
}
