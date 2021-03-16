package com.example.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mall.entity.Collect;
import com.example.mall.entity.Product;
import com.example.mall.exception.ExceptionEnum;
import com.example.mall.exception.MallException;
import com.example.mall.mapper.CollectMapper;
import com.example.mall.service.ICollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements ICollectService {

    @Autowired
    CollectMapper collectMapper;

    @Override
    public void addCollect(Integer userId, Integer productId){
        Collect collect = new Collect();
        collect.setUserId(userId);
        collect.setProductId(productId);
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId).eq("product_id",productId);
        Collect one = collectMapper.selectOne(queryWrapper);
        if (one != null){
            throw new MallException(ExceptionEnum.SAVE_COLLECT_REUSE);
        }
        collect.setCollectTime(System.currentTimeMillis());
        int count = collectMapper.insert(collect);
        if (count != 1) {
            throw new MallException(ExceptionEnum.SAVE_COLLECT_ERROR);
        }
    }

    @Override
    public List<Product> getCollect(Integer userId){
        List<Product> list = null;
        try {
            list = collectMapper.getCollect(userId);
            if (ArrayUtils.isEmpty(list.toArray())) {
                throw new MallException(ExceptionEnum.GET_COLLECT_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MallException(ExceptionEnum.GET_COLLECT_ERROR);
        }
        return list;
    }

    @Override
    public void deleteCollect(Integer userId, Integer productId){
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId).eq("product_id",productId);
        try {
            int count = collectMapper.delete(queryWrapper);
            if (count != 1) {
                throw new MallException(ExceptionEnum.DELETE_COLLECT_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MallException(ExceptionEnum.DELETE_COLLECT_ERROR);
        }
    }
}
