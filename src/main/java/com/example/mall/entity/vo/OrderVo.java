package com.example.mall.entity.vo;

import com.example.mall.entity.Order;
import lombok.Data;

/**
 * @author ZY
 */
@Data
public class OrderVo extends Order {

    private String productName;

    private String productPicture;

}
