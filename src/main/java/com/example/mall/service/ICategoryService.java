package com.example.mall.service;

import com.example.mall.entity.Category;
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
public interface ICategoryService extends IService<Category> {
    /**
     * 获取类别
     * @return
     */
    List<Category> getCategoryAll();
}
