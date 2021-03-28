package com.example.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DiscountProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "seckill_id", type = IdType.AUTO)
    private Integer discountId;

    private Integer productId;

    private Double discountPrice;

    private Integer discountStock;

    private Integer timeId;


}
