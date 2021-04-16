package com.example.mall.service.impl;

import com.example.mall.entity.Category;
import com.example.mall.exception.ExceptionEnum;
import com.example.mall.exception.MallException;
import com.example.mall.mapper.CategoryMapper;
import com.example.mall.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<Category> getCategoryAll(){
        List<Category> categories = null;
        try {
            categories = categoryMapper.selectAll();
            if (categories == null) {
                throw new MallException(ExceptionEnum.GET_CATEGORY_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MallException(ExceptionEnum.GET_CATEGORY_ERROR);
        }
        return categories;
        }
}
