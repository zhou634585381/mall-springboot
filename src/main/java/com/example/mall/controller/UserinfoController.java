package com.example.mall.controller;


import com.example.mall.entity.Userinfo;
import com.example.mall.service.impl.UserinfoServiceImpl;
import com.example.mall.utils.ResultMessage;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ZY
 */
@RestController
@RequestMapping("/mall/userinfo")
@Slf4j
@Api(tags = "用户信息")
public class UserinfoController {
    @Autowired
    private UserinfoServiceImpl userinfoService;
    @Autowired
    private ResultMessage resultMessage;

    @PostMapping("/add")
    public ResultMessage addUserinfo(@RequestBody Userinfo userinfo){
        userinfoService.addUserinfo(userinfo);
        resultMessage.success("001", "添加成功");
        return resultMessage;
    }

    @GetMapping("/getUserinfoAll/{userId}")
    public ResultMessage getUserinfoAll(@PathVariable Integer userId){
        List<Userinfo> userinfoList = userinfoService.getUserinfoAll(userId);
        resultMessage.success("001", userinfoList);
        return resultMessage;
    }

    @GetMapping("/getUserinfo/{id}")
    public ResultMessage getUserinfo(@PathVariable Integer id){
        resultMessage.success("001",userinfoService.getUserinfo(id));
        return resultMessage;
    }

    @PostMapping("/updateUserinfo")
    public ResultMessage updateUserinfo(@RequestBody Userinfo userinfo){
        userinfoService.updateUserinfo(userinfo);
        resultMessage.success("001", "修改成功");
        return resultMessage;
    }

    @DeleteMapping("/deleteUserinfo/{id}")
    public ResultMessage deleteUserinfo(@PathVariable Integer id){
        userinfoService.deleteUserinfo(id);
        resultMessage.success("001", "删除成功");
        return resultMessage;
    }
}
