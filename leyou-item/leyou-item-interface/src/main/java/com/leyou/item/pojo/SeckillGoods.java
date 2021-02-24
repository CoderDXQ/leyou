package com.leyou.item.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/21 10:23 下午
 */

@Data
@Table(name = "tb_seckill_sku")
public class SeckillGoods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long skuId;

    //    秒杀开始时间
    private Date startTime;

    //    秒杀结束时间
    private Date endTime;

    private Double seckillPrice;

    private String title;

    private String image;

    //    是否可以秒杀
    private Boolean enable;

    //    库存
    @JsonIgnore//禁止转换为json  这是一种数据保护
    @Transient//启动事务
    private Integer stock;

    @JsonIgnore
    @Transient
    private Integer seckillTotal;

}
