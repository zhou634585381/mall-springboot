package com.example.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mall.entity.DiscountProduct;
import com.example.mall.entity.DiscountTime;
import com.example.mall.entity.Product;
import com.example.mall.entity.ShoppingCart;
import com.example.mall.entity.vo.CartVo;
import com.example.mall.entity.vo.DiscountProductVo;
import com.example.mall.exception.ExceptionEnum;
import com.example.mall.exception.MallException;
import com.example.mall.mapper.DiscountProductMapper;
import com.example.mall.mapper.DiscountTimeMapper;
import com.example.mall.mapper.ProductMapper;
import com.example.mall.mapper.ShoppingCartMapper;
import com.example.mall.service.IDiscountProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mall.utils.BeanUtil;
import com.example.mall.utils.RedisKey;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
@Service
public class DiscountProductServiceImpl extends ServiceImpl<DiscountProductMapper, DiscountProduct> implements IDiscountProductService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private DiscountTimeMapper discountTimeMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private DiscountProductMapper discountProductMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private HashMap<String, Boolean> localOverMap = new HashMap<>();
    @Override
    public List<DiscountProductVo> getProduct(Integer timeId){
        //查看Redis缓存列表，是否存在商品信息
        List<DiscountProductVo> discountProductVoList = redisTemplate.opsForList().range(RedisKey.DISCOUNT_PRODUCT_LIST + timeId,0,-1);
        if (ArrayUtils.isNotEmpty(discountProductVoList.toArray())){
        return discountProductVoList;
       }
        discountProductVoList = discountProductMapper.getDiscountProductVos(timeId,System.currentTimeMillis());
        if (ArrayUtils.isNotEmpty(discountProductVoList.toArray())) {
            redisTemplate.opsForList().leftPushAll(RedisKey.DISCOUNT_PRODUCT_LIST + timeId, discountProductVoList);
            // 设置过期时间
            long l = discountProductVoList.get(0).getEndTime() - System.currentTimeMillis();
            redisTemplate.expire(RedisKey.DISCOUNT_PRODUCT_LIST + timeId, l, TimeUnit.MILLISECONDS);
        }else {
            // 秒杀商品过期或不存在
            throw new MallException(ExceptionEnum.GET_DISCOUNT_NOT_FOUND);
        }
        return discountProductVoList;
    }

    @Override
    public void addDiscountProduct(DiscountProduct discountProduct){
        Date time = getDate();
        Long startTime = time.getTime()/1000*1000 + 100 * 60 * 60;
        Long endTime = startTime + 10000 * 60 * 60;
        DiscountTime discountTime = new DiscountTime();
        discountTime.setStartTime(startTime);
        discountTime.setEndTime(endTime);
        QueryWrapper<DiscountTime> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("time_id",discountProduct.getTimeId());
        DiscountTime one = discountTimeMapper.selectOne(queryWrapper);
        if (one == null) {
            discountTimeMapper.insert(discountTime);
            discountProduct.setTimeId(discountTime.getTimeId());
        }else {
            discountProduct.setTimeId(one.getTimeId());
        }
        discountProductMapper.insert(discountProduct);

    }

    private Date getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    @Override
    public List<DiscountTime> getTime(){
        // 获取当前时间及往后7个时间段, 总共8个
        Date time = getDate();
        List<DiscountTime> discountTimes = discountTimeMapper.getTime(time.getTime()/1000*1000);
        return discountTimes;
    }

    @Override
    public DiscountProductVo getDiscount(String discountId){
        Map map = redisTemplate.opsForHash().entries(RedisKey.DISCOUNT_PRODUCT + discountId);
        if (!map.isEmpty()){
            map.size();
            DiscountProductVo discountProductVo = null;
            try {
                discountProductVo = BeanUtil.map2bean(map, DiscountProductVo.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return discountProductVo;
        }
        DiscountProductVo discountProductVo = discountProductMapper.getDiscount(discountId);
        if (discountProductVo != null) {
            try {
                redisTemplate.opsForHash().putAll(RedisKey.DISCOUNT_PRODUCT + discountId, BeanUtil.bean2map(discountProductVo));
                redisTemplate.expire(RedisKey.DISCOUNT_PRODUCT + discountId, discountProductVo.getEndTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                // 将库存单独存入一个key中
                if (stringRedisTemplate.opsForValue().get(RedisKey.DISCOUNT_PRODUCT_STOCK + discountId) == null) {
                    stringRedisTemplate.opsForValue().set(RedisKey.DISCOUNT_PRODUCT_STOCK + discountId, discountProductVo.getDiscountStock() + "",discountProductVo.getEndTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return discountProductVo;
        }
        return null;
    }


    @Override
    public void discountProduct(String discountId,Integer userId){
        if (localOverMap.get(discountId) != null && localOverMap.get(discountId)) {
            // 售空
            throw new MallException(ExceptionEnum.GET_DISCOUNT_IS_OVER);
        }
        // 判断秒杀是否开始, 防止路径暴露被刷
        Map m = redisTemplate.opsForHash().entries(RedisKey.DISCOUNT_PRODUCT + discountId);
        if (!m.isEmpty()) {
            DiscountProductVo discountProductVo = null;
            try {
                discountProductVo = BeanUtil.map2bean(m, DiscountProductVo.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 秒杀开始时间
            Long startTime = discountProductVo.getStartTime();

            if (startTime > System.currentTimeMillis()) {
                throw new MallException(ExceptionEnum.GET_DISCOUNT_IS_NOT_START);
            }
        }
        // 判断是否已经秒杀到了，避免一个账户秒杀多个商品
        List<String> list = redisTemplate.opsForList().range(RedisKey.DISCOUNT_PRODUCT_USER_LIST + discountId, 0, -1);
        if (list.contains(String.valueOf(userId))) {
            throw new MallException(ExceptionEnum.GET_DISCOUNT_IS_REUSE);
        }
        // 预减库存：从缓存中减去库存
        // 利用redis中的方法，减去库存，返回值为减去1之后的值
//        if (stringRedisTemplate.opsForValue().decrement(RedisKey.DISCOUNT_PRODUCT_STOCK + discountId) < 0) {
//            // 设置内存标记
//            localOverMap.put(discountId, true);
//            // 秒杀完成，库存为空
//            throw new MallException(ExceptionEnum.GET_DISCOUNT_IS_OVER);
//        }
        // 使用RabbitMQ异步传输
        mqSend(discountId, userId);
    }

    private void mqSend(String discountId, Integer userId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("discountId", discountId);
        map.put("userId", userId.toString());
        // 设置ID，保证消息队列幂等性
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(discountId + ":" + userId);
        try {
            rabbitTemplate.convertAndSend("discount_order", map, correlationData);
        } catch (AmqpException e) {
            // 发送消息失败
            e.printStackTrace();
            stringRedisTemplate.opsForValue().increment(RedisKey.DISCOUNT_PRODUCT_STOCK + discountId);
        }
    }
    public Long getEndTime(String discountId) {
        DiscountProductVo discountProductVo = discountProductMapper.getDiscount(discountId);
        return discountTimeMapper.getEndTime(discountProductVo.getTimeId());
    }
}
