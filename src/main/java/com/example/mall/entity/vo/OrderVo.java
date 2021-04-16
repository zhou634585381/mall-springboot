package com.example.mall.entity.vo;

import com.example.mall.entity.Orders;
import lombok.Data;

/**
 * @author ZY
 */
@Data
public class OrderVo extends Orders {

    private String orderId;

    private Integer userId;

    private Integer productId;

    private Integer productNum;

    private Double productPrice;

    private Long orderTime;

    private String productName;

    private String productPicture;

}
