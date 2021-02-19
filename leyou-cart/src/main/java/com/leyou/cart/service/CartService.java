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

        System.out.println();
        System.out.println("addCart()方法中的传入的cart对象信息：" + cart.toString());

//        获取用户信息 拦截器中已经解析好，不需要再重复解析
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        System.out.println("拦截器中userInfo：" + userInfo.toString());

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
            System.out.println("即将查询数据库");
            System.out.println(cart.getSkuId());
            //问题就出在这里 这里的SkuId应该就是表tb_sku中的id
            Sku sku = this.goodsClient.querySkuBySkuId(cart.getSkuId());

            System.out.println("数据库中的sku: " + sku.toString());

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

    //更新购物车中商品的数量
    public void updateNum(Cart cart) {
//        获取登录的用户是谁
        UserInfo userInfo = LoginInterceptor.getUserInfo();
//        判断用户是否有购物车记录 没有就直接返回
        if (!this.redisTemplate.hasKey(KEY_PREFIX + userInfo.getId())) {
            return;
        }
//        获取更新后的购物车中商品的数量 下面会给cart重新赋值
        Integer num = cart.getNum();
//        获取用户的购物车记录
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());
//        第一个toString()是 BoundHashOperations中的第一个参数的类型是String，第二个toString()是将结果转化为String类型
        String cartJson = hashOperations.get(cart.getSkuId().toString()).toString();
//        反序列化 反序列化之后才能使用getter setter方法 cart被重新赋值了 cart变为Redis中的数据
        cart = JsonUtils.parse(cartJson, Cart.class);
//        更新数量 数量在传过来的cart中
        cart.setNum(num);
//        Redis存储 Redis中存储的是序列化的数据 就是字符串
        hashOperations.put(cart.getSkuId().toString(), JsonUtils.serialize(cart));
    }

    //    从购物车中删除商品
    public void deleteCart(String skuId) {
//        获取登录的用户信息
        UserInfo userInfo = LoginInterceptor.getUserInfo();
//        获取Redis中的KEY值
        String key = KEY_PREFIX + userInfo.getId();
//        获取Redis的操作对象
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(key);
//        删除 购物车记录目前只在Redis中，因此不需要删除数据库
        hashOperations.delete(skuId);
    }


}
