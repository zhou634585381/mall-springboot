package com.example.mall.mapper;

import com.example.mall.entity.Collect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mall.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
public interface CollectMapper extends BaseMapper<Collect> {
    /**
     * 查询收藏商品信息
     * @param userId
     * @return
     */
    List<Product> getCollect(@Param("userId") Integer userId);
}
