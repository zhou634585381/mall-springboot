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
public class SeckillTime implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "time_id", type = IdType.AUTO)
    private Integer timeId;

    private Long startTime;

    private Long endTime;


}
