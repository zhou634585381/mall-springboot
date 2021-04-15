package com.example.mall.mapper;

import com.example.mall.entity.DiscountTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
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
public interface DiscountTimeMapper extends BaseMapper<DiscountTime> {

    /**
     * 获取打折时间信息
     * @param time
     * @return
     */
    List<DiscountTime> getTime(Long time);

    /**
     * 获取结束时间
     * @param timeId
     * @return
     */
    Long getEndTime(Integer timeId);

    @Delete("delete from discount_time")
    void deleteAll();
}
