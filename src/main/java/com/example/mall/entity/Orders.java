package com.example.mall.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @TableId(value = "id",type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Integer id;

    private String orderId;

    private Integer userId;

    private Integer productId;

    private Integer productNum;

    private Double productPrice;

    private Long orderTime;


}
