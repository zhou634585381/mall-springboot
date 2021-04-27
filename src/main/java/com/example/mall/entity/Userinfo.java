package com.example.mall.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author ZY
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Userinfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String userNames;

    private Integer phoneNumber;

    private String userAddress;

}
