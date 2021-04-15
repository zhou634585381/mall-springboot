package com.example.mall.utils;

import com.example.mall.entity.DiscountProduct;
import com.example.mall.entity.DiscountTime;
import com.example.mall.mapper.DiscountProductMapper;
import com.example.mall.mapper.DiscountTimeMapper;
import com.example.mall.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * @author ZY
 */
@Component
public class DiscountTask {

    @Autowired
    private DiscountTimeMapper discountTimeMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private DiscountProductMapper discountProductMapper;

    @Scheduled(cron = "0 0 15 * * ?")
    public void execute() {
        // 获取商品设为秒杀商品
        List<Integer> productIds = productMapper.selectIds();
        Date time = getDate();
        discountTimeMapper.deleteAll();
        discountProductMapper.deleteAll();
        for (int i = 1; i < 24; i = i + 2) {

            // 插入时间
            long startTime = time.getTime()/1000*1000 + 1000 * 60 * 60 * i;
            long endTime = startTime + 1000 * 60 * 60;
            DiscountTime discountTime = new DiscountTime();
            discountTime.setStartTime(startTime);
            discountTime.setEndTime(endTime);
            discountTimeMapper.insert(discountTime);
            // 随机选15个商品id
            HashSet<Integer> set = new HashSet<>();
            while (set.size() < 15) {
                Random random = new Random();
                int nextInt = random.nextInt(productIds.size());
                set.add(productIds.get(nextInt));
            }
            ArrayList<Integer> integers = new ArrayList<>(set);
            // 添加秒杀商品
            for (int j = 0; j < 15; j++) {
                DiscountProduct discountProduct = new DiscountProduct();
                discountProduct.setDiscountPrice(1000.0);
                discountProduct.setDiscountStock(100);
                discountProduct.setProductId(integers.get(j));
                discountProduct.setTimeId(discountProduct.getTimeId());
                discountProductMapper.insert(discountProduct);
            }
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成---------------------");

        }
        System.out.println("一次添加ok-------------------------------------------");

    }

    private Date getDate() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        return ca.getTime();
    }

}
