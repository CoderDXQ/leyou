package com.leyou.seckill.service;

import com.leyou.item.bo.SeckillParameter;
import com.leyou.item.pojo.SeckillGoods;
import com.leyou.seckill.vo.SeckillMessage;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/23 11:39 下午
 */
//@Service
public interface SeckillService {

    /**
     * 创建订单
     *
     * @param seckillGoods
     * @return
     */
    Long createOrder(SeckillGoods seckillGoods);


    /**
     * 检查库存
     *
     * @param skuId
     * @return
     */
    boolean queryStock(Long skuId);

    /**
     * 发送秒杀信息到队列当中
     *
     * @param seckillMessage
     */
    void sendMessage(SeckillMessage seckillMessage);

    /**
     * 根据用户id查询秒杀订单
     *
     * @param userId
     * @return
     */
    Long checkSeckillOrder(Long userId);


    /**
     * 创建秒杀地址
     *
     * @param goodsId
     * @param id
     * @return
     */
    String createPath(Long goodsId, Long id);

    /**
     * 验证秒杀地址
     *
     * @param goodsId
     * @param id
     * @param path
     * @return
     */
    boolean checkSeckillPath(Long goodsId, Long id, String path);

    void addSeckillGoods(SeckillParameter seckillParameter) throws ParseException;

    List<SeckillGoods> querySeckillGoods();

//    void addSeckillGoods(SeckillParameter seckillParameter);
}
