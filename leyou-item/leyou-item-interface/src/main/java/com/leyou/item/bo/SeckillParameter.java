package com.leyou.item.bo;

import lombok.Data;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/25 12:24 上午
 */
@Data //秒杀请求参数
public class SeckillParameter {

    private Long id;

    private String startTime;

    private String endTime;

    private Integer count;

    private double discount;


}
