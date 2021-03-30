package com.example.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mall.entity.Users;
import com.example.mall.exception.ExceptionEnum;
import com.example.mall.exception.MallException;
import com.example.mall.mapper.UsersMapper;
import com.example.mall.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mall.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {
    @Autowired
    private UsersMapper usersMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Users login(Users users) {
        users.setPassword(MD5Util.MD5Encode(users.getPassword() + "", "UTF-8"));
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",users.getUserName()).eq("password",users.getPassword());
        Users one = usersMapper.selectOne(queryWrapper);
        if (one == null) {
            throw new MallException(ExceptionEnum.GET_USER_NOT_FOUND);
        }
        return one;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void register(Users users) {
        // 先去看看用户名是否重复
        if (getUsersByName(users.getUserName()) != null) {
            // 用户名已存在
            throw new MallException(ExceptionEnum.SAVE_USER_REUSE);
        }
        // 使用md5对密码进行加密
        users.setPassword(MD5Util.MD5Encode(users.getPassword() + "", "UTF-8"));
        // 存入数据库
        try {
            usersMapper.insert(users);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MallException(ExceptionEnum.SAVE_USER_ERROR);
        }
    }

    private Users getUsersByName(String userName) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        List<Users> users = this.usersMapper.selectList(queryWrapper);
        if (users == null || users.size() <= 0) {
            return null;
        }
        return users.get(0);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void isUserName(String username) {
        // 先去看看用户名是否重复
        if (getUsersByName(username) != null) {
            // 用户名已存在
            throw new MallException(ExceptionEnum.SAVE_USER_REUSE);
        }
    }
}
