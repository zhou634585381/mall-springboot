package com.example.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mall.entity.ProductPicture;
import com.example.mall.exception.ExceptionEnum;
import com.example.mall.exception.MallException;
import com.example.mall.mapper.ProductPictureMapper;
import com.example.mall.service.IProductPictureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class ProductPictureServiceImpl extends ServiceImpl<ProductPictureMapper, ProductPicture> implements IProductPictureService {
    @Autowired
    private ProductPictureMapper productPictureMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<ProductPicture> getProductPictureByProductId(Integer productId) {
        ProductPicture picture = new ProductPicture();
        picture.setProductId(productId);
        List<ProductPicture> list = null;
        QueryWrapper<ProductPicture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id",productId);
        try {
            list = productPictureMapper.selectList(queryWrapper);
            if (ArrayUtils.isEmpty(list.toArray())) {
                throw new MallException(ExceptionEnum.GET_PRODUCT_PICTURE_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MallException(ExceptionEnum.GET_PRODUCT_PICTURE_ERROR);
        }
        return list;
    }
}
