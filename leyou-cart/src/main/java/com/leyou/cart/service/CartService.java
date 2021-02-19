package com.leyou.cart.service;

import com.leyou.cart.client.GoodsClient;
import com.leyou.cart.interceptor.LoginInterceptor;
import com.leyou.cart.pojo.Cart;
import com.leyou.common.pojo.UserInfo;
import com.leyou.common.utils.JsonUtils;
import com.leyou.item.bo.Sku;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/19 1:50 上午
 */

@Service
public class CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GoodsClient goodsClient;

    private static final String KEY_PREFIX = "user:cart:";

    public void addCart(Cart cart) {

//        获取用户信息 拦截器中已经解析好，不需要再重复解析
        UserInfo userInfo = LoginInterceptor.getUserInfo();

//        查询购物车记录
//        获取hash操作对象
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());

        String key = cart.getSkuId().toString();
        Integer num = cart.getNum();

//        判断当前的商品是否在购物车当中
        if (hashOperations.hasKey(key)) {
            //        在，更新数量
            String cartJson = hashOperations.get(key).toString();
//            解析json字符串
            cart = JsonUtils.parse(cartJson, Cart.class);
//            修改数量
            cart.setNum(cart.getNum() + num);
        } else {
            //        不在，新增购物车
            Sku sku = this.goodsClient.querySkuBySkuId(cart.getSkuId());
            cart.setUserId(userInfo.getId());
            cart.setTitle(sku.getTitle());
            cart.setOwnSpec(sku.getOwnSpec());
            cart.setImage(StringUtils.isAllBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
            cart.setPrice(sku.getPrice());
        }
        //            更新到Redis  put的是序列化的内容
        hashOperations.put(key, JsonUtils.serialize(cart));
    }


    public List<Cart> queryCarts() {

        UserInfo userInfo = LoginInterceptor.getUserInfo();

//        判断用户是否有购物车记录
        if (!this.redisTemplate.hasKey(KEY_PREFIX + userInfo.getId())) {
            return null;
        }

//        获取用户的购物车记录
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());

//        获取购物车Map中所有Cart值的集合
        List<Object> cartsJson = hashOperations.values();

//        如果购物车集合为空，直接返回null
        if (CollectionUtils.isEmpty(cartsJson)) {
            return null;
        }

//        把List<Object>集合转化为List<Cart>集合
        return cartsJson.stream().map(cartJson -> JsonUtils.parse(cartJson.toString(), Cart.class)).collect(Collectors.toList());


    }


}
