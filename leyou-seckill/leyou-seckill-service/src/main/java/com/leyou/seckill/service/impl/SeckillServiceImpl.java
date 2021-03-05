package com.leyou.seckill.service.impl;

import com.leyou.item.bo.SeckillParameter;
import com.leyou.item.bo.Sku;
import com.leyou.item.pojo.SeckillGoods;
import com.leyou.item.pojo.Stock;
import com.leyou.order.pojo.Order;
import com.leyou.order.pojo.OrderDetail;
import com.leyou.order.pojo.SeckillOrder;
import com.leyou.seckill.client.GoodsClient;
import com.leyou.seckill.client.OrderClient;
import com.leyou.seckill.mapper.SeckillMapper;
import com.leyou.seckill.mapper.SeckillOrderMapper;
import com.leyou.seckill.mapper.SkuMapper;
import com.leyou.seckill.mapper.StockMapper;
import com.leyou.seckill.service.SeckillService;
import com.leyou.seckill.utils.JsonUtils;
import com.leyou.seckill.vo.SeckillMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import tk.mybatis.mapper.entity.Example;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/23 11:39 下午
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(SeckillServiceImpl.class);

    private static final String KEY_PREFIX_PATH = "leyou:seckill:path";


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrder(SeckillGoods seckillGoods) {
        Order order = new Order();
        order.setPaymentType(1);
        order.setTotalPay(seckillGoods.getSeckillPrice());
        order.setActualPay(seckillGoods.getSeckillPrice());
        order.setPostFee(0 + "");
        order.setReceiver("李四");
        order.setReceiverMobile("15812312312");
        order.setReceiverCity("西安");
        order.setReceiverDistrict("碑林区");
        order.setReceiverState("陕西");
        order.setReceiverZip("000000000");
        order.setInvoiceType(0);
        order.setSourceType(2);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setSkuId(seckillGoods.getSkuId());
        orderDetail.setNum(1);
        orderDetail.setTitle(seckillGoods.getTitle());
        orderDetail.setImage(seckillGoods.getImage());
        orderDetail.setPrice(seckillGoods.getSeckillPrice());
        orderDetail.setOwnSpec(this.skuMapper.selectByPrimaryKey(seckillGoods.getSkuId()).getOwnSpec());

        order.setOrderDetails(Arrays.asList(orderDetail));

        String seck = "seckill";

//        ？？？暂时卡在这里 需要进行订单模块的重构
        ResponseEntity<List<Long>> responseEntity = this.orderClient.createOrder(seck, order);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return null;
        }

        return responseEntity.getBody().get(0);
    }


    @Override
    public boolean queryStock(Long skuId) {
        Stock stock = this.stockMapper.selectByPrimaryKey(skuId);
        if (stock.getSeckillStock() - 1 < 0) {
            return false;
        }
        return true;
    }

    @Override
    public void sendMessage(SeckillMessage seckillMessage) {
        String json = JsonUtils.serialize(seckillMessage);
        System.out.println(json);
        try {
            this.amqpTemplate.convertAndSend("order.seckill", json);
        } catch (AmqpException e) {
            LOGGER.error("秒杀商品消息发送异常，商品id: {}", seckillMessage.getSeckillGoods().getSkuId(), e);
        }

    }


    @Override
    public Long checkSeckillOrder(Long userId) {
        Example example = new Example(SeckillOrder.class);
        example.createCriteria().andEqualTo("userId", userId);
        List<SeckillOrder> seckillOrders = this.seckillOrderMapper.selectByExample(example);
        if (seckillOrders == null || seckillOrders.size() == 0) {
            return null;
        }
        return seckillOrders.get(0).getOrderId();
    }

    @Override
    public String createPath(Long goodsId, Long id) {
        String str = new BCryptPasswordEncoder().encode(goodsId.toString() + id);
        BoundHashOperations<String, Object, Object> hashOperations = this.stringRedisTemplate.boundHashOps(KEY_PREFIX_PATH);
        String key = id.toString() + "_" + goodsId;
        hashOperations.put(key, str);
        hashOperations.expire(60, TimeUnit.SECONDS);
        return str;
    }

    @Override
    public boolean checkSeckillPath(Long goodsId, Long id, String path) {
//        组装键
        String key = id.toString() + "_" + goodsId;
        BoundHashOperations<String, Object, Object> hashOperations = this.stringRedisTemplate.boundHashOps(KEY_PREFIX_PATH);
        String encodePath = (String) hashOperations.get(key);
        return new BCryptPasswordEncoder().matches(path, encodePath);
    }

    /**
     * 添加秒杀商品
     *
     * @param seckillParameter
     */

//    ？？？添加秒杀商品接口需要改改 duan 2021.3.5
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void addSeckillGoods(SeckillParameter seckillParameter) {
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        seckillParameter.setStartTime(sdf.format(calendar.getTime()));
//        calendar.add(Calendar.HOUR, 2);
//        seckillParameter.setEndTime(sdf.format(calendar.getTime()));
//
//        //1.根据spu_id查询商品
//        Long id = seckillParameter.getId();
//        Sku sku = goodsClient.querySkuBySkuId(id);
//        //2.插入到秒杀商品表中
//        SeckillGoods seckillGoods = new SeckillGoods();
//        seckillGoods.setEnable(true);
//        seckillGoods.setStartTime(seckillParameter.getStartTime());
//        seckillGoods.setEndTime(seckillParameter.getEndTime());
//        seckillGoods.setImage(sku.getImages());
//        seckillGoods.setSkuId(sku.getId());
//        seckillGoods.setStock(seckillParameter.getCount());
//        seckillGoods.setTitle(sku.getTitle());
//        seckillGoods.setSeckillPrice(sku.getPrice() * seckillParameter.getDiscount());
//        this.seckillMapper.insert(seckillGoods);
//        //3.更新对应的库存信息，tb_stock
//        Stock stock = stockMapper.selectByPrimaryKey(sku.getId());
//        stock.setSeckillStock(stock.getSeckillStock() + seckillParameter.getCount());
//        stock.setSeckillTotal(stock.getSeckillTotal() + seckillParameter.getCount());
//        stock.setStock(stock.getStock() - seckillParameter.getCount());
//        this.stockMapper.updateByPrimaryKeySelective(stock);
//
//    }
}
