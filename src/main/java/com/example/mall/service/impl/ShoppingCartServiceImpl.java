package com.example.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mall.entity.Product;
import com.example.mall.entity.ShoppingCart;
import com.example.mall.entity.vo.CartVo;
import com.example.mall.exception.ExceptionEnum;
import com.example.mall.exception.MallException;
import com.example.mall.mapper.ProductMapper;
import com.example.mall.mapper.ShoppingCartMapper;
import com.example.mall.service.IShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QDecoderStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements IShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<CartVo> getCartByUserId(Integer userId){
        List<ShoppingCart> list = null;
        List<CartVo> cartVoList = new ArrayList<>();
        QueryWrapper<ShoppingCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        try {
            list = shoppingCartMapper.selectList(queryWrapper);
            for (ShoppingCart c : list) {
                cartVoList.add(getCartVo(c));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MallException(ExceptionEnum.GET_CART_ERROR);
        }
        return cartVoList;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CartVo addShoppingCart(Integer productId, Integer userId){
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setProductId(productId);
        shoppingCart.setUserId(userId);
        QueryWrapper<ShoppingCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id",productId).eq("user_id",userId);
        // 查看数据库是否已存在,存在数量直接加1
        ShoppingCart cart = shoppingCartMapper.selectOne(queryWrapper);
        if (cart != null) {
            // 还要判断是否达到该商品规定上限
            if (cart.getNum() >= 5) {
                throw new MallException(ExceptionEnum.ADD_CART_NUM_UPPER);
            }
            cart.setNum(cart.getNum() + 1);
            shoppingCartMapper.update(cart,queryWrapper);
            return null;
        }else {
            // 不存在
            shoppingCart.setNum(1);
            shoppingCartMapper.insert(shoppingCart);
            return getCartVo(shoppingCart);
        }
    }

    private CartVo getCartVo(ShoppingCart cart) {
        // 获取商品，用于封装下面的类
        Product product = productMapper.selectById(cart.getProductId());
        // 返回购物车详情
        CartVo cartVo = new CartVo();
        cartVo.setId(cart.getId());
        cartVo.setProductId(cart.getProductId());
        cartVo.setProductName(product.getProductName());
        cartVo.setProductImg(product.getProductPicture());
        cartVo.setPrice(product.getProductSellingPrice());
        cartVo.setNum(cart.getNum());
        cartVo.setMaxNum(5);
        cartVo.setCheck(false);
        return cartVo;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateCartNum(Integer Id, Integer userId, Integer num){
        ShoppingCart cart = new ShoppingCart();
        QueryWrapper<ShoppingCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",Id).eq("user_id",userId);
        cart.setNum(num);
        try {
            shoppingCartMapper.update(cart,queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MallException(ExceptionEnum.UPDATE_CART_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteCart(Integer cartId, Integer userId){
        try {
            QueryWrapper<ShoppingCart> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",cartId).eq("user_id",userId);
            shoppingCartMapper.delete(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MallException(ExceptionEnum.DELETE_CART_ERROR);
        }
    }
}
