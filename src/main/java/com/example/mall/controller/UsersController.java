package com.example.mall.controller;


import com.example.mall.entity.Users;
import com.example.mall.service.impl.UsersServiceImpl;
import com.example.mall.utils.BeanUtil;
import com.example.mall.utils.CookieUtil;
import com.example.mall.utils.MD5Util;
import com.example.mall.utils.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
@RestController
@RequestMapping("/mall/users")
@Slf4j
public class UsersController {
    @Autowired
    private UsersServiceImpl usersService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ResultMessage resultMessage;

    @PostMapping("/login")
    public ResultMessage login(@RequestBody Users users, HttpServletRequest request, HttpServletResponse response) {
        users = usersService.login(users);
        String encode = MD5Util.MD5Encode(users.getUserName() + users.getPassword(), "UTF-8");
        encode += "|" + users.getUserId() + "|" + users.getUserName() + "|";
        CookieUtil.setCookie(request, response, "XM_TOKEN", encode, 1800);
        log.info(encode);
        try {
            redisTemplate.opsForHash().putAll(encode, BeanUtil.bean2map(users));
            redisTemplate.expire(encode, 30 * 60, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 将密码设为null,返回给前端
        users.setPassword(null);
        resultMessage.success("001", "登录成功", users);
        return resultMessage;
    }


    @PostMapping("/register")
    public ResultMessage register(@RequestBody Users users) {
        usersService.register(users);
        resultMessage.success("001", "注册成功");
        return resultMessage;
    }

    /**
     * 判断用户名是否已存在
     * @param username
     * @return
     */
    @GetMapping("/username")
    public ResultMessage username(String username) {
        usersService.isUserName(username);
        resultMessage.success("001", "可注册");
        return resultMessage;
    }
}
