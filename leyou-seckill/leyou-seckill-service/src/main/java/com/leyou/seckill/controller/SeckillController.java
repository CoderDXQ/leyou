package com.leyou.seckill.controller;

import com.leyou.item.pojo.SeckillGoods;
import com.leyou.seckill.client.GoodsClient;
import com.leyou.seckill.service.SeckillService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/23 11:34 下午
 */

@RestController
@RequestMapping
public class SeckillController implements InitializingBean {

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //ES中的索引前缀
    private static final String KEY_PREFIX = "leyou:seckill:stock";

    private Map<Long, Boolean> localOverMap = new HashMap<>();

    /**
     * 系统初始化，初始化秒杀商品数量
     *
     * @throws Exception
     */
//    @Override
//    public void afterPropertiesSet() throws Exception {
//
//    }
    @PostMapping("seck")
    public ResponseEntity<Long> seckillOrder(@RequestBody SeckillGoods seckillGoods) {
//        创建订单
        Long id = this.seckillService.createOrder(seckillGoods);

//        判断是否秒杀成功
        if (id == null) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
        return ResponseEntity.ok(id);
    }


}
