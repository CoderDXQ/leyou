package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/7 2:25 上午
 */
@Data
@Table(name = "tb_stock")//这其实是一个水平分表
public class Stock {
    @Id
    private Long skuId;
    //    秒杀库存是从库存中分出来的
    private Integer seckillStock;// 秒杀可用库存
    private Integer seckillTotal;// 已秒杀数量
    private Integer stock;// 正常库存
}
