package com.leyou.seckill.controller;

import com.leyou.common.pojo.UserInfo;
import com.leyou.item.bo.SeckillParameter;
import com.leyou.item.pojo.SeckillGoods;
import com.leyou.seckill.access.AccessLimit;
import com.leyou.seckill.client.GoodsClient;
import com.leyou.seckill.interceptor.LoginInterceptor;
import com.leyou.seckill.service.SeckillService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/23 11:34 下午
 */

@RestController
@RequestMapping
public class SeckillController {

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
//    ???不知道这里到底有什么问题
//    @Override
    public void afterPropertiesSet() throws Exception {
//        从Redis中获取初始值
        BoundHashOperations<String, Object, Object> hashOperations = this.stringRedisTemplate.boundHashOps(KEY_PREFIX);
        if (hashOperations.hasKey(KEY_PREFIX)) {
            hashOperations.entries().forEach((m, n) -> localOverMap.put(Long.parseLong(m.toString()), false));
        }
    }

    //    ？？？这里还需要优化
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

//    @PostMapping("addSeckill")
//    public ResponseEntity<Boolean> addSeckillGoods(@RequestBody SeckillParameter seckillParameter){
//        if (seckillParameter != null){
//            this.seckillService.addSeckillGoods(seckillParameter);
//        }else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//        return ResponseEntity.ok().build();
//    }

    //    根据userId查询订单号
    @GetMapping("orderId")
    public ResponseEntity<Long> checkSeckillOrder(Long userId) {
        Long result = this.seckillService.checkSeckillOrder(userId);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(result);
    }

    //    自定义注解
    @AccessLimit(seconds = 20, maxCount = 5, needLogin = true)
    @GetMapping("get_path/{goodsId}")
    public ResponseEntity<String> getSeckillPath(@PathVariable("goodsId") Long goodsId) {
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String str = this.seckillService.createPath(goodsId, userInfo.getId());
        if (StringUtils.isEmpty(str)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(str);
    }


}
