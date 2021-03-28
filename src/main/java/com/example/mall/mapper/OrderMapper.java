package com.example.mall.mapper;

import com.example.mall.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mall.entity.vo.OrderVo;
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
public interface OrderMapper extends BaseMapper<Order> {
    /**
     * 获取订单用户id
     * @param userId
     * @return
     */
    List<OrderVo> getUserId(Integer userId);
}
