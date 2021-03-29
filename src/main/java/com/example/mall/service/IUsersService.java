package com.example.mall.service;

import com.example.mall.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
public interface IUsersService extends IService<Users> {
    /**
     * 登录
     * @param users
     * @return
     */
    Users login(Users users);

    /**
     * 注册
     * @param users
     */
    void register(Users users);

    /**
     * 用户名是否存在
     * @param username
     */
    void isUserName(String username);
}
