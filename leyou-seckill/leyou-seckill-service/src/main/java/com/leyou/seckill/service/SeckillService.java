package com.leyou.seckill.service;

import com.leyou.item.pojo.SeckillGoods;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/23 11:39 下午
 */
public interface SeckillService {

    Long createOrder(SeckillGoods seckillGoods);
}
