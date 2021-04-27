package com.example.mall.controller;


import com.example.mall.entity.Users;
import com.example.mall.service.impl.UsersServiceImpl;
import com.example.mall.utils.BeanUtil;
import com.example.mall.utils.CookieUtil;
import com.example.mall.utils.MD5Util;
import com.example.mall.utils.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
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
@Api(tags = "用户登录注册")
public class UsersController {
    @Autowired
    private UsersServiceImpl usersService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ResultMessage resultMessage;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public ResultMessage login(@RequestBody Users users, HttpServletRequest request, HttpServletResponse response) {
        users = usersService.login(users);
        // 添加cookie，设置唯一认证
        String encode = MD5Util.MD5Encode(users.getUserName() + users.getPassword(), "UTF-8");
        encode += "|" + users.getUserId() + "|" + users.getUserName() + "|";
        CookieUtil.setCookie(request, response, "XM_TOKEN", encode, 1800);
        log.info(encode);
        // 将encode放入redis中，用于认证
        try {
            redisTemplate.opsForHash().putAll(encode, BeanUtil.bean2map(users));
            redisTemplate.expire(encode, 30 * 60 * 60, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 将密码设为null,返回给前端
        users.setPassword(null);
        resultMessage.success("001", "登录成功", users);
        return resultMessage;
    }


    @PostMapping("/register")
    @ApiOperation(value = "注册")
    public ResultMessage register(@RequestBody Users users) {
        usersService.register(users);
        resultMessage.success("001", "注册成功");
        return resultMessage;
    }

    @GetMapping("/username")
    @ApiOperation(value = "判断用户名是否已存在")
    public ResultMessage username(String username) {
        usersService.isUserName(username);
        resultMessage.success("001", "可注册");
        return resultMessage;
    }

    @ApiOperation(value = "根据token获取用户信息")
    @GetMapping("/token")
    public ResultMessage token(@CookieValue("XM_TOKEN") String token, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = redisTemplate.opsForHash().entries(token);
        // 可能map为空 ， 即redis中时间已过期，但是cookie还存在。
        // 这个时候应该删除cookie，让用户重新登录
        if (map.isEmpty()) {
            CookieUtil.delCookie(request, token);
            resultMessage.fail("002", "账号过期,请重新登录");
            return resultMessage;
        }
        // 设置过期时间
        redisTemplate.expire(token, 30 * 60 * 60, TimeUnit.SECONDS);
        Users users = BeanUtil.map2bean(map, Users.class);
        users.setPassword(null);
        resultMessage.success("001", users);
        return resultMessage;
    }
}
