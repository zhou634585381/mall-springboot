package com.example.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mall.entity.Order;
import com.example.mall.entity.Product;
import com.example.mall.entity.DiscountProduct;
import com.example.mall.entity.ShoppingCart;
import com.example.mall.entity.vo.CartVo;
import com.example.mall.entity.vo.OrderVo;
import com.example.mall.exception.ExceptionEnum;
import com.example.mall.exception.MallException;
import com.example.mall.mapper.OrderMapper;
import com.example.mall.mapper.ProductMapper;
import com.example.mall.mapper.DiscountProductMapper;
import com.example.mall.mapper.ShoppingCartMapper;
import com.example.mall.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mall.utils.IdWorker;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DiscountProductMapper seckillProductMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    private final static String SECKILL_PRODUCT_USER_LIST = "seckill:product:user:list";

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void addOrder(List<CartVo> cartVoList, Integer userId){
        String orderId = idWorker.nextId() + "";
        Long time = System.currentTimeMillis();
        for (CartVo cartVo : cartVoList){
            Order order = new Order();
            order.setOrderId(orderId);
            order.setOrderTime(time);
            order.setProductNum(cartVo.getNum());
            order.setProductId(cartVo.getProductId());
            order.setProductPrice(cartVo.getPrice());
            order.setUserId(userId);
            try {
                orderMapper.insert(order);
            }catch (Exception e){
                e.printStackTrace();
                throw new MallException(ExceptionEnum.ADD_ORDER_ERROR);
            }
            // 减去商品库存,记录卖出商品数量
            // TODO : 此处会产生多线程问题，即不同用户同时对这个商品操作，此时会导致数量不一致问题
            Product product = productMapper.selectById(cartVo.getProductId());
            product.setProductNum(product.getProductNum() - cartVo.getNum());
            product.setProductSales(product.getProductSales() + cartVo.getNum());
            QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("product_id",cartVo.getProductId());
            productMapper.update(product,queryWrapper);
        }
        QueryWrapper<ShoppingCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        try {
            int count = shoppingCartMapper.delete(queryWrapper);
            if (count == 0) {
                throw new MallException(ExceptionEnum.ADD_ORDER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MallException(ExceptionEnum.ADD_ORDER_ERROR);
        }
    }
    @Override
    public List<List<OrderVo>> getOrder(Integer userId) {
        List<OrderVo> list = null;
        ArrayList<List<OrderVo>> ret = new ArrayList<>();
        try {
            list = orderMapper.getUserId(userId);
            if (ArrayUtils.isEmpty(list.toArray())) {
                throw new MallException(ExceptionEnum.GET_ORDER_NOT_FOUND);
            }
            // 将同一个订单放在一组
            Map<String, List<OrderVo>> collect = list.stream().collect(Collectors.groupingBy(Order::getOrderId));
            Collection<List<OrderVo>> values = collect.values();
            ret.addAll(values);
        } catch (MallException e) {
            e.printStackTrace();
            throw new MallException(ExceptionEnum.GET_ORDER_ERROR);
        }
        return ret;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void addSeckillOrder(String discountId, String userId){
        // 订单id
        String orderId = idWorker.nextId() + "";
        // 商品id
        DiscountProduct seckillProduct = new DiscountProduct();
        seckillProduct.setDiscountId(Integer.parseInt(discountId));
        QueryWrapper<DiscountProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("seckill_id",discountId);
        DiscountProduct one = seckillProductMapper.selectOne(queryWrapper);
        Integer productId = one.getProductId();
        // 秒杀价格
        Double price = one.getDiscountPrice();

        // 订单封装
        Order order = new Order();
        order.setOrderId(orderId);
        order.setProductId(productId);
        order.setProductNum(1);
        order.setUserId(Integer.parseInt(userId));
        order.setOrderTime(System.currentTimeMillis());
        order.setProductPrice(price);

        try {
            orderMapper.insert(order);
            // 减库存
            seckillProductMapper.decrStock(one.getDiscountId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MallException(ExceptionEnum.ADD_ORDER_ERROR);
        }

        // 订单创建成功, 将用户写入redis, 防止多次抢购
        redisTemplate.opsForList().leftPush(SECKILL_PRODUCT_USER_LIST + discountId, userId);

    }
}
