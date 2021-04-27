package com.example.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mall.entity.Userinfo;
import com.example.mall.exception.ExceptionEnum;
import com.example.mall.exception.MallException;
import com.example.mall.mapper.UserinfoMapper;
import com.example.mall.service.IUserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.geom.QuadCurve2D;
import java.util.List;

/**
 * @author ZY
 */
@Service
public class UserinfoServiceImpl extends ServiceImpl<UserinfoMapper, Userinfo> implements IUserinfoService {
    @Autowired
    private UserinfoMapper userinfoMapper;

    @Override
    public void addUserinfo(Userinfo userinfo){
        int flag = userinfoMapper.insert(userinfo);
        if (flag <= 0){
            throw new MallException(ExceptionEnum.ADD_USERINFO_ERROR);
        }
    }

    @Override
    public List<Userinfo> getUserinfoAll(Integer userId){
        QueryWrapper<Userinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return userinfoMapper.selectList(queryWrapper);
    }

    @Override
    public Userinfo getUserinfo(Integer id){
        return userinfoMapper.selectById(id);
    }

    @Override
    public void deleteUserinfo(Integer id){
        int flag = userinfoMapper.deleteById(id);
        if (flag <= 0){
            throw new MallException(ExceptionEnum.DELETE_USERINFO_ERROR);
        }
    }

    @Override
    public void updateUserinfo(Userinfo userinfo){
        int flag = userinfoMapper.updateById(userinfo);
        if (flag <= 0){
            throw new MallException(ExceptionEnum.UPDATE_USERINFO_ERROR);
        }
    }
}
