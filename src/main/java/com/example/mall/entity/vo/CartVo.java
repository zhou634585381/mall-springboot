package com.example.mall.entity.vo;

import lombok.Data;
/**
 * @author ZY
 */
@Data
public class CartVo {

    private Integer id;

    private Integer productId;

    private String productName;

    private String productImg;

    private Double price;

    private Integer num;

    private Integer maxNum;

    private boolean check;
}
