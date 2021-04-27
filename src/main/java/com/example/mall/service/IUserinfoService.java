package com.example.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.entity.Userinfo;

import java.util.List;

/**
 * @author ZY
 */
public interface IUserinfoService extends IService<Userinfo> {

    /**
     * 添加用户信息
     * @param userinfo
     */
    void addUserinfo(Userinfo userinfo);

    /**
     * 查询用户所有信息
     * @param userId
     * @return
     */
    List<Userinfo> getUserinfoAll(Integer userId);

    /**
     * 查询用户信息
     * @param id
     * @return
     */
    Userinfo getUserinfo(Integer id);

    /**
     * 删除用户信息
     * @param id
     */
    void deleteUserinfo(Integer id);

    /**
     * 修改用户信息
     * @param userinfo
     */
    void updateUserinfo(Userinfo userinfo);
}
