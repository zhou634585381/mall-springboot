package com.example.mall.utils;

import com.example.mall.service.impl.OrderServiceImpl;
import com.example.mall.service.impl.DiscountProductServiceImpl;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @author ZY
 */
@Component
public class DiscountOrderQueue {

    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private DiscountProductServiceImpl discountProductService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @RabbitListener(queues = "discount_order")
    public void insertOrder(Map map, Channel channel, Message message){

        // 查看id，保证幂等性
        String correlationId = message.getMessageProperties().getCorrelationId();
        if (!stringRedisTemplate.hasKey(RedisKey.DISCOUNT_RABBITMQ_ID + correlationId)) {
            // redis中存在，表明此条消息已消费，请勿重复消费
            return;
        }
        Integer discountId = (Integer) map.get("discountId");
        Integer userId = (Integer) map.get("userId");
        // 存入redis，因为只需要判断是否存在，因此value为多少无所谓
        stringRedisTemplate.opsForValue().set(RedisKey.DISCOUNT_RABBITMQ_ID + correlationId, "1");
        Long seckillEndTime = discountProductService.getEndTime(discountId);
        // 设置过期时间
        stringRedisTemplate.expire(RedisKey.DISCOUNT_RABBITMQ_ID + correlationId, seckillEndTime - System.currentTimeMillis(), TimeUnit.SECONDS);

        try {
            orderService.addDiscountOrder(discountId, userId);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                stringRedisTemplate.delete(RedisKey.DISCOUNT_RABBITMQ_ID + correlationId);
                // 将该消息放入队列尾部，尝试再次消费
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
