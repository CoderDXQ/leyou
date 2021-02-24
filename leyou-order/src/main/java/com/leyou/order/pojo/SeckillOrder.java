package com.leyou.order.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/25 1:59 上午
 */
@Data
@Table(name = "tb_seckill_order")
public class SeckillOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long skuId;

    private Long orderId;
}
