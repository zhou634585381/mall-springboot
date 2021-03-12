package com.example.mall.entity;

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
public class ProductPicture implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer productId;

    private String productPicture;

    private String intro;


}
