package com.example.mall.entity.vo;


import com.example.mall.entity.DiscountProduct;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ZY
 */
@Data
public class DiscountProductVo extends DiscountProduct implements Serializable {

    private String productName;

    private Double productPrice;

    private String productPicture;

    private Integer productId;

    private Double discountPrice;

    private Integer discountStock;

    private Long startTime;

    private Long endTime;
}
